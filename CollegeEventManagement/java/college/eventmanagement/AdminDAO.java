package college.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public boolean authenticate(String email, String password) {
        return "admin@campussphere.edu".equals(email) && "admin123".equals(password);
    }

    public int getStudentCount() {
        String sql = "SELECT COUNT(*) AS count FROM students";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException exception) {
            return new StudentDAO().getAllStudents().size();
        }
        return 0;
    }

    public int getEventCount() {
        String sql = "SELECT COUNT(*) AS count FROM events";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException exception) {
            return new EventDAO().getAllEvents().size();
        }
        return 0;
    }
}
