package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.AccountService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

public class CheckBalanceHandler implements HttpHandler {
    AccountService accountService;

    public CheckBalanceHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Get an account number from URL
        String accountNumber = exchange.getRequestURI().getQuery().split("=")[1];

        BigDecimal balance = accountService.getAccountBalance(accountNumber);

        // Send response
        OutputStream os = exchange.getResponseBody();
        // TODO: 20.05.2021 поправить костыль через строку 
//        byte[] response = balance.unscaledValue().toByteArray();
        byte[] response = balance.toString().getBytes();
        exchange.sendResponseHeaders(200, response.length);
        os.write(response);
        os.close();
    }
}
