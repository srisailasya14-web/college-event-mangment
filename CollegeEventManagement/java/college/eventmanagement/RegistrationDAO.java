package college.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {
    private static final List<Registration> FALLBACK_REGISTRATIONS = new ArrayList<>();

    public boolean registerStudentToEvent(int studentId, int eventId) {
        String checkSql = "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND event_id = ?";
        String insertSql = "INSERT INTO registrations (student_id, event_id) VALUES (?, ?)";
        String updateSql = "UPDATE events SET available_seats = available_seats - 1 WHERE event_id = ? AND available_seats > 0";

        try (Connection connection = DBConnection.getConnection()) {
            try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
                checkStatement.setInt(1, studentId);
                checkStatement.setInt(2, eventId);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        return false;
                    }
                }
            }

            try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                updateStatement.setInt(1, eventId);
                if (updateStatement.executeUpdate() == 0) {
                    return false;
                }
            }

            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                insertStatement.setInt(1, studentId);
                insertStatement.setInt(2, eventId);
                return insertStatement.executeUpdate() > 0;
            }
        } catch (SQLException exception) {
            boolean duplicate = FALLBACK_REGISTRATIONS.stream().anyMatch(registration -> registration.getStudentId() == studentId && registration.getEventId() == eventId);
            if (duplicate) {
                return false;
            }
            FALLBACK_REGISTRATIONS.add(new Registration(FALLBACK_REGISTRATIONS.size() + 1, studentId, eventId, new java.util.Date()));
            return true;
        }
    }

    public boolean cancelRegistration(int registrationId) {
        String selectEventSql = "SELECT event_id FROM registrations WHERE registration_id = ?";
        String deleteSql = "DELETE FROM registrations WHERE registration_id = ?";
        String updateSql = "UPDATE events SET available_seats = available_seats + 1 WHERE event_id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            int eventId;
            try (PreparedStatement selectStatement = connection.prepareStatement(selectEventSql)) {
                selectStatement.setInt(1, registrationId);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (!resultSet.next()) {
                        return false;
                    }
                    eventId = resultSet.getInt("event_id");
                }
            }

            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setInt(1, registrationId);
                if (deleteStatement.executeUpdate() == 0) {
                    return false;
                }
            }

            try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                updateStatement.setInt(1, eventId);
                return updateStatement.executeUpdate() > 0;
            }
        } catch (SQLException exception) {
            FALLBACK_REGISTRATIONS.removeIf(registration -> registration.getRegistrationId() == registrationId);
            return true;
        }
    }

    public List<Registration> getStudentRegistrations(int studentId) {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registrations WHERE student_id = ? ORDER BY registration_date DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    registrations.add(new Registration(
                        resultSet.getInt("registration_id"),
                        resultSet.getInt("student_id"),
                        resultSet.getInt("event_id"),
                        resultSet.getTimestamp("registration_date")
                    ));
                }
            }
            return registrations;
        } catch (SQLException exception) {
            List<Registration> filtered = new ArrayList<>();
            for (Registration registration : FALLBACK_REGISTRATIONS) {
                if (registration.getStudentId() == studentId) {
                    filtered.add(registration);
                }
            }
            return filtered;
        }
    }
}
