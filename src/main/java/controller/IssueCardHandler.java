package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Card;
import service.CardService;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class IssueCardHandler implements HttpHandler {
    CardService cardService = new CardService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO: 18.05.2021 делегировать логику в другой класс?
        // TODO: 18.05.2021 генерировать cvc код?

        String stringRequestBody = getStringRequestBody(exchange);

        // Получаем java модель из json
        ObjectMapper mapper = new ObjectMapper();
        Card card = mapper.readValue(stringRequestBody, Card.class);
        System.out.println(card);
    }

    private String getStringRequestBody(HttpExchange exchange) {
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
