package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.AccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class CheckBalanceHandler implements HttpHandler, ResponseSender {
    private final AccountService accountService;

    public CheckBalanceHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] response;

        // Get an account number from URL
        String[] arr = exchange.getRequestURI().getQuery().split("=");
        if (arr.length < 2) {
            response = "Incorrect query.".getBytes(StandardCharsets.UTF_8);
            sendResponse(exchange, 400, response);
            return;
        }
        if (!arr[0].equals("number")) {
            response = "Wrong parameter name.".getBytes(StandardCharsets.UTF_8);
            sendResponse(exchange, 400, response);
            return;
        }

        BigDecimal balance;
        String accountNumber = arr[1];
        try {
            balance = accountService.getAccountBalance(accountNumber);
        } catch (SQLException ex) {
            response = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            sendResponse(exchange, 400, response);
            return;
        }
        if (balance == null) {
            sendResponse(exchange, 400, "Card doesn't exist".getBytes(StandardCharsets.UTF_8));
            return;
        }

        // If all works correctly
        response = balance.toPlainString().getBytes(StandardCharsets.UTF_8);
        sendResponse(exchange, 200, response);
    }
}
