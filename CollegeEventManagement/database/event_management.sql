-- College Event Management System
-- MySQL database script for students, events, registrations, and contact messages

CREATE DATABASE IF NOT EXISTS college_event_management;
USE college_event_management;

-- Students table
CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    roll_number VARCHAR(30) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- Events table
CREATE TABLE IF NOT EXISTS events (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    event_name VARCHAR(120) NOT NULL,
    description TEXT NOT NULL,
    event_date DATE NOT NULL,
    venue VARCHAR(150) NOT NULL,
    available_seats INT NOT NULL DEFAULT 100
) ENGINE=InnoDB;

-- Registrations table
CREATE TABLE IF NOT EXISTS registrations (
    registration_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    event_id INT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reg_student FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    CONSTRAINT fk_reg_event FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Contact messages table
CREATE TABLE IF NOT EXISTS contact_messages (
    message_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    subject VARCHAR(150) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ALTER TABLE example
ALTER TABLE events
ADD COLUMN category VARCHAR(50) NOT NULL DEFAULT 'General';

-- INSERT statements
INSERT INTO students (name, roll_number, department, year, email, phone, password)
VALUES
('Aarav Sharma', 'CS101', 'Computer Science', 2, 'aarav@college.edu', '9876543210', 'Password@123'),
('Meera Patel', 'EE205', 'Electrical', 3, 'meera@college.edu', '9123456780', 'Secure@456');

INSERT INTO events (event_name, description, event_date, venue, available_seats, category)
VALUES
('Tech Fest 2026', 'A three-day technology showcase with hackathons and coding challenges.', '2026-09-15', 'Main Auditorium', 120, 'Technical'),
('Cultural Night', 'An evening of dance, music, and drama performances.', '2026-10-02', 'Open Air Theatre', 80, 'Cultural'),
('Sports Meet', 'Inter-department sports events, relay races, and team games.', '2026-10-20', 'Sports Complex', 150, 'Sports');

INSERT INTO registrations (student_id, event_id)
VALUES
(1, 1),
(2, 2);

INSERT INTO contact_messages (name, email, subject, message)
VALUES
('Riya Kumar', 'riya@college.edu', 'Event Schedule', 'Could you please share the final schedule for Tech Fest?');

-- UPDATE statement
UPDATE events
SET available_seats = available_seats - 1
WHERE event_id = 1;

-- DELETE statement
DELETE FROM contact_messages
WHERE message_id = 1;

-- SELECT statements
SELECT * FROM students;
SELECT * FROM events;
SELECT * FROM registrations;
SELECT * FROM contact_messages;

-- WHERE clause
SELECT * FROM students WHERE department = 'Computer Science';

-- ORDER BY, LIMIT, OFFSET
SELECT * FROM events ORDER BY event_date ASC LIMIT 2 OFFSET 0;

-- GROUP BY, COUNT, SUM, AVG, MAX, MIN
SELECT category, COUNT(*) AS event_count FROM events GROUP BY category;
SELECT SUM(available_seats) AS total_seats FROM events;
SELECT AVG(available_seats) AS average_seats FROM events;
SELECT MAX(available_seats) AS max_seats FROM events;
SELECT MIN(available_seats) AS min_seats FROM events;

-- INNER JOIN
SELECT s.name, s.roll_number, e.event_name
FROM students s
INNER JOIN registrations r ON s.student_id = r.student_id
INNER JOIN events e ON r.event_id = e.event_id;

-- LEFT JOIN
SELECT e.event_name, COUNT(r.registration_id) AS registration_count
FROM events e
LEFT JOIN registrations r ON e.event_id = r.event_id
GROUP BY e.event_id, e.event_name;

-- View example
CREATE VIEW student_registration_summary AS
SELECT s.student_id, s.name, s.roll_number, e.event_name, r.registration_date
FROM students s
INNER JOIN registrations r ON s.student_id = r.student_id
INNER JOIN events e ON r.event_id = e.event_id;

SELECT * FROM student_registration_summary;
