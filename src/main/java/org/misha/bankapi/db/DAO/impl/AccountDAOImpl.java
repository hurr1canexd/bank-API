package org.misha.bankapi.db.DAO.impl;

import org.misha.bankapi.db.DAO.AccountDAO;
import org.misha.bankapi.db.H2JDBCUtils;
import org.misha.bankapi.exception.AccountNotFoundException;
import org.misha.bankapi.model.Account;
import org.misha.bankapi.model.Card;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {
    private static final String addMoneyQuery = "UPDATE ACCOUNT SET BALANCE = (BALANCE + ?) " +
            "WHERE NUMBER = ?;";
    private static final String getBalanceQuery = "SELECT BALANCE FROM ACCOUNT " +
            "WHERE NUMBER = ?;";
    private static final String getAccountQuery = "SELECT * FROM ACCOUNT " +
            "WHERE NUMBER = ?;";


    @Override
    public void addMoney(String number, BigDecimal sum) throws AccountNotFoundException {
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(addMoneyQuery)) {

            statement.setBigDecimal(1, sum);
            statement.setString(2, number);
            // TODO: Бизнес логика зашита в ДАО, это неправильно
            int rowsChanged = statement.executeUpdate();
            if (rowsChanged == 0) {
                throw new AccountNotFoundException();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public BigDecimal getBalance(String accountNumber) throws AccountNotFoundException {
        BigDecimal balance = null;

        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(getBalanceQuery)) {

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
            throw new AccountNotFoundException();
        }

        return balance;
    }

    @Override
    public Account getAccount(String accountNumber) {
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(getAccountQuery)) {

            statement.setString(1, accountNumber);
            ResultSet rs = statement.executeQuery();

            String number = rs.getString("number");
            BigDecimal balance = rs.getBigDecimal("balance");
            int clientId = rs.getInt("client_id");

            if (!rs.next()) {
                return null;
            }
            // TODO: решить проблему с автоинкрементом айди
            return new Account(
                    number,
                    balance,
                    clientId);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
