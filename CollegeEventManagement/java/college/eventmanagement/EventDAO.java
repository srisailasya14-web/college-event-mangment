package college.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    public List<String[]> getAllEvents() throws SQLException {
        List<String[]> events = new ArrayList<>();
        String sql = "SELECT event_id, event_name, description, event_date, venue, available_seats, category FROM events ORDER BY event_date";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                events.add(new String[]{
                        String.valueOf(resultSet.getInt("event_id")),
                        resultSet.getString("event_name"),
                        resultSet.getString("description"),
                        resultSet.getDate("event_date").toString(),
                        resultSet.getString("venue"),
                        String.valueOf(resultSet.getInt("available_seats")),
                        resultSet.getString("category")
                });
            }
        }
        return events;
    }
}
