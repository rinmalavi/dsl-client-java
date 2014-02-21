package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class BCSqlRepository extends
        ClientSearchableRepository<model.linqAndSql.BCSql> {
    public BCSqlRepository(
            final ServiceLocator locator) {
        super(model.linqAndSql.BCSql.class, locator);
    }

    public BCSqlRepository() {
        this(Bootstrap.getLocator());
    }
}
