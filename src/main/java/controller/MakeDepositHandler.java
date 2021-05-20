package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.AccountService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class MakeDepositHandler implements HttpHandler {
    AccountService accountService;

    public MakeDepositHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Get String JSON
        String stringRequestBody = getStringRequestBody(exchange);

        ObjectNode node = new ObjectMapper().readValue(stringRequestBody, ObjectNode.class);
        String accountNumber = node.get("number").asText();
        BigDecimal sum = node.get("sum").decimalValue();

        accountService.topUpAccountBalance(accountNumber, sum);

        // Send response
        OutputStream os = exchange.getResponseBody();
        byte[] response = "Balance replenished.".getBytes(StandardCharsets.UTF_8);
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
