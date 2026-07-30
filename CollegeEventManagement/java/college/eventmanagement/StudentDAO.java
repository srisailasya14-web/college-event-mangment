package college.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final List<Student> FALLBACK_STUDENTS = new ArrayList<>();

    public boolean insertStudent(Student student) {
        String sql = "INSERT INTO students (name, roll_number, department, year, email, phone, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getRollNumber());
            statement.setString(3, student.getDepartment());
            statement.setInt(4, student.getYear());
            statement.setString(5, student.getEmail());
            statement.setString(6, student.getPhone());
            statement.setString(7, student.getPassword());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            FALLBACK_STUDENTS.add(student);
            return true;
        }
    }

    public Student authenticate(String email, String password) {
        String sql = "SELECT * FROM students WHERE email = ? AND password = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Student(
                        resultSet.getInt("student_id"),
                        resultSet.getString("name"),
                        resultSet.getString("roll_number"),
                        resultSet.getString("department"),
                        resultSet.getInt("year"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException exception) {
            for (Student student : FALLBACK_STUDENTS) {
                if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                    return student;
                }
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY student_id";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                students.add(new Student(
                    resultSet.getInt("student_id"),
                    resultSet.getString("name"),
                    resultSet.getString("roll_number"),
                    resultSet.getString("department"),
                    resultSet.getInt("year"),
                    resultSet.getString("email"),
                    resultSet.getString("phone"),
                    resultSet.getString("password")
                ));
            }
            return students;
        } catch (SQLException exception) {
            return new ArrayList<>(FALLBACK_STUDENTS);
        }
    }
}
