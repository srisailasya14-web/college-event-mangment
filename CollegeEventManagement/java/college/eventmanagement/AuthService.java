package college.eventmanagement;

import java.sql.SQLException;

public class AuthService {
    private final StudentDAO studentDAO = new StudentDAO();

    public boolean registerStudent(String name, String rollNumber, String department, int year,
                                   String email, String phone, String password) throws SQLException {
        return studentDAO.insertStudent(name, rollNumber, department, year, email, phone, password);
    }

    public boolean loginStudent(String email, String password) throws SQLException {
        return studentDAO.authenticateStudent(email, password);
    }
}
