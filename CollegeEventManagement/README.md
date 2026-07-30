# College Event Management System

A complete full-stack college event management application using HTML, CSS, JavaScript, Java, MySQL, and JDBC.

## Features
- Responsive home page with hero section, featured events, about, contact, and footer
- Student registration and login
- Student dashboard with overview cards
- Event browsing with search, filter, and sort
- Registration and cancellation flow
- Admin login and management support
- MySQL database schema with CRUD-ready SQL scripts
- Java DAO layer with JDBC prepared statements

## Project Structure
- index.html
- login.html
- register.html
- dashboard.html
- events.html
- my-registrations.html
- contact.html
- css/
- js/
- java/
- database/

## Setup Instructions
1. Create a MySQL database named college_event_management.
2. Run the SQL script in database/event_management.sql.
3. Update the database credentials in java/DBConnection.java if necessary.
4. Open the HTML pages in a browser, or serve the folder using a local web server.
5. Compile and run the Java backend using:
   - javac java/*.java
   - java java.Main

## Notes
- The frontend uses localStorage for demo flow and interactions.
- The Java backend uses JDBC and PreparedStatement for database operations.
- Admin login credentials: admin@campussphere.edu / admin123
