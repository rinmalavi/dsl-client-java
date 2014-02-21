package model.linqAndSql;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;
import com.fasterxml.jackson.annotation.*;

public class C implements java.io.Serializable, AggregateRoot {
    public C() {
        _serviceLocator = Bootstrap.getLocator();
        _domainProxy = _serviceLocator.resolve(DomainProxy.class);
        _crudProxy = _serviceLocator.resolve(CrudProxy.class);
        this.ID = 0;
        this.name = "";
        this.a1ID = 0;
        this.a2ID = 0;
    }

    private transient final ServiceLocator _serviceLocator;
    private transient final DomainProxy _domainProxy;
    private transient final CrudProxy _crudProxy;

    private String URI;

    @JsonProperty("URI")
    public String getURI() {
        return this.URI;
    }

    @Override
    public int hashCode() {
        return URI != null ? URI.hashCode() : super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;
        final C other = (C) obj;

        return URI != null && URI.equals(other.URI);
    }

    @Override
    public String toString() {
        return URI != null ? "C(" + URI + ')' : "new C(" + super.hashCode()
                + ')';
    }

    private static final long serialVersionUID = 0x0097000a;

    public C(
            final String name,
            final model.linqAndSql.A1 a1,
            final model.linqAndSql.A2 a2) {
        _serviceLocator = Bootstrap.getLocator();
        _domainProxy = _serviceLocator.resolve(DomainProxy.class);
        _crudProxy = _serviceLocator.resolve(CrudProxy.class);
        setName(name);
        setA1(a1);
        setA2(a2);
    }

    @JsonCreator
    private C(
            @JacksonInject("_serviceLocator") final ServiceLocator _serviceLocator,
            @JsonProperty("URI") final String URI,
            @JsonProperty("ID") final int ID,
            @JsonProperty("name") final String name,
            @JsonProperty("a1URI") final String a1URI,
            @JsonProperty("a1ID") final int a1ID,
            @JsonProperty("a2URI") final String a2URI,
            @JsonProperty("a2ID") final int a2ID) {
        this._serviceLocator = _serviceLocator;
        this._domainProxy = _serviceLocator.resolve(DomainProxy.class);
        this._crudProxy = _serviceLocator.resolve(CrudProxy.class);
        this.URI = URI;
        this.ID = ID;
        this.name = name == null ? "" : name;
        this.a1URI = a1URI == null ? null : a1URI;
        this.a1ID = a1ID;
        this.a2URI = a2URI == null ? null : a2URI;
        this.a2ID = a2ID;
    }

    private int ID;

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public int getID() {
        return ID;
    }

    private C setID(final int value) {
        this.ID = value;

        return this;
    }

    public static C find(final String uri) throws java.io.IOException {
        return find(uri, Bootstrap.getLocator());
    }

    public static C find(final String uri, final ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(CrudProxy.class).read(C.class, uri).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<C> find(final Iterable<String> uris)
            throws java.io.IOException {
        return find(uris, Bootstrap.getLocator());
    }

    public static java.util.List<C> find(
            final Iterable<String> uris,
            final ServiceLocator locator) throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class).find(C.class, uris).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<C> findAll() throws java.io.IOException {
        return findAll(null, null, Bootstrap.getLocator());
    }

    public static java.util.List<C> findAll(final ServiceLocator locator)
            throws java.io.IOException {
        return findAll(null, null, locator);
    }

    public static java.util.List<C> findAll(
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return findAll(limit, offset, Bootstrap.getLocator());
    }

    public static java.util.List<C> findAll(
            final Integer limit,
            final Integer offset,
            final ServiceLocator locator) throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class)
                    .findAll(C.class, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<C> search(final Specification<C> specification)
            throws java.io.IOException {
        return search(specification, null, null, Bootstrap.getLocator());
    }

    public static java.util.List<C> search(
            final Specification<C> specification,
            final ServiceLocator locator) throws java.io.IOException {
        return search(specification, null, null, locator);
    }

    public static java.util.List<C> search(
            final Specification<C> specification,
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(specification, limit, offset, Bootstrap.getLocator());
    }

    public static java.util.List<C> search(
            final Specification<C> specification,
            final Integer limit,
            final Integer offset,
            final ServiceLocator locator) throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class)
                    .search(specification, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count() throws java.io.IOException {
        return count(Bootstrap.getLocator());
    }

    public static long count(final ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class).count(C.class).get()
                    .longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count(final Specification<C> specification)
            throws java.io.IOException {
        return count(specification, Bootstrap.getLocator());
    }

    public static long count(
            final Specification<C> specification,
            final ServiceLocator locator) throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class).count(specification).get()
                    .longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    private void updateWithAnother(final model.linqAndSql.C result) {
        this.URI = result.URI;

        this.name = result.name;
        this.a1 = result.a1;
        this.a1URI = result.a1URI;
        this.a1ID = result.a1ID;
        this.a2 = result.a2;
        this.a2URI = result.a2URI;
        this.a2ID = result.a2ID;
        this.ID = result.ID;
    }

    public C persist() throws java.io.IOException {
        if (this.getA1URI() == null) {
            throw new IllegalArgumentException(
                    "Cannot persist instance of 'model.linqAndSql.C' because reference 'a1' was not assigned");
        }
        if (this.getA2URI() == null) {
            throw new IllegalArgumentException(
                    "Cannot persist instance of 'model.linqAndSql.C' because reference 'a2' was not assigned");
        }
        final C result;
        try {
            result = this.URI == null
                    ? _crudProxy.create(this).get()
                    : _crudProxy.update(this).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
        this.updateWithAnother(result);
        return this;
    }

    public C delete() throws java.io.IOException {
        try {
            return _crudProxy.delete(C.class, URI).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    private String name;

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String getName() {
        return name;
    }

    public C setName(final String value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"name\" cannot be null!");
        this.name = value;

        return this;
    }

    private model.linqAndSql.A1 a1;

    @JsonIgnore
    public model.linqAndSql.A1 getA1() throws java.io.IOException {
        if (a1 != null && !a1.getURI().equals(a1URI) || a1 == null
                && a1URI != null) try {
            a1 = _crudProxy.read(model.linqAndSql.A1.class, a1URI).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
        return a1;
    }

    public C setA1(final model.linqAndSql.A1 value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"a1\" cannot be null!");

        if (value != null && value.getURI() == null)
            throw new IllegalArgumentException(
                    "Reference \"linqAndSql.A1\" for property \"a1\" must be persisted before it's assigned");
        this.a1 = value;

        this.a1URI = value.getURI();

        this.a1ID = value.getID();
        return this;
    }

    private String a1URI;

    @JsonProperty("a1URI")
    public String getA1URI() {
        return this.a1URI;
    }

    private int a1ID;

    @JsonProperty("a1ID")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public int getA1ID() {
        return a1ID;
    }

    private C setA1ID(final int value) {
        this.a1ID = value;

        return this;
    }

    private model.linqAndSql.A2 a2;

    @JsonIgnore
    public model.linqAndSql.A2 getA2() throws java.io.IOException {
        if (a2 != null && !a2.getURI().equals(a2URI) || a2 == null
                && a2URI != null) try {
            a2 = _crudProxy.read(model.linqAndSql.A2.class, a2URI).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
        return a2;
    }

    public C setA2(final model.linqAndSql.A2 value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"a2\" cannot be null!");

        if (value != null && value.getURI() == null)
            throw new IllegalArgumentException(
                    "Reference \"linqAndSql.A2\" for property \"a2\" must be persisted before it's assigned");
        this.a2 = value;

        this.a2URI = value.getURI();

        this.a2ID = value.getID();
        return this;
    }

    private String a2URI;

    @JsonProperty("a2URI")
    public String getA2URI() {
        return this.a2URI;
    }

    private int a2ID;

    @JsonProperty("a2ID")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public int getA2ID() {
        return a2ID;
    }

    private C setA2ID(final int value) {
        this.a2ID = value;

        return this;
    }
}
