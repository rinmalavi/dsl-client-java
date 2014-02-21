package model.linqAndSql;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public final class BSnowSql implements Searchable, java.io.Serializable {
    @com.fasterxml.jackson.annotation.JsonCreator
    public BSnowSql(
            @com.fasterxml.jackson.annotation.JsonProperty("b") final model.linqAndSql.BSnow b,
            @com.fasterxml.jackson.annotation.JsonProperty("s") final String s,
            @com.fasterxml.jackson.annotation.JsonProperty("i") final int i,
            @com.fasterxml.jackson.annotation.JsonProperty("f") final float f,
            @com.fasterxml.jackson.annotation.JsonProperty("intArr") final int[] intArr,
            @com.fasterxml.jackson.annotation.JsonProperty("intList") final java.util.List<Integer> intList,
            @com.fasterxml.jackson.annotation.JsonProperty("intSet") final java.util.Set<Integer> intSet) {
        this.b = b;
        this.s = s == null ? "" : s;
        this.i = i;
        this.f = f;
        this.intArr = intArr == null ? new int[0] : intArr;
        this.intList = intList == null
                ? new java.util.ArrayList<Integer>()
                : intList;
        model.Guards.checkNulls(intList);
        this.intSet = intSet == null
                ? new java.util.HashSet<Integer>()
                : intSet;
        model.Guards.checkNulls(intSet);
    }

    private static final long serialVersionUID = 0x0097000a;

    private final model.linqAndSql.BSnow b;

    @com.fasterxml.jackson.annotation.JsonProperty("b")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public model.linqAndSql.BSnow getB() {
        return this.b;
    }

    private final String s;

    @com.fasterxml.jackson.annotation.JsonProperty("s")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public String getS() {
        return this.s;
    }

    private final int i;

    @com.fasterxml.jackson.annotation.JsonProperty("i")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public int getI() {
        return this.i;
    }

    private final float f;

    @com.fasterxml.jackson.annotation.JsonProperty("f")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public float getF() {
        return this.f;
    }

    private final int[] intArr;

    @com.fasterxml.jackson.annotation.JsonProperty("intArr")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public int[] getIntArr() {
        return this.intArr;
    }

    private final java.util.List<Integer> intList;

    @com.fasterxml.jackson.annotation.JsonProperty("intList")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public java.util.List<Integer> getIntList() {
        return this.intList;
    }

    private final java.util.Set<Integer> intSet;

    @com.fasterxml.jackson.annotation.JsonProperty("intSet")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    public java.util.Set<Integer> getIntSet() {
        return this.intSet;
    }

    public static java.util.List<BSnowSql> findAll() throws java.io.IOException {
        return findAll(null, null, Bootstrap.getLocator());
    }

    public static java.util.List<BSnowSql> findAll(final ServiceLocator locator)
            throws java.io.IOException {
        return findAll(null, null, locator);
    }

    public static java.util.List<BSnowSql> findAll(
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return findAll(limit, offset, Bootstrap.getLocator());
    }

    public static java.util.List<BSnowSql> findAll(
            final Integer limit,
            final Integer offset,
            final ServiceLocator locator) throws java.io.IOException {
        try {
            return (locator != null ? locator : Bootstrap.getLocator())
                    .resolve(DomainProxy.class)
                    .findAll(BSnowSql.class, limit, offset, null).get();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static java.util.List<BSnowSql> search(
            final Specification<BSnowSql> specification)
            throws java.io.IOException {
        return search(specification, null, null, Bootstrap.getLocator());
    }

    public static java.util.List<BSnowSql> search(
            final Specification<BSnowSql> specification,
            final ServiceLocator locator) throws java.io.IOException {
        return search(specification, null, null, locator);
    }

    public static java.util.List<BSnowSql> search(
            final Specification<BSnowSql> specification,
            final Integer limit,
            final Integer offset) throws java.io.IOException {
        return search(specification, limit, offset, Bootstrap.getLocator());
    }

    public static java.util.List<BSnowSql> search(
            final Specification<BSnowSql> specification,
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
                    .resolve(DomainProxy.class).count(BSnowSql.class).get()
                    .longValue();
        } catch (final InterruptedException e) {
            throw new java.io.IOException(e);
        } catch (final java.util.concurrent.ExecutionException e) {
            throw new java.io.IOException(e);
        }
    }

    public static long count(final Specification<BSnowSql> specification)
            throws java.io.IOException {
        return count(specification, Bootstrap.getLocator());
    }

    public static long count(
            final Specification<BSnowSql> specification,
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
