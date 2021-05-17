import com.sun.net.httpserver.HttpServer;
import controller.CheckBalanceHandler;
import controller.IssueCardHandler;
import controller.MakeDepositHandler;
import controller.ViewCardsHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/order", new IssueCardHandler());
        server.createContext("/cards", new ViewCardsHandler());
        server.createContext("/pay", new MakeDepositHandler());
        server.createContext("/balance", new CheckBalanceHandler());

        server.start();
    }
}
