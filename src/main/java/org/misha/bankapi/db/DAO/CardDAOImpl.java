package org.misha.bankapi.db.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.misha.bankapi.db.H2JDBCUtils;
import org.misha.bankapi.model.Card;

import java.sql.*;

public class CardDAOImpl implements CardDAO {
    @Override
    public void create(Card card) throws SQLException {
        String query = "INSERT INTO CARD (number, month, year, code, accountid) " +
                "VALUES ( ?, ?, ?, ?, ?);";

        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, card.getNumber());
            statement.setString(2, card.getMonth());
            statement.setString(3, card.getYear());
            statement.setString(4, card.getCode());
            statement.setInt(5, card.getAccountId());
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public ArrayNode getCards() throws SQLException {
        String query = "SELECT id, number from CARD;";
        ArrayNode arr = null;

        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(query);
            ObjectMapper mapper = new ObjectMapper();
            arr = mapper.createArrayNode();
            while (rs.next()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", rs.getInt("id"));
                node.put("number", rs.getString("number"));
                arr.add(node);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return arr;
    }
}
