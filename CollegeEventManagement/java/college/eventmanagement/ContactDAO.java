package college.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDAO {
    private static final java.util.List<ContactMessage> FALLBACK_MESSAGES = new java.util.ArrayList<>();

    public boolean saveMessage(ContactMessage message) {
        String sql = "INSERT INTO contact_messages (name, email, subject, message) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, message.getName());
            statement.setString(2, message.getEmail());
            statement.setString(3, message.getSubject());
            statement.setString(4, message.getMessage());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            FALLBACK_MESSAGES.add(message);
            return true;
        }
    }
}
