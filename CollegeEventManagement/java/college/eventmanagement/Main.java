package college.eventmanagement;

public class Main {
    public static void main(String[] args) {
        try {
            if (DBConnection.testConnection()) {
                System.out.println("Database connection successful.");
            } else {
                System.out.println("Database connection failed.");
            }

            StudentDAO studentDAO = new StudentDAO();
            System.out.println("Student DAO ready: " + studentDAO.getClass().getName());

            EventDAO eventDAO = new EventDAO();
            System.out.println("Event DAO ready: " + eventDAO.getClass().getName());
        } catch (Exception ex) {
            System.out.println("Startup error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
