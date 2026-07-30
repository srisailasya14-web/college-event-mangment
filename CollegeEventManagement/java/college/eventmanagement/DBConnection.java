package college.eventmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DBConnection {
    private static final String DB_NAME = "college_event_management";
    private static final List<String> URL_CANDIDATES = Arrays.asList(
            "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            "jdbc:mysql://127.0.0.1:3306/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
    );

    private static final List<String> USER_CANDIDATES = Arrays.asList("root");
    private static final List<String> PASSWORD_CANDIDATES = Arrays.asList("");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        SQLException lastError = null;

        for (String url : URL_CANDIDATES) {
            for (String user : USER_CANDIDATES) {
                for (String password : PASSWORD_CANDIDATES) {
                    try {
                        Connection connection = DriverManager.getConnection(url, user, password);
                        if (connection != null && !connection.isClosed()) {
                            return connection;
                        }
                    } catch (SQLException ex) {
                        lastError = ex;
                        System.out.println("Attempt failed for user '" + user + "' at " + url + " -> " + ex.getMessage());
                    }
                }
            }
        }

        if (lastError != null) {
            throw lastError;
        }

        throw new SQLException("Unable to connect to MySQL. Verify that the server is running and the credentials are correct.");
    }

    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException ex) {
            System.out.println("Connection test failed: " + ex.getMessage());
            return false;
        }
    }
}
