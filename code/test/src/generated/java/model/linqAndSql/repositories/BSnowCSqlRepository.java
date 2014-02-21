package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class BSnowCSqlRepository extends
        ClientSearchableRepository<model.linqAndSql.BSnowCSql> {
    public BSnowCSqlRepository(
            final ServiceLocator locator) {
        super(model.linqAndSql.BSnowCSql.class, locator);
    }

    public BSnowCSqlRepository() {
        this(Bootstrap.getLocator());
    }
}
