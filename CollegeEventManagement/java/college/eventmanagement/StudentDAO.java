package college.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public boolean insertStudent(String name, String rollNumber, String department, int year,
                                 String email, String phone, String password) throws SQLException {
        String sql = "INSERT INTO students (name, roll_number, department, year, email, phone, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, rollNumber);
            statement.setString(3, department);
            statement.setInt(4, year);
            statement.setString(5, email);
            statement.setString(6, phone);
            statement.setString(7, password);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean authenticateStudent(String email, String password) throws SQLException {
        String sql = "SELECT 1 FROM students WHERE email = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public List<String[]> getAllStudents() throws SQLException {
        List<String[]> students = new ArrayList<>();
        String sql = "SELECT student_id, name, roll_number, department, year, email FROM students ORDER BY student_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                students.add(new String[]{
                        String.valueOf(resultSet.getInt("student_id")),
                        resultSet.getString("name"),
                        resultSet.getString("roll_number"),
                        resultSet.getString("department"),
                        String.valueOf(resultSet.getInt("year")),
                        resultSet.getString("email")
                });
            }
        }
        return students;
    }
}
