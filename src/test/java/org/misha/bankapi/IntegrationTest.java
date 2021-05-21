package org.misha.bankapi;

import com.sun.net.httpserver.HttpServer;
import org.junit.Assert;
import org.junit.Test;
import org.misha.bankapi.db.DBInitializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IntegrationTest {
    @Test
    public void issueCardTest() throws IOException {
        DBInitializer.init();
        Main.ServerStarter.start();

        final URL url = new URL("http://localhost:8080/order");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;");
        String jsonString = "{\n" +
                "  \"number\": \"42024305346286324586\",\n" +
                "  \"month\": \"07\",\n" +
                "  \"year\": \"2028\",\n" +
                "  \"code\": \"353\",\n" +
                "  \"balance\": 0,\n" +
                "  \"accountId\": 1\n" +
                "}";
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        String actual;

        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {

            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            actual = response.toString();
        }

        String expected = "Card added.";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void viewCardsTest() throws IOException {
        DBInitializer.init();
        Main.ServerStarter.start();

        final URL url = new URL("http://localhost:8080/cards");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

        final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        final StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        String expected = "[ {  \"id\" : 1,  \"number\" : \"42024305346286324576\"} ]";
        Assert.assertEquals(expected, content.toString());
    }

    @Test
    public void makeDepositTest() throws IOException {
        DBInitializer.init();
        Main.ServerStarter.start();

        final URL url = new URL("http://localhost:8080/pay");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;");
        String jsonString = "{\n" +
                "  \"number\": \"40804810200003497183\",\n" +
                "  \"sum\" : 100\n" +
                "}";
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        String actual;

        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {

            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            actual = response.toString();
        }

        String expected = "Balance replenished.";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkBalanceTest() throws IOException {
        DBInitializer.init();
        Main.ServerStarter.start();

        final URL url = new URL("http://localhost:8080/balance?number=40804810200003497183");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

        final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        final StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        String expected = "0";
        Assert.assertEquals(expected, content.toString());
    }

}
