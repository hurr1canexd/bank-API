package controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.CardService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ViewCardsHandler implements HttpHandler {
    private final CardService cardService;

    public ViewCardsHandler(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Get cards as array of JSON objects
        ArrayNode arr = cardService.getCards();

        // Send response
        OutputStream os = exchange.getResponseBody();
        String result = arr.toPrettyString();
        byte[] response = result.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, response.length);
        os.write(response);
        os.close();
    }
}
