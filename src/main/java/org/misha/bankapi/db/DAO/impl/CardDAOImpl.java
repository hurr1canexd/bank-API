package org.misha.bankapi.db.DAO.impl;

import org.misha.bankapi.db.DAO.CardDAO;
import org.misha.bankapi.db.H2JDBCUtils;
import org.misha.bankapi.model.Card;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDAOImpl implements CardDAO {
    private final static String createCardQuery = "INSERT INTO CARD (number, month, year, code, account_id) " +
            "VALUES ( ?, ?, ?, ?, ?);";
    private final static String selectCardInfoQuery = "SELECT * from CARD;";

    @Override
    public void create(Card card) {
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(createCardQuery)) {

            statement.setString(1, card.getNumber());
            statement.setString(2, card.getMonth());
            statement.setString(3, card.getYear());
            statement.setString(4, card.getCode());
            statement.setInt(5, card.getAccountId());
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            // log and throw custom exception
        }

    }

    @Override
    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();

        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(selectCardInfoQuery);
            // todo: write from result set to list
            int index = 0;
            while (rs.next()) {
                index++;
                System.out.println(index);

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                // TODO: а что со статусом вообще
                String number = rs.getString("number");
                String month = rs.getString("month");
                String year = rs.getString("year");
                String code = rs.getString("code");
                BigDecimal balance = rs.getBigDecimal("balance");
                int accountId = rs.getInt("account_id");

                // TODO: решить проблему с автоинкрементом айди
                cards.add(new Card(
                        number,
                        month,
                        year,
                        code,
                        balance,
                        accountId
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cards;
    }
}
