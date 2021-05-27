package org.misha.bankapi.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private static final String createScriptPath = "src/main/resources/scripts/create_db.sql";
    private static final String fillDbScriptPath = "src/main/resources/scripts/fill_db.sql";

    public static void init() throws IOException {
        String createQuery = Files.readString(Path.of(createScriptPath),
                StandardCharsets.US_ASCII);
        String fillQuery = Files.readString(Path.of(fillDbScriptPath),
                StandardCharsets.US_ASCII);

        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createQuery);
            statement.execute(fillQuery);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
