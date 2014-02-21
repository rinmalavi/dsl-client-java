package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class BSqlRepository extends
        ClientSearchableRepository<model.linqAndSql.BSql> {
    public BSqlRepository(
            final ServiceLocator locator) {
        super(model.linqAndSql.BSql.class, locator);
    }

    public BSqlRepository() {
        this(Bootstrap.getLocator());
    }
}
