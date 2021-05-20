package service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Card;

public interface CardService {
    public Card getCardFromJson();
    public void insertCardInDatabase(Card card);
    public ArrayNode getCards();
}
