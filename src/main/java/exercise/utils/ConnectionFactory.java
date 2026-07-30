package exercise.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static ConnectionFactory instance;

    private final String url;
    private final String username;
    private final String password;

    private ConnectionFactory() {
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("DB_URL");
        this.username = dotenv.get("DB_USER");
        this.password = dotenv.get("DB_PASSWORD");

        if (this.url == null || this.username == null || this.password == null) {
            throw new IllegalStateException(
                    "Critical Error: Database .env values (DB_URL, DB_USER, DB_PASSWORD) are not configured."
            );
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC driver not found on classpath.", e);
        }
    }

    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

