package org.misha.bankapi.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.misha.bankapi.db.DAO.CardDAO;
import org.misha.bankapi.model.Card;

import java.sql.SQLException;

public class CardServiceImpl implements CardService {
    private final CardDAO cardDAO;

    public CardServiceImpl(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }
    @Override
    public void insertCardInDatabase(Card card) throws SQLException {
        cardDAO.create(card);
    }

    @Override
    public ArrayNode getCards() throws SQLException {
        return cardDAO.getCards();
    }
}
