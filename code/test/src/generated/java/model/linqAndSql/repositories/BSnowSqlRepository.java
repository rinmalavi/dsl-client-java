package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class BSnowSqlRepository extends
        ClientSearchableRepository<model.linqAndSql.BSnowSql> {
    public BSnowSqlRepository(
            final ServiceLocator locator) {
        super(model.linqAndSql.BSnowSql.class, locator);
    }

    public BSnowSqlRepository() {
        this(Bootstrap.getLocator());
    }
}
