package com.dslplatform.client;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import com.dslplatform.client.json.JsonObject;
import com.dslplatform.client.json.JsonReader;
import com.dslplatform.patterns.ServiceLocator;
import org.slf4j.Logger;

import com.dslplatform.client.exceptions.HttpException;
import com.dslplatform.client.exceptions.HttpSecurityException;
import com.dslplatform.client.exceptions.HttpServerErrorException;
import com.dslplatform.client.exceptions.HttpUnexpectedCodeException;

class HttpClient {
	static String encode(final String param) {
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	static class Response {
		public final int code;
		public final byte[] body;
		public final HttpURLConnection connection;

		public Response(final HttpURLConnection connection) throws IOException {
			this.code = connection.getResponseCode();
			int len = connection.getContentLength();
			if (code < 300 && len >= 0) {
				body = new byte[len];
				int pos = 0;
				while (pos < body.length) {
					pos += connection.getInputStream().read(body, pos, body.length - pos);
				}
			} else {
				body = code < 400
						? Utils.inputStreamToByteArray(connection.getInputStream())
						: Utils.inputStreamToByteArray(connection.getErrorStream());
			}
			this.connection = connection;
		}

		public String bodyToString() {
			try {
				return body == null ? "" : new String(body, "UTF-8");
			} catch (final UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private final Logger logger;
	private final JsonSerialization jsonDeserialization;
	private final ServiceLocator locator;
	private final HttpHeaderProvider headerProvider;
	private final ExecutorService executorService;
	private final String domainPrefix;
	private final int domainPrefixLength;
	private final String[] remoteUrls;
	private final SSLSocketFactory SSL_SOCKET_FACTORY;
	private static final String MIME_TYPE = "application/json";
	private int currentUrl;

	public HttpClient(
			final Properties properties,
			final JsonSerialization jsonDeserialization,
			final ServiceLocator locator,
			final Logger logger,
			final HttpHeaderProvider headerProvider,
			final ExecutorService executorService) {
		this.logger = logger;
		this.jsonDeserialization = jsonDeserialization;
		this.locator = locator;
		this.executorService = executorService;
		this.remoteUrls = properties.getProperty("api-url").split(",\\s+");

		SSL_SOCKET_FACTORY = createSSLSocketFactory(properties);

		domainPrefix = properties.getProperty("package-name");
		if (domainPrefix == null) {
			throw new IllegalArgumentException("package-name is missing from provided configuration. It is used to specify root namespace");
		}
		domainPrefixLength = domainPrefix.length() + 1;

		this.headerProvider = headerProvider;
	}

	private SSLSocketFactory createSSLSocketFactory(
			final Properties properties) {
		final String trustStore = properties.getProperty("trustStore");
		final String trustStorePassword = properties.getProperty("trustStorePassword");
		if (trustStore != null && trustStorePassword != null)
			return createSSLSocketFactory(trustStore, trustStorePassword);
		else {
			final String trustStoreEnv = System.getenv("trustStore");
			final String trustStorePasswordEnv = System.getenv("trustStorePassword");
			if (trustStoreEnv != null && trustStorePasswordEnv != null)
				return createSSLSocketFactory(trustStoreEnv, trustStorePasswordEnv);
			else
				return null;
		}
	}

	private SSLSocketFactory createSSLSocketFactory(
			final String trustStore,
			final String trustStorePassword) {

		final String storeType = KeyStore.getDefaultType();

		try {
			if (storeType.equals("jks")) {
				final SSLContext sslContext = SSLContext.getInstance("TLS");
				final KeyStore keystore = KeyStore.getInstance(storeType);
				keystore.load(HttpClient.class.getResourceAsStream(trustStore), trustStorePassword.toCharArray());
				final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				tmf.init(keystore);
				final TrustManager[] tms = tmf.getTrustManagers();
				sslContext.init(null, tms, null);
				return sslContext.getSocketFactory();
			} else {
				return null;
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getDslName(final Class<?> clazz) {
		final String domainObjectName = clazz.getName();
		if (domainObjectName.startsWith(domainPrefix)) return domainObjectName.substring(domainPrefixLength);
		throw new RuntimeException(domainObjectName + " is not defined for package " + domainPrefix);
	}

	private static boolean contains(final int[] array, final int v) {
		for (final int e : array) {
			if (e == v) return true;
		}
		return false;
	}

	private <TArgument> byte[] doRawRequest(
			final String service,
			final List<Map.Entry<String, String>> headers,
			final String method,
			final TArgument content,
			final int[] expected,
			final long start) throws IOException {

		final byte[] body;
		if (content == null) {
			body = null;

			logger.debug("Sending request [{}]: {}", method, service);
		} else {
			if (content instanceof JsonObject) {
				StringWriter sw = new StringWriter();
				JsonObject jo = (JsonObject)content;
				jo.serialize(sw, true);
				sw.flush();
				body = sw.toString().getBytes("UTF-8");
			} else {
				body = JsonSerialization.serializeBytes(content);
			}

			logger.debug("Sending request [{}]: {}, content size: {} bytes", method, service, body.length);
		}

		final Response response = transmit(service, headers, method, body, 2);

		if (logger.isDebugEnabled()) {
			final long time = System.currentTimeMillis() - start;
			logger.debug("Received response [{}, {} bytes] in {} ms", response.code, response.body.length, time);

			if (logger.isTraceEnabled()) {
				logger.trace("Received response body: {}", response.bodyToString());
			}
		}

		if (response.code == HttpURLConnection.HTTP_FORBIDDEN || response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
			throw new HttpSecurityException(response.bodyToString(), response.code, response.connection.getHeaderFields());
		} else if (response.code >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
			throw new HttpServerErrorException(response.bodyToString(), response.code, response.connection.getHeaderFields());
		} else if (expected != null && !contains(expected, response.code)) {
			throw new HttpUnexpectedCodeException(response.bodyToString(), expected, response.code, response.connection.getHeaderFields());
		} else if (expected == null && response.code >= HttpURLConnection.HTTP_BAD_REQUEST) {
			throw new HttpException(response.bodyToString(), response.code, response.connection.getHeaderFields());
		}

		return response.body;
	}

	private static final List<Map.Entry<String, String>> emptyHeaders =
			new java.util.ArrayList<Map.Entry<String, String>>(0);

	public <TArgument> Future<byte[]> sendRawRequest(
			final String service,
			final String method,
			final TArgument content,
			final List<Map.Entry<String, String>> headers,
			final int[] expected) {

		final long start = System.currentTimeMillis();

		return executorService.submit(new Callable<byte[]>() {
			@Override
			public byte[] call() throws IOException {
				return doRawRequest(service, headers, method, content, expected, start);
			}
		});
	}

	private static final ConcurrentHashMap<Class<?>, JsonReader.ReadJsonObject<JsonObject>> jsonReaderes =
			new ConcurrentHashMap<Class<?>, JsonReader.ReadJsonObject<JsonObject>>();

	@SuppressWarnings("unchecked")
	private static JsonReader.ReadJsonObject<JsonObject> getReader(final Class<?> manifest) {
		try {
			JsonReader.ReadJsonObject<JsonObject> reader = jsonReaderes.get(manifest);
			if (reader == null) {
				reader = (JsonReader.ReadJsonObject<JsonObject>) manifest.getField("JSON_READER").get(null);
				jsonReaderes.putIfAbsent(manifest, reader);
			}
			return reader;
		}catch (Exception ignore) {
			return null;
		}
	}

	public <TArgument, TResult> Future<TResult> sendRequest(
			final Class<TResult> manifest,
			final String service,
			final String method,
			final TArgument content,
			final int[] expected) {

		final long start = System.currentTimeMillis();

		return executorService.submit(new Callable<TResult>() {
			@SuppressWarnings("unchecked")
			@Override
			public TResult call() throws IOException {
				final byte[] response = doRawRequest(service, emptyHeaders, method, content, expected, start);
				if (JsonObject.class.isAssignableFrom(manifest)) {
					JsonReader.ReadJsonObject<JsonObject> reader = getReader(manifest);
					if (reader != null) {
						JsonReader json = new JsonReader(response, locator);
						if (json.getNextToken() == '{') {
							return (TResult) reader.deserialize(json, locator);
						}
					}
				}
				return jsonDeserialization.deserialize(manifest, response);
			}
		});
	}

	public <TArgument, TResult> Future<List<TResult>> sendCollectionRequest(
			final Class<TResult> manifest,
			final String service,
			final String method,
			final TArgument content,
			final int[] expected) {

		final long start = System.currentTimeMillis();

		return executorService.submit(new Callable<List<TResult>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<TResult> call() throws IOException {
				final byte[] response = doRawRequest(service, emptyHeaders, method, content, expected, start);
				if (JsonObject.class.isAssignableFrom(manifest)) {
					JsonReader.ReadJsonObject<JsonObject> reader = getReader(manifest);
					if (reader != null) {
						final JsonReader json = new JsonReader(response, locator);
						if (json.getNextToken() == '[') {
							if (json.getNextToken() == ']') {
								return new ArrayList<TResult>();
							}
							return (List<TResult>) json.deserializeCollection(reader);
						}
					}
				}
				return jsonDeserialization.deserialize(JsonSerialization.buildCollectionType(ArrayList.class, manifest), response);
			}
		});
	}

	private Response transmit(
			final String service,
			final List<Map.Entry<String, String>> headers,
			final String method,
			final byte[] payload,
			final int retriesOnConflictOrConnectionError) throws IOException {
		IOException error = null;
		final int maxLen = remoteUrls.length + currentUrl;
		for (int i = currentUrl; i < maxLen; i++) {
			try {
				final URL url = new URL(remoteUrls[i % remoteUrls.length] + service);
				final HttpClient.Response response = transmit(url, headers, method, payload);
				if (response.code == HttpURLConnection.HTTP_CONFLICT && retriesOnConflictOrConnectionError > 0) {
					return transmit(service, headers, method, payload, retriesOnConflictOrConnectionError - 1);
				}
				if (response.code < HttpURLConnection.HTTP_INTERNAL_ERROR) {
					return response;
				}
				logger.error("At {} [{}] {}", url, response.code, response.bodyToString());
				logger.error("Error connecting to {}. Trying next server if exists...", url);
				error = new IOException(response.bodyToString());
			} catch (final java.net.ConnectException ce) {
				if (retriesOnConflictOrConnectionError > 0) {
					return transmit(service, headers, method, payload, retriesOnConflictOrConnectionError - 1);
				}
				logger.error("At {} {}. Trying next server if exists...", service, ce.getMessage());
				error = ce;
			} catch (final IOException ex) {
				logger.error("IOException {} to {}. Trying next server if exists...", ex.getMessage(), service);
				error = ex;
			}
			currentUrl++;
			currentUrl %= remoteUrls.length;
		}
		throw error;
	}

	private Response transmit(
			final URL url,
			final List<Map.Entry<String, String>> headers,
			final String method,
			final byte[] payload) throws IOException {

		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn instanceof HttpsURLConnection && SSL_SOCKET_FACTORY != null)
			((HttpsURLConnection) conn).setSSLSocketFactory(SSL_SOCKET_FACTORY);

		conn.setRequestMethod(method);

		conn.setRequestProperty("Accept", MIME_TYPE);
		conn.setRequestProperty("Content-Type", MIME_TYPE);

		for (final Map.Entry<String, String> h : headers) {
			conn.setRequestProperty(h.getKey(), h.getValue());
		}

		logger.debug("{} {}", method, url.toString());
		for (final Map.Entry<String, String> h : headerProvider.getHeaders()) {
			conn.setRequestProperty(h.getKey(), h.getValue());
		}

		if (logger.isTraceEnabled()) {
			for (final Map.Entry<String, List<String>> header : conn.getRequestProperties().entrySet()) {
				logger.trace("{}: {}", header.getKey(), header.getValue());
			}
		}

		if (payload != null) {
			conn.setDoOutput(true);
			if (logger.isTraceEnabled()) logger.trace("Adding payload: {}", new String(payload, "UTF-8"));
			//conn.setFixedLengthStreamingMode(payload.length); //TODO: enable when changed to stream
			conn.setRequestProperty("Content-Length", Integer.toString(payload.length));
			conn.getOutputStream().write(payload);
			conn.getOutputStream().close();
		}

		return new Response(conn);
	}
}
