package org.misha.bankapi.service;

import org.misha.bankapi.exception.SuchCardExistsException;
import org.misha.bankapi.model.CardInfo;
import org.misha.bankapi.model.CardRequest;

import java.util.List;

public interface CardService {
    void insertCardInDatabase(CardRequest cardRequest) throws SuchCardExistsException;
    List<CardInfo> getCards();
}
