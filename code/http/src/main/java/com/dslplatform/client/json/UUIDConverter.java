package com.dslplatform.client.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class UUIDConverter {

	public static final UUID MIN_UUID = new java.util.UUID(0L, 0L);

	public static void serializeNullable(final UUID value, final Writer sw) throws IOException {
		if (value == null)
			sw.write("null");
		else
			serialize(value, sw);
	}

	public static void serialize(final UUID value, final Writer sw) throws IOException {
		sw.write('"');
		sw.write(value.toString());
		sw.write('"');
	}

	public static UUID deserialize(final JsonReader reader) throws IOException {
		return UUID.fromString(reader.readSimpleString());
	}

	private static JsonReader.ReadObject<UUID> Reader = new JsonReader.ReadObject<UUID>() {
		@Override
		public UUID read(JsonReader reader) throws IOException {
			return deserialize(reader);
		}
	};

	public static ArrayList<UUID> deserializeCollection(final JsonReader reader) throws IOException {
		return reader.deserializeCollection(Reader);
	}

	public static void deserializeCollection(final JsonReader reader, final Collection<UUID> res) throws IOException {
		reader.deserializeCollection(Reader, res);
	}

	public static ArrayList<UUID> deserializeNullableCollection(final JsonReader reader) throws IOException {
		return reader.deserializeNullableCollection(Reader);
	}

	public static void deserializeNullableCollection(final JsonReader reader, final Collection<UUID> res) throws IOException {
		reader.deserializeNullableCollection(Reader, res);
	}
}
