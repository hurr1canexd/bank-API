package org.misha.bankapi.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.misha.bankapi.model.CardInfo;
import org.misha.bankapi.model.CardRequest;
import org.misha.bankapi.service.CardService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class CardHandler implements HttpHandler, ResponseSender {
    private final CardService cardService;

    public CardHandler(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // GET method
        if (exchange.getRequestMethod().equals("GET")) {
            // View all cards
            if (exchange.getRequestURI().getPath().equals("/card/view")) {
                byte[] response;
                List<CardInfo> infos;
                try {
                    infos = cardService.getCards();
                } catch (SQLException ex) {
                    response = "Can not get cards.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 500, response);
                    return;
                }

                // If all works correctly
                // TODO: отправлять как-то нормальный JSON сразу не перегоняя в ArrayNode
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode arr = mapper.createArrayNode();
                for (CardInfo info : infos) {
                    ObjectNode node = mapper.createObjectNode();
                    node.put("id", info.getId());
                    node.put("number", info.getNumber());
                    arr.add(node);
                }
                response = arr.toPrettyString().getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 200, response);
            }
        }
        // POST method
        if (exchange.getRequestMethod().equals("POST")) {
            // Add new card
            if (exchange.getRequestURI().getPath().equals("/card/order")) {
                byte[] response;

                try {
                    // Get java model from JSON
                    InputStream requestBody = exchange.getRequestBody();
                    CardRequest cardRequest = new ObjectMapper().readValue(requestBody, CardRequest.class);
                    cardService.insertCardInDatabase(cardRequest);
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
                    sendResponse(exchange, 404, response);
                    return;
                } catch (SQLException ex) {
                    response = ex.getMessage().getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 500, response);
                    return;
                }

                // If all works correctly
                response = "Card added.".getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 201, response);

            }
        } else {
            sendResponse(exchange, 400, "Incorrect method".getBytes(StandardCharsets.UTF_8));
        }
    }
}
