package college.eventmanagement;

import java.util.Date;

public class Registration {
    private int registrationId;
    private int studentId;
    private int eventId;
    private Date registrationDate;

    public Registration() {
    }

    public Registration(int registrationId, int studentId, int eventId, Date registrationDate) {
        this.registrationId = registrationId;
        this.studentId = studentId;
        this.eventId = eventId;
        this.registrationDate = registrationDate;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getEventId() {
        return eventId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
