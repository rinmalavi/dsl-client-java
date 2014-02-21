package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class A2Repository extends
        ClientPersistableRepository<model.linqAndSql.A2> {
    public A2Repository(
            final ServiceLocator locator) {
        super(model.linqAndSql.A2.class, locator);
    }
}
