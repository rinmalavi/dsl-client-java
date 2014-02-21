package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class CRepository extends
        ClientPersistableRepository<model.linqAndSql.C> {
    public CRepository(
            final ServiceLocator locator) {
        super(model.linqAndSql.C.class, locator);
    }
}
