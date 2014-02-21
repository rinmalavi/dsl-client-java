package model.linqAndSql;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;
import com.fasterxml.jackson.annotation.*;

public class A1 implements java.io.Serializable, AggregateRoot {
    public A1() {
        _serviceLocator = Bootstrap.getLocator();
        _domainProxy = _serviceLocator.resolve(DomainProxy.class);
        _crudProxy = _serviceLocator.resolve(CrudProxy.class);
        this.ID = 0;
        this.s = "";
        this.i = 0;
        this.f = 0.0f;
        this.intArr = new int[] {};
        this.intList = new java.util.ArrayList<Integer>();
        this.intSet = new java.util.HashSet<Integer>();
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
        final A1 other = (A1) obj;

        return URI != null && URI.equals(other.URI);
    }

    @Override
    public String toString() {
        return URI != null ? "A1(" + URI + ')' : "new A1(" + super.hashCode()
                + ')';
    }

    private static final long serialVersionUID = 0x0097000a;

    public A1(
            final String s,
            final int i,
            final float f,
            final int[] intArr,
            final java.util.List<Integer> intList,
            final java.util.Set<Integer> intSet) {
        _serviceLocator = Bootstrap.getLocator();
        _domainProxy = _serviceLocator.resolve(DomainProxy.class);
        _crudProxy = _serviceLocator.resolve(CrudProxy.class);
        setS(s);
        setI(i);
        setF(f);
        setIntArr(intArr);
        setIntList(intList);
        setIntSet(intSet);
    }

    @JsonCreator
    private A1(
            @JacksonInject("_serviceLocator") final ServiceLocator _serviceLocator,
            @JsonProperty("URI") final String URI,
            @JsonProperty("ID") final int ID,
            @JsonProperty("s") final String s,
            @JsonProperty("i") final int i,
            @JsonProperty("f") final float f,
            @JsonProperty("intArr") final int[] intArr,
            @JsonProperty("intList") final java.util.List<Integer> intList,
            @JsonProperty("intSet") final java.util.Set<Integer> intSet) {
        this._serviceLocator = _serviceLocator;
        this._domainProxy = _serviceLocator.resolve(DomainProxy.class);
        this._crudProxy = _serviceLocator.resolve(CrudProxy.class);
        this.URI = URI;
        this.ID = ID;
        this.s = s == null ? "" : s;
        this.i = i;
        this.f = f;
        this.intArr = intArr == null ? new int[] {} : intArr;
        this.intList = intList == null
                ? new java.util.ArrayList<Integer>()
                : intList;
        this.intSet = intSet == null
                ? new java.util.HashSet<Integer>()
                : intSet;
    }

    private int ID;

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public int getID() {
        return ID;
    }

    private A1 setID(final int value) {
        this.ID = value;

        return this;
    }

    public static A1 find(final String uri) throws java.io.IOException {
        return find(uri, Bootstrap.getLocator());
    }

    public static A1 find(final String uri, final ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(CrudProxy.class).read(A1.class, uri).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<A1> find(final Iterable<String> uris)
            throws java.io.IOException {
        return find(uris, Bootstrap.getLocator());
    }

    public static java.util.List<A1> find(
            final Iterable<String> uris,
            final ServiceLocator locator) throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class).find(A1.class, uris).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<A1> findAll() throws java.io.IOException {
        return findAll(null, null, Bootstrap.getLocator());
    }

    public static java.util.List<A1> findAll(final ServiceLocator locator)
            throws java.io.IOException {
        return findAll(null, null, locator);
    }

    public static java.util.List<A1> findAll(
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return findAll(limit, offset, Bootstrap.getLocator());
    }

    public static java.util.List<A1> findAll(
            final Integer limit,
            final Integer offset,
            final ServiceLocator locator) throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class)
                    .findAll(A1.class, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<A1> search(
            final Specification<A1> specification) throws java.io.IOException {
        return search(specification, null, null, Bootstrap.getLocator());
    }

    public static java.util.List<A1> search(
            final Specification<A1> specification,
            final ServiceLocator locator) throws java.io.IOException {
        return search(specification, null, null, locator);
    }

    public static java.util.List<A1> search(
            final Specification<A1> specification,
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(specification, limit, offset, Bootstrap.getLocator());
    }

    public static java.util.List<A1> search(
            final Specification<A1> specification,
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
                    .resolve(DomainProxy.class).count(A1.class).get()
                    .longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count(final Specification<A1> specification)
            throws java.io.IOException {
        return count(specification, Bootstrap.getLocator());
    }

    public static long count(
            final Specification<A1> specification,
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

    private void updateWithAnother(final model.linqAndSql.A1 result) {
        this.URI = result.URI;

        this.s = result.s;
        this.i = result.i;
        this.f = result.f;
        this.intArr = result.intArr;
        this.intList = result.intList;
        this.intSet = result.intSet;
        this.ID = result.ID;
    }

    public A1 persist() throws java.io.IOException {
        final A1 result;
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

    public A1 delete() throws java.io.IOException {
        try {
            return _crudProxy.delete(A1.class, URI).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    private String s;

    @JsonProperty("s")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String getS() {
        return s;
    }

    public A1 setS(final String value) {
        if (value == null)
            throw new IllegalArgumentException("Property \"s\" cannot be null!");
        this.s = value;

        return this;
    }

    private int i;

    @JsonProperty("i")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public int getI() {
        return i;
    }

    public A1 setI(final int value) {
        this.i = value;

        return this;
    }

    private float f;

    @JsonProperty("f")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public float getF() {
        return f;
    }

    public A1 setF(final float value) {
        this.f = value;

        return this;
    }

    private int[] intArr;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("intArr")
    public int[] getIntArr() {
        return intArr;
    }

    public A1 setIntArr(final int[] value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"intArr\" cannot be null!");
        this.intArr = value;

        return this;
    }

    private java.util.List<Integer> intList;

    @JsonProperty("intList")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public java.util.List<Integer> getIntList() {
        return intList;
    }

    public A1 setIntList(final java.util.List<Integer> value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"intList\" cannot be null!");
        model.Guards.checkNulls(value);
        this.intList = value;

        return this;
    }

    private java.util.Set<Integer> intSet;

    @JsonProperty("intSet")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public java.util.Set<Integer> getIntSet() {
        return intSet;
    }

    public A1 setIntSet(final java.util.Set<Integer> value) {
        if (value == null)
            throw new IllegalArgumentException(
                    "Property \"intSet\" cannot be null!");
        model.Guards.checkNulls(value);
        this.intSet = value;

        return this;
    }
}
