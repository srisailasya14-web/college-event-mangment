package college.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationDAO {
    public boolean registerStudentForEvent(int studentId, int eventId) throws SQLException {
        String sql = "INSERT INTO registrations (student_id, event_id) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, eventId);
            return statement.executeUpdate() > 0;
        }
    }
}
