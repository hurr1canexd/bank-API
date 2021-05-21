package service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Card;

import java.sql.SQLException;

public interface CardService {
    void insertCardInDatabase(Card card) throws SQLException;
    ArrayNode getCards() throws SQLException;
}
