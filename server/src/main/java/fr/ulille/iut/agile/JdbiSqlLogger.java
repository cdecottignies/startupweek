package fr.ulille.iut.agile;

import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;
import java.sql.SQLException;
import java.util.logging.Logger;

public class JdbiSqlLogger implements SqlLogger {
    private static final Logger LOGGER = Logger.getLogger(JdbiSqlLogger.class.getName());

    public void logBeforeExecution(StatementContext context) {
        LOGGER.info(context.getStatement().toString());
    }

    public void logException(StatementContext context, SQLException ex) {
        LOGGER.info("JDBI SQL EXCEPTION: " + ex.getMessage());
    }
}
