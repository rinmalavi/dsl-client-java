package model.linqAndSql;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;
import com.fasterxml.jackson.annotation.*;

public final class BSnow implements Identifiable, java.io.Serializable {
    @JsonCreator
    public BSnow(
            @JsonProperty("URI") final String URI,
            @JsonProperty("name") final String name,
            @JsonProperty("a1") final model.linqAndSql.A1 a1,
            @JsonProperty("a2") final model.linqAndSql.A2 a2,
            @JsonProperty("a1ID") final int a1ID,
            @JsonProperty("intArr") final int[] intArr,
            @JsonProperty("intList") final java.util.List<Integer> intList,
            @JsonProperty("intSet") final java.util.Set<Integer> intSet,
            @JsonProperty("i") final int i,
            @JsonProperty("s") final String s,
            @JsonProperty("f") final float f) {
        this.URI = URI;
        this.name = name;
        if (name == null)
            throw new IllegalArgumentException(
                    "Property \"name\" cannot be null!");
        this.a1 = a1;
        if (a1 == null)
            throw new IllegalArgumentException(
                    "Property \"a1\" cannot be null!");
        this.a2 = a2;
        if (a2 == null)
            throw new IllegalArgumentException(
                    "Property \"a2\" cannot be null!");
        this.a1ID = a1ID;
        this.intArr = intArr;
        if (intArr == null)
            throw new IllegalArgumentException(
                    "Property \"intArr\" cannot be null!");
        this.intList = intList;
        if (intList == null)
            throw new IllegalArgumentException(
                    "Property \"intList\" cannot be null!");
        model.Guards.checkNulls(intList);
        this.intSet = intSet;
        if (intSet == null)
            throw new IllegalArgumentException(
                    "Property \"intSet\" cannot be null!");
        model.Guards.checkNulls(intSet);
        this.i = i;
        this.s = s;
        if (s == null)
            throw new IllegalArgumentException("Property \"s\" cannot be null!");
        this.f = f;
    }

    private BSnow() {
        this.URI = null;
        this.name = null;
        this.a1 = null;
        this.a2 = null;
        this.a1ID = 0;
        this.intArr = null;
        this.intList = null;
        this.intSet = null;
        this.i = 0;
        this.s = null;
        this.f = 0.0f;
    }

    private final String URI;

    public String getURI() {
        return this.URI;
    }

    @Override
    public int hashCode() {
        return URI.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;
        final BSnow other = (BSnow) obj;

        return URI.equals(other.URI);
    }

    @Override
    public String toString() {
        return "BSnow(" + URI + ')';
    }

    private static final long serialVersionUID = 0x0097000a;

    private final String name;

    public String getName() {
        return this.name;
    }

    private final model.linqAndSql.A1 a1;

    public model.linqAndSql.A1 getA1() {
        return this.a1;
    }

    private final model.linqAndSql.A2 a2;

    public model.linqAndSql.A2 getA2() {
        return this.a2;
    }

    private final int a1ID;

    public int getA1ID() {
        return this.a1ID;
    }

    private final int[] intArr;

    public int[] getIntArr() {
        return this.intArr;
    }

    private final java.util.List<Integer> intList;

    public java.util.List<Integer> getIntList() {
        return this.intList;
    }

    private final java.util.Set<Integer> intSet;

    public java.util.Set<Integer> getIntSet() {
        return this.intSet;
    }

    private final int i;

    public int getI() {
        return this.i;
    }

    private final String s;

    public String getS() {
        return this.s;
    }

    private final float f;

    public float getF() {
        return this.f;
    }

    public static BSnow find(final String uri) throws java.io.IOException {
        return find(uri, null);
    }

    public static BSnow find(final String uri, final ServiceLocator locator)
            throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(CrudProxy.class).read(BSnow.class, uri).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<BSnow> find(final Iterable<String> uris)
            throws java.io.IOException {
        return find(uris, Bootstrap.getLocator());
    }

    public static java.util.List<BSnow> find(
            final Iterable<String> uris,
            final ServiceLocator locator) throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class).find(BSnow.class, uris).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<BSnow> findAll() throws java.io.IOException {
        return findAll(null, null, Bootstrap.getLocator());
    }

    public static java.util.List<BSnow> findAll(final ServiceLocator locator)
            throws java.io.IOException {
        return findAll(null, null, locator);
    }

    public static java.util.List<BSnow> findAll(
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return findAll(limit, offset, Bootstrap.getLocator());
    }

    public static java.util.List<BSnow> findAll(
            final Integer limit,
            final Integer offset,
            final ServiceLocator locator) throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class)
                    .findAll(BSnow.class, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<BSnow> search(
            final Specification<BSnow> specification)
            throws java.io.IOException {
        return search(specification, null, null, Bootstrap.getLocator());
    }

    public static java.util.List<BSnow> search(
            final Specification<BSnow> specification,
            final ServiceLocator locator) throws java.io.IOException {
        return search(specification, null, null, locator);
    }

    public static java.util.List<BSnow> search(
            final Specification<BSnow> specification,
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(specification, limit, offset, Bootstrap.getLocator());
    }

    public static java.util.List<BSnow> search(
            final Specification<BSnow> specification,
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
                    .resolve(DomainProxy.class).count(BSnow.class).get()
                    .longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count(final Specification<BSnow> specification)
            throws java.io.IOException {
        return count(specification, Bootstrap.getLocator());
    }

    public static long count(
            final Specification<BSnow> specification,
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
}
