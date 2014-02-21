package model;

import java.io.IOException;

import model.linqAndSql.*;
import model.linqAndSql.repositories.*;
import org.slf4j.Logger;

import com.dslplatform.client.Bootstrap;
import com.dslplatform.patterns.ServiceLocator;

public class Main {
    private static final int[] is = { 1, 1, 1, 1, 2 };

    public static void main(final String[] args) throws IOException {
        // An instance of Service Locator.
        // You can use it to fetch instances of repositories.
        final ServiceLocator locator = init();
        //cleanUp(locator);
        //wormUp(locator);
        //searchSQLs(locator);
        snowShow(locator);
        shutdown(locator);
    }

    public static void snowShow(final ServiceLocator locator) throws IOException {
        for (BSnowSql bs : BSnowSql.findAll()) {
            final BSnow b = bs.getB();
            System.out.println("b:" + b.getName());
            System.out.println("a.s:" + bs.getS());
        }

        for (BSnowCSql bs : BSnowCSql.findAll()) {
            System.out.println("b:" + bs.getB().getName());
            System.out.println("c:" + ((bs.getC() == null) ? "null" : bs.getC().getName()));
            System.out.println("a.s:" + bs.getS());
        }
    }

    public static void cleanUp(final ServiceLocator locator) throws IOException {
        locator.resolve(BRepository.class).delete(B.findAll());
        locator.resolve(CRepository.class).delete(C.findAll());

        locator.resolve(A1Repository.class).delete(A1.findAll());
        locator.resolve(A2Repository.class).delete(A2.findAll());
    }

    public static void wormUp(final ServiceLocator locator) throws IOException {

        for (int i : is) {
            A1 a1 = new A1().setS(String.format("A1 for joined %s", i));
            A2 a2 = new A2(String.format("A2 for joined %s", i));
            a1.persist();
            a2.persist();
            B b = new B("joined B", a1, a2);
            b.persist();
            C c = new C("joined C", a1, a2);
            c.persist();
        }

        for (int i : is) {
            A1 a1 = new A1().setS("A1 for only B ");
            A2 a2 = new A2("A2 for only B ");
            a1.persist();
            a2.persist();
            B b = new B("only B", a1, a2);
            b.persist();
        }

        for (int i : is) {
            A1 a1 = new A1().setS("A1 for only C");
            A2 a2 = new A2("A2 for only C");
            a1.persist();
            a2.persist();
            C b = new C("only C", a1, a2);
            b.persist();
        }
    }

    public static void searchSQLs(final ServiceLocator locator) throws IOException {
        System.out.println("bcSQL");

        for (BCSql bs : BCSql.findAll()) {
            final C c = bs.getC();
            final B b = bs.getB();
            System.out.println("c:" + ((c != null) ? c.getName() : "null") + ", b:" + b.getName());
        }

        System.out.println("BSQL:");
        for (BSql bs : BSql.findAll()) {
            final B b = bs.getB();
            System.out.println("b name:" + b.getName());
        }
        System.out.println("cSQL");
        for (CSql bs : CSql.findAll()) {
            final C c = bs.getC();

            System.out.println("c name:" + c.getName());
        }
        System.out.println("DONE");
    }

    /**
     * Call to initialize an instance of ServiceLocator with a dsl-project.ini
     */
    public static ServiceLocator init() throws IOException {
        final ServiceLocator locator =
                Bootstrap.init(Main.class.getResourceAsStream("/dsl-project.ini"));

        // an example how to resolve components from the ServiceLocator
        locator.resolve(Logger.class).info("Locator has been initialized.");
        return locator;
    }

    /**
     * ExecutorService will keep on working up to a minute after program has
     * finished. This method is an example how one could quickly exit the
     * program and can be called to speed up application ending.
     * Alternatively, you can also invoke System.exit();
     */
    public static void shutdown(final ServiceLocator locator)
            throws IOException {
        locator.resolve(java.util.concurrent.ExecutorService.class).shutdown();
    }
}

