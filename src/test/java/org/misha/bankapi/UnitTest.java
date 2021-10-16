package org.misha.bankapi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.misha.bankapi.db.DAO.impl.AccountDAOImpl;
import org.misha.bankapi.db.DAO.impl.CardDAOImpl;
import org.misha.bankapi.db.DBInitializer;
import org.misha.bankapi.db.H2JDBCUtils;
import org.misha.bankapi.exception.AccountNotFoundException;
import org.misha.bankapi.model.Card;
import org.misha.bankapi.model.CardRequest;
import org.misha.bankapi.model.Deposit;
import org.misha.bankapi.service.AccountService;
import org.misha.bankapi.service.impl.AccountServiceImpl;
import org.misha.bankapi.service.CardService;
import org.misha.bankapi.service.impl.CardServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UnitTest {
    private final String getCardNumberQuery = "SELECT COUNT(*) AS total FROM Card";
    private final String getBalanceQuery = "SELECT balance FROM Account WHERE number = '40804810200003497183';";

    @BeforeAll
    public static void setUp() throws IOException {
        DBInitializer.init();
    }

    @Test
    public void shouldInsertCardTest() throws IOException {
        CardRequest cardRq = new CardRequest(
                "42024305346286324586", "07", "2028", BigDecimal.ZERO, 1);
        CardService cardService = new CardServiceImpl(new CardDAOImpl());
        try {
            cardService.insertCardInDatabase(cardRq);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        int actual = 0;
        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(getCardNumberQuery);
            while (rs.next()) {
                actual = rs.getInt("total");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        int expected = 2;
        Assert.assertEquals(expected, actual);


    }

    @Test
    public void shouldViewCardsTest() throws IOException {
        String actual = "";
        CardService cardService = new CardServiceImpl(new CardDAOImpl());
        try {
            actual = cardService.getCards().toString();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String expected = "[{\"id\":1,\"number\":\"42024305346286324576\"}]";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldMakeDepositTest() throws IOException {
        AccountService accountService = new AccountServiceImpl(new AccountDAOImpl());
        Deposit deposit = new Deposit("40804810200003497183", BigDecimal.valueOf(250));
        accountService.topUpAccountBalance(deposit);

        BigDecimal actual = BigDecimal.ZERO;
        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(getBalanceQuery);
            while (rs.next()) {
                actual = rs.getBigDecimal("balance");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        BigDecimal expected = new BigDecimal(250);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldCheckBalanceTest() throws IOException {
        DBInitializer.init();

        AccountService accountService = new AccountServiceImpl(new AccountDAOImpl());
        BigDecimal actual = null;
        try {
            actual = accountService.getAccountBalance("40804810200003497183");
        } catch (AccountNotFoundException ex) {
            ex.printStackTrace();
        }

        BigDecimal expected = new BigDecimal(0);
        Assert.assertEquals(expected, actual);
    }

}
