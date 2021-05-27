package org.misha.bankapi;

import com.sun.net.httpserver.HttpServer;
import org.misha.bankapi.controller.*;
import org.misha.bankapi.db.DAO.AccountDAOImpl;
import org.misha.bankapi.db.DAO.CardDAOImpl;
import org.misha.bankapi.db.DBInitializer;
import org.misha.bankapi.service.AccountService;
import org.misha.bankapi.service.AccountServiceImpl;
import org.misha.bankapi.service.CardService;
import org.misha.bankapi.service.CardServiceImpl;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        DBInitializer.init();
        ServerStarter.start();
    }


    public static class ServerStarter {
        public static void start() throws IOException {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            AccountService accountService = new AccountServiceImpl(new AccountDAOImpl());
            CardService cardService = new CardServiceImpl(new CardDAOImpl());

            // TODO: 18.05.2021 Проверять в контроллерах что приходит в запросе (метод, headers)
            server.createContext("/card/order", new CardHandler(cardService));
            server.createContext("/card/view", new CardHandler(cardService));
            server.createContext("/account/pay", new AccountHandler(accountService));
            server.createContext("/account/balance", new AccountHandler(accountService));

            server.start();
        }
    }
}

