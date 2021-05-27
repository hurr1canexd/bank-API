package org.misha.bankapi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2JDBCUtils {
    private static final String jdbcURL = "jdbc:h2:mem:bankDB;DB_CLOSE_DELAY=-1";
    private static final String jdbcUser = "sa";
    private static final String jdbcPassword = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return connection;
    }
}
