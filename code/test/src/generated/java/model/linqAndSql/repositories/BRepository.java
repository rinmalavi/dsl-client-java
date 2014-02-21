package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class BRepository extends
        ClientPersistableRepository<model.linqAndSql.B> {
    public BRepository(
            final ServiceLocator locator) {
        super(model.linqAndSql.B.class, locator);
    }
}
