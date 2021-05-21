package controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.CardService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ViewCardsHandler implements HttpHandler, ResponseSender {
    private final CardService cardService;

    public ViewCardsHandler(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] response;

        // Get cards as array of JSON objects
        ArrayNode arr = cardService.getCards();

        // If all works correctly
        response = arr.toPrettyString().getBytes(StandardCharsets.UTF_8);
        sendResponse(exchange, 200, response);
    }
}
