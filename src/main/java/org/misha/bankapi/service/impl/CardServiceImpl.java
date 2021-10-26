package org.misha.bankapi.service.impl;

import org.misha.bankapi.db.DAO.CardDAO;
import org.misha.bankapi.exception.SuchCardExistsException;
import org.misha.bankapi.model.Card;
import org.misha.bankapi.model.CardInfo;
import org.misha.bankapi.model.CardRequest;
import org.misha.bankapi.service.CardService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CardServiceImpl implements CardService {
    private final CardDAO cardDAO;

    public CardServiceImpl(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    @Override
    public void insertCardInDatabase(CardRequest cardRq) throws SuchCardExistsException {
        // TODO: 27.05.2021 Map CardRequest to Card
        Card card = new Card(
                cardRq.getNumber(),
                cardRq.getMonth(),
                cardRq.getYear(),
                "123", // TODO: set random pin from service
                cardRq.getBalance(),
                cardRq.getAccountId()
        );
        if (!cardExists(card)) {
            throw new SuchCardExistsException();
        }
        // Then set generated fields from service:
        // card.setPin(generatePin());
        // TODO: check if same card exists
        cardDAO.create(card);
    }

    @Override
    public List<CardInfo> getCards() {
        List<Card> cards = cardDAO.getCards();
        return cards.stream()
                .map(card -> new CardInfo(card.getId(), card.getNumber()))
                .collect(Collectors.toList());
    }

    private boolean cardExists(Card card) {
        return cardDAO.getCards().contains(card);
    }
}
