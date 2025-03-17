import java.sql.*;
import java.util.Scanner;

public class StudentApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test_db";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n1. Add Student\n2. View Students\n3. Delete Student\n4. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = scanner.nextLine();
                        System.out.print("Enter Marks: ");
                        int marks = scanner.nextInt();

                        String insertSQL = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                            pstmt.setInt(1, id);
                            pstmt.setString(2, name);
                            pstmt.setString(3, dept);
                            pstmt.setInt(4, marks);
                            pstmt.executeUpdate();
                            System.out.println("Student added successfully.");
                        }
                        break;

                    case 2:
                        String sql = "SELECT * FROM Student";
                        try (Statement stmt = conn.createStatement();
                             ResultSet rs = stmt.executeQuery(sql)) {
                            while (rs.next()) {
                                System.out.println(rs.getInt("StudentID") + " | " + rs.getString("Name") +
                                        " | " + rs.getString("Department") + " | " + rs.getInt("Marks"));
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter Student ID to delete: ");
                        int deleteId = scanner.nextInt();
                        String deleteSQL = "DELETE FROM Student WHERE StudentID = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                            pstmt.setInt(1, deleteId);
                            pstmt.executeUpdate();
                            System.out.println("Student deleted successfully.");
                        }
                        break;

                    case 4:
                        scanner.close();
                        return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
