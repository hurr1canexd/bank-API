package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Card;
import service.CardService;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class IssueCardHandler implements HttpHandler {
    private final CardService cardService;

    public IssueCardHandler(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO: 18.05.2021 генерировать cvc код?

        // Get java model from JSON
        Card card = new ObjectMapper().readValue(exchange.getRequestBody(), Card.class);

        cardService.insertCardInDatabase(card); // в try catch

        // Send response
        OutputStream os = exchange.getResponseBody();
        byte[] response = "Card added.".getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, response.length);
        os.write(response);
        os.close();
    }
}
