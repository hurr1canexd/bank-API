package org.misha.bankapi.db.DAO;

import org.misha.bankapi.model.Card;

import java.util.List;


public interface CardDAO {
    void create(Card card);
    List<Card> getCards();
}
