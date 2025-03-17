import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n1. Add Product\n2. View Products\n3. Update Product\n4. Delete Product\n5. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> addProduct(scanner);
                    case 2 -> viewProducts();
                    case 3 -> updateProduct(scanner);
                    case 4 -> deleteProduct(scanner);
                    case 5 -> {
                        conn.close();
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addProduct(Scanner scanner) throws SQLException {
        System.out.print("Enter Product Name: ");
        String name = scanner.next();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
            System.out.println("Product added successfully.");
        }
    }

    private static void viewProducts() throws SQLException {
        String sql = "SELECT * FROM Product";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("ProductID") + " | " + rs.getString("ProductName") +
                        " | " + rs.getDouble("Price") + " | " + rs.getInt("Quantity"));
            }
        }
    }
}
