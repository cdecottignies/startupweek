package fr.ulille.iut.agile;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import fr.ulille.iut.agile.dao.UUIDArgumentFactory;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class BDDFactory {
    private static Jdbi jdbi = null;
    private static String dbPath = "jdbc:sqlite:"
            + System.getProperty("java.io.tmpdir")
            + System.getProperty("file.separator")
            + System.getProperty("user.name")
            + "_" ;

    public static Jdbi getJdbi() {
        if (jdbi == null) {
            Properties properties = new Properties();
            // Nécessaire pour SQLite
            properties.setProperty("foreign_keys", "true");

            jdbi = Jdbi.create(dbPath + "data.db", properties)
                    .installPlugin(new SQLitePlugin())
                    .installPlugin(new SqlObjectPlugin())
                    .registerArgument(new UUIDArgumentFactory());

            // La ligne suivante permet d'activer l'affichage de log liés aux DAO
            // jdbi.setSqlLogger(new JdbiSqlLogger());
        }
        return jdbi;
    }

    public static void setJdbiForTests() {
        if ( jdbi == null ) {
            Properties properties = new Properties();
            properties.setProperty("foreign_keys", "true");

            jdbi = Jdbi.create(dbPath + "pizza_test.db", properties)
                .installPlugin(new SQLitePlugin())
                .installPlugin(new SqlObjectPlugin())
                .registerArgument(new UUIDArgumentFactory());

            // La ligne suivante permet d'activer l'affichage de log liés aux DAO
            // jdbi.setSqlLogger(new JdbiSqlLogger());
        }
    }

    public static boolean tableExist(String tableName) throws SQLException {
        DatabaseMetaData dbm = getJdbi().open().getConnection().getMetaData();
        ResultSet tables = dbm.getTables(null, null, tableName, null);
        boolean exist = tables.next();
        tables.close();
        return exist;
    }

        public static void dropTables() throws SQLException {
        Handle handle = getJdbi().open();
        DatabaseMetaData dbm = handle.getConnection().getMetaData();
        ResultSet tables = dbm.getTables(null, null, "", null);
        ArrayList<String> tableNames = new ArrayList<String>();
        while ( tables.next() ) {
            tableNames.add(tables.getString("TABLE_NAME"));
        }
        tableNames.forEach(name -> handle.execute("drop table " + name));
    }

    public static <T> T buildDao(Class<T> daoClass) {
        return getJdbi().onDemand(daoClass);
    }
}

