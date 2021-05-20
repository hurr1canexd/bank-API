package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2JDBCUtils {
    private static String jdbcURL = "jdbc:h2:~/bankDB";
    private static String jdbcUser = "sa";
    private static String jdbcPassword = "";

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
