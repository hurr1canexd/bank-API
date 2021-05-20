import com.sun.net.httpserver.HttpServer;
import db.DAO.*;
import controller.*;
import service.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        ServerStarter.start();
    }


    public static class ServerStarter {
        public static void start() throws IOException {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            ClientService clientService = new ClientServiceImpl();
            AccountService accountService = new AccountServiceImpl(new AccountDAOImpl());
            CardService cardService = new CardServiceImpl(new CardDAOImpl());

            // TODO: 18.05.2021 Проверять в контроллерах что приходит в запросе (метод, headers)
            server.createContext("/order", new IssueCardHandler(cardService));
            server.createContext("/cards", new ViewCardsHandler(cardService));
            server.createContext("/pay", new MakeDepositHandler(accountService));
            server.createContext("/balance", new CheckBalanceHandler(accountService));

            server.start();
        }
    }
}

