package college.eventmanagement;

public class Student {
    private int studentId;
    private String name;
    private String rollNumber;
    private String department;
    private int year;
    private String email;
    private String phone;
    private String password;

    public Student() {
    }

    public Student(int studentId, String name, String rollNumber, String department, int year, String email, String phone, String password) {
        this.studentId = studentId;
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.year = year;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public int getYear() {
        return year;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
