package model.linqAndSql.repositories;

import com.dslplatform.patterns.*;
import com.dslplatform.client.*;

public class CSqlRepository extends
        ClientSearchableRepository<model.linqAndSql.CSql> {
    public CSqlRepository(
            final ServiceLocator locator) {
        super(model.linqAndSql.CSql.class, locator);
    }

    public CSqlRepository() {
        this(Bootstrap.getLocator());
    }
}
