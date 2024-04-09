package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

public class DatabaseIntegrationTest extends IntegrationTest {
    @Test
    @Transactional
    @Rollback
    void dataBaseCreateTest() throws SQLException {
        Assertions.assertTrue(POSTGRES.isRunning());

        Connection connection = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),
            POSTGRES.getPassword()
        );

        DatabaseMetaData databaseMetaData = connection.getMetaData();

        ResultSet resultSet = databaseMetaData.getTables(
            null,
            null,
            null,
            new String[] {"TABLE"}
        );

        Set<String> tables = new HashSet<>();

        while (resultSet.next()) {
            tables.add(resultSet.getString("TABLE_NAME"));
        }

        Assertions.assertTrue(tables.contains("chat"));
        Assertions.assertTrue(tables.contains("chat_link"));
        Assertions.assertTrue(tables.contains("link"));
    }

    @Test
    @Transactional
    @Rollback
    void chatsInsertTest() throws SQLException {
        Assertions.assertTrue(POSTGRES.isRunning());

        Connection connection = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),
            POSTGRES.getPassword()
        );

        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO chat (id) VALUES (1)");

        ResultSet resultSet = statement.executeQuery("SELECT id FROM chat");

        resultSet.next();
        Assertions.assertEquals(
            1,
            resultSet.getInt("id")
        );
    }

    @Test
    @Transactional
    @Rollback
    void linksInsertTest() throws SQLException {
        Assertions.assertTrue(POSTGRES.isRunning());

        Connection connection = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),
            POSTGRES.getPassword()
        );

        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO link (uri) VALUES ('somelink.com')");

        ResultSet resultSet = statement.executeQuery("SELECT uri FROM link");

        resultSet.next();
        Assertions.assertEquals(
            "somelink.com",
            resultSet.getString("uri")
        );
    }
}
