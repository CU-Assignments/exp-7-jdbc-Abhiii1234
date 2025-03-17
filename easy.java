import java.sql.*;

public class EmployeeData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test_db";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Employee")) {

            while (rs.next()) {
                int empId = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");

                System.out.println(empId + " | " + name + " | " + salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
