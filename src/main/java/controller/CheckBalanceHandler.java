package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.AccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class CheckBalanceHandler implements HttpHandler, ResponseSender {
    private final AccountService accountService;

    public CheckBalanceHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] response;

        // Get an account number from URL
        String accountNumber = exchange.getRequestURI().getQuery().split("=")[1];

        BigDecimal balance = accountService.getAccountBalance(accountNumber);
//        if (balance == null) {
//            System.out.println("cjidoijcdon");
//        }

        // If all works correctly
        response = balance.toPlainString().getBytes(StandardCharsets.UTF_8);
        sendResponse(exchange, 200, response);
    }
}
