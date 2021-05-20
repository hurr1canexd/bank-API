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
        // TODO: 19.05.2021 отдать сервису маппинг?

        // Get String JSON
        String stringRequestBody = getStringRequestBody(exchange);

        // Get java model from JSON
        Card card = new ObjectMapper().readValue(stringRequestBody, Card.class);
        System.out.println(card);

        cardService.insertCardInDatabase(card);

        // Send response
        OutputStream os = exchange.getResponseBody();
        byte[] response = "Card added.".getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, response.length);
        os.write(response);
        os.close();
    }

    private static String getStringRequestBody(HttpExchange exchange) {
        StringBuilder buf = new StringBuilder(512);
        try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr);) {
            int b;
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf.toString();
    }
}
