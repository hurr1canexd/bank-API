package db.DAO;

import db.H2JDBCUtils;
import org.h2.jdbc.JdbcSQLNonTransientException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {
    @Override
    public void addMoney(String number, BigDecimal sum) throws SQLException {
        String query = "UPDATE ACCOUNT SET BALANCE = (BALANCE + ?) " +
                "WHERE NUMBER = ?;";

        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setBigDecimal(1, sum);
            statement.setString(2, number);
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public BigDecimal getBalance(String accountNumber) throws SQLException {
        String query = "SELECT BALANCE FROM ACCOUNT " +
                "WHERE NUMBER = ?;";

        BigDecimal balance = null;

        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, accountNumber);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                balance = rs.getBigDecimal("balance");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return balance;
    }
}
