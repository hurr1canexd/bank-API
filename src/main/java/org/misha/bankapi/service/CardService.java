package org.misha.bankapi.service;

import org.misha.bankapi.model.CardInfo;
import org.misha.bankapi.model.CardRequest;

import java.sql.SQLException;
import java.util.List;

public interface CardService {
    void insertCardInDatabase(CardRequest cardRequest) throws SQLException;
    List<CardInfo> getCards() throws SQLException;
}
