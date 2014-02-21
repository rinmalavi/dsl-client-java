package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class BSnowRepository extends ClientRepository<model.linqAndSql.BSnow> {
    public BSnowRepository(
            final ServiceLocator locator) {
        super(model.linqAndSql.BSnow.class, locator);
    }

    public BSnowRepository() {
        this(Bootstrap.getLocator());
    }
}
