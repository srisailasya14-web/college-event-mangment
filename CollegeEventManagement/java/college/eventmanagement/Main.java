package college.eventmanagement;

import java.util.List;

/**
 * Main entry point for demo operations and backend verification.
 */
public class Main {
    public static void main(String[] args) {
        try {
            StudentDAO studentDAO = new StudentDAO();
            EventDAO eventDAO = new EventDAO();
            ContactDAO contactDAO = new ContactDAO();
            AdminDAO adminDAO = new AdminDAO();

            Student sampleStudent = new Student(0, "Aarav Sharma", "CS101", "Computer Science", 2, "aarav@college.edu", "9876543210", "Password@123");
            studentDAO.insertStudent(sampleStudent);

            List<Student> students = studentDAO.getAllStudents();
            List<Event> events = eventDAO.getAllEvents();

            System.out.println("College Event Management System is running.");
            System.out.println("Total students: " + students.size());
            System.out.println("Total events: " + events.size());
            System.out.println("Admin login valid: " + adminDAO.authenticate("admin@campussphere.edu", "admin123"));

            ContactMessage message = new ContactMessage("Riya Kumar", "riya@college.edu", "Query", "Hello from Java backend");
            contactDAO.saveMessage(message);

            System.out.println("Student count: " + adminDAO.getStudentCount());
            System.out.println("Event count: " + adminDAO.getEventCount());
            System.out.println("Demo mode enabled because MySQL JDBC driver is not configured in this environment.");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
