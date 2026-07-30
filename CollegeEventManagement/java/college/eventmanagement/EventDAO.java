package college.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private static final List<Event> FALLBACK_EVENTS = new ArrayList<>();

    public boolean addEvent(Event event) {
        String sql = "INSERT INTO events (event_name, description, event_date, venue, available_seats, category) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, event.getEventName());
            statement.setString(2, event.getDescription());
            statement.setDate(3, new java.sql.Date(event.getEventDate().getTime()));
            statement.setString(4, event.getVenue());
            statement.setInt(5, event.getAvailableSeats());
            statement.setString(6, event.getCategory());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            FALLBACK_EVENTS.add(event);
            return true;
        }
    }

    public boolean updateEvent(Event event) {
        String sql = "UPDATE events SET event_name = ?, description = ?, event_date = ?, venue = ?, available_seats = ?, category = ? WHERE event_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, event.getEventName());
            statement.setString(2, event.getDescription());
            statement.setDate(3, new java.sql.Date(event.getEventDate().getTime()));
            statement.setString(4, event.getVenue());
            statement.setInt(5, event.getAvailableSeats());
            statement.setString(6, event.getCategory());
            statement.setInt(7, event.getEventId());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            for (int index = 0; index < FALLBACK_EVENTS.size(); index++) {
                if (FALLBACK_EVENTS.get(index).getEventId() == event.getEventId()) {
                    FALLBACK_EVENTS.set(index, event);
                    return true;
                }
            }
            FALLBACK_EVENTS.add(event);
            return true;
        }
    }

    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            FALLBACK_EVENTS.removeIf(event -> event.getEventId() == eventId);
            return true;
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events ORDER BY event_date ASC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                events.add(new Event(
                    resultSet.getInt("event_id"),
                    resultSet.getString("event_name"),
                    resultSet.getString("description"),
                    resultSet.getDate("event_date"),
                    resultSet.getString("venue"),
                    resultSet.getInt("available_seats"),
                    resultSet.getString("category")
                ));
            }
            return events;
        } catch (SQLException exception) {
            return new ArrayList<>(FALLBACK_EVENTS);
        }
    }
}
