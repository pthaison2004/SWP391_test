package pe.controller;

import pe.utils.DBUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ApplicationController - Điều khiển khởi tạo ứng dụng web
 * Chứa các phương thức để setup và cấu hình hệ thống
 */
public class ApplicationController {
    
    /**
     * Khởi tạo ứng dụng khi web server start
     */
    public static void initializeApplication() {
        System.out.println("🚀 Initializing School Medical Management System...");
        
        // Kiểm tra kết nối database
        if (checkDatabaseConnection()) {
            System.out.println("✅ Database connection verified.");
            
            // Tạo dữ liệu mặc định nếu cần
            createDefaultData();
            
            System.out.println("✅ Application initialized successfully!");
        } else {
            System.err.println("❌ Failed to initialize application - Database connection failed!");
        }
    }
    
    /**
     * Kiểm tra kết nối database
     */
    private static boolean checkDatabaseConnection() {
        try (Connection conn = DBUtils.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Tạo dữ liệu mặc định
     */
    private static void createDefaultData() {
        try (Connection conn = DBUtils.getConnection()) {
            // Kiểm tra xem đã có admin chưa
            Statement stmt = conn.createStatement();
            var rs = stmt.executeQuery("SELECT COUNT(*) FROM Users WHERE userType = 'admin'");
            
            if (rs.next() && rs.getInt(1) == 0) {
                // Tạo tài khoản admin mặc định
                String insertAdmin = "INSERT INTO Users (username, password, firstName, lastName, email, phone, userType) " +
                    "VALUES ('admin', 'admin123', 'System', 'Administrator', 'admin@school.edu.vn', '0123456789', 'admin')";
                
                stmt.executeUpdate(insertAdmin);
                System.out.println("🔧 Created default admin account: admin/admin123");
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating default data: " + e.getMessage());
        }
    }
    
    /**
     * Cleanup khi ứng dụng shutdown
     */
    public static void cleanupApplication() {
        System.out.println("🛑 Shutting down School Medical Management System...");
        // Cleanup resources if needed
        System.out.println("✅ Application shutdown completed.");
    }
    
    /**
     * Lấy thông tin trạng thái hệ thống
     */
    public static String getSystemStatus() {
        StringBuilder status = new StringBuilder();
        status.append("School Medical Management System Status:\n");
        status.append("- Database: ").append(checkDatabaseConnection() ? "Connected" : "Disconnected").append("\n");
        status.append("- Application: Running\n");
        status.append("- Version: 1.0.0\n");
        return status.toString();
    }
}
