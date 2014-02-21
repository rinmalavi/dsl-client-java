package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class A1Repository extends
        ClientPersistableRepository<model.linqAndSql.A1> {
    public A1Repository(
            final ServiceLocator locator) {
        super(model.linqAndSql.A1.class, locator);
    }
}
