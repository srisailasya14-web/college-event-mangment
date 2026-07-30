package college.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDAO {
    public boolean saveMessage(String name, String email, String subject, String message) throws SQLException {
        String sql = "INSERT INTO contact_messages (name, email, subject, message) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, subject);
            statement.setString(4, message);
            return statement.executeUpdate() > 0;
        }
    }
}
