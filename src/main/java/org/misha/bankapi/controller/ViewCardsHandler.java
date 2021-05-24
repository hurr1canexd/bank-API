package org.misha.bankapi.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.misha.bankapi.service.CardService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class ViewCardsHandler implements HttpHandler, ResponseSender {
    private final CardService cardService;

    public ViewCardsHandler(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] response;
        ArrayNode arr;

        // Get cards as array of JSON objects
        try {
            arr = cardService.getCards();
        } catch (SQLException ex) {
            response = "Can not get cards.".getBytes(StandardCharsets.UTF_8);
            sendResponse(exchange, 400, response);
            return;
        }

        // If all works correctly
        response = arr.toPrettyString().getBytes(StandardCharsets.UTF_8);
        sendResponse(exchange, 200, response);
    }
}
