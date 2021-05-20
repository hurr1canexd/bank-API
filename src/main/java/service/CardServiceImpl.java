package service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import db.DAO.CardDAO;
import model.Card;
import org.json.JSONArray;

public class CardServiceImpl implements CardService {
    private final CardDAO cardDAO;

    public CardServiceImpl(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    @Override
    public Card getCardFromJson() {
        // TODO: 19.05.2021
        return null;
    }

    @Override
    public void insertCardInDatabase(Card card) {
        cardDAO.create(card);
    }

    @Override
    public ArrayNode getCards() {
        return cardDAO.getCards();
    }
}
