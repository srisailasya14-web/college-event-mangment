package college.eventmanagement;

import java.util.Date;

public class Event {
    private int eventId;
    private String eventName;
    private String description;
    private Date eventDate;
    private String venue;
    private int availableSeats;
    private String category;

    public Event() {
    }

    public Event(int eventId, String eventName, String description, Date eventDate, String venue, int availableSeats, String category) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.eventDate = eventDate;
        this.venue = venue;
        this.availableSeats = availableSeats;
        this.category = category;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDescription() {
        return description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public String getCategory() {
        return category;
    }
}
