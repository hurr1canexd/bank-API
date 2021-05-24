package org.misha.bankapi.db.DAO;

import org.misha.bankapi.db.H2JDBCUtils;
import org.misha.bankapi.exception.AccountNotFoundException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {
    @Override
    public void addMoney(String number, BigDecimal sum) throws AccountNotFoundException {
        String query = "UPDATE ACCOUNT SET BALANCE = (BALANCE + ?) " +
                "WHERE NUMBER = ?;";

        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setBigDecimal(1, sum);
            statement.setString(2, number);
            int rowsChanged = statement.executeUpdate();
            if (rowsChanged == 0) {
                throw new AccountNotFoundException();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        String query = "SELECT BALANCE FROM ACCOUNT " +
                "WHERE NUMBER = ?;";

        BigDecimal balance = null;

        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, accountNumber);
            ResultSet rs = statement.executeQuery();
//            if (!rs.isBeforeFirst()) {
//                throw new AccountNotFoundException();
//            }
            if (rs.next()) {
                balance = rs.getBigDecimal("balance");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return balance;
    }
}
