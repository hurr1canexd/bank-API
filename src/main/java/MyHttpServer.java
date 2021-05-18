import com.sun.net.httpserver.HttpServer;
import controller.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        ServerStarter.start();
    }


    public static class ServerStarter {
        public static void start() throws IOException {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            // TODO: 18.05.2021 Проверять в контроллерах что приходит в запросе (метод, headers)
            server.createContext("/order", new IssueCardHandler());
            server.createContext("/cards", new ViewCardsHandler());
            server.createContext("/pay", new MakeDepositHandler());
            server.createContext("/balance", new CheckBalanceHandler());

            server.start();
        }
    }
}

