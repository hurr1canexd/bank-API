package org.misha.bankapi.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.misha.bankapi.model.Card;
import org.misha.bankapi.service.CardService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class IssueCardHandler implements HttpHandler, ResponseSender {
    private final CardService cardService;

    public IssueCardHandler(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] response;

        try {
            Card card = new ObjectMapper().readValue(exchange.getRequestBody(), Card.class); // Get java model from JSON
            cardService.insertCardInDatabase(card);
        } catch (JsonMappingException ex) {
            response = "Wrong number of parameters.".getBytes(StandardCharsets.UTF_8);
            sendResponse(exchange, 400, response);
            return;
        } catch (JsonParseException ex) {
            response = "Incorrect data.".getBytes(StandardCharsets.UTF_8);
            sendResponse(exchange, 400, response);
            return;
        } catch (JdbcSQLIntegrityConstraintViolationException ex) {
            response = "Card with this number doesn't exists.".getBytes(StandardCharsets.UTF_8);
            sendResponse(exchange, 400, response);
            return;
        } catch (SQLException ex) {
            response = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            sendResponse(exchange, 400, response);
            return;
        }

        // If all works correctly
        response = "Card added.".getBytes(StandardCharsets.UTF_8);
        sendResponse(exchange, 200, response);
    }
}
