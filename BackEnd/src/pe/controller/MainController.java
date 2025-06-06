package pe.controller;

import pe.dao.UserDAO;
import pe.dao.HealthDeclarationDAO;
import pe.dao.VaccinationDAO;
import pe.dao.MedicineRecordDAO;
import pe.entity.User;
import pe.utils.DBUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * MainController - Lớp điều khiển chính của ứng dụng
 * Chứa hàm main để test và khởi tạo hệ thống
 */
public class MainController {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Hàm main chính của ứng dụng
     * @param args tham số dòng lệnh
     */
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("    SCHOOL MEDICAL MANAGEMENT SYSTEM");
        System.out.println("    Hệ Thống Quản Lý Y Tế Học Đường");
        System.out.println("=================================================");
        
        // Test kết nối database
        if (!testDatabaseConnection()) {
            System.err.println("❌ Không thể kết nối database. Vui lòng kiểm tra cấu hình!");
            return;
        }
        
        // Menu chính
        showMainMenu();
    }
    
    /**
     * Test kết nối database
     * @return true nếu kết nối thành công
     */
    private static boolean testDatabaseConnection() {
        System.out.println("\n🔄 Đang test kết nối database...");
        
        try (Connection conn = DBUtils.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Kết nối database thành công!");
                System.out.println("📋 Database: " + conn.getCatalog());
                System.out.println("🔗 URL: " + conn.getMetaData().getURL());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối database: " + e.getMessage());
            System.err.println("💡 Kiểm tra:");
            System.err.println("   - SQL Server đã chạy chưa?");
            System.err.println("   - Database SchoolMedicalDB đã tồn tại chưa?");
            System.err.println("   - Thông tin đăng nhập SA/12345 có đúng không?");
        }
        return false;
    }
    
    /**
     * Hiển thị menu chính
     */
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n📋 MENU CHÍNH:");
            System.out.println("1. 👥 Quản lý người dùng");
            System.out.println("2. 🏥 Quản lý khai báo sức khỏe");
            System.out.println("3. 💉 Quản lý tiêm chủng");
            System.out.println("4. 💊 Quản lý thuốc");
            System.out.println("5. 🔍 Kiểm tra hệ thống");
            System.out.println("6. 📊 Thống kê tổng quan");
            System.out.println("0. ❌ Thoát");
            System.out.print("\n👉 Chọn chức năng (0-6): ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    userManagementMenu();
                    break;
                case 2:
                    healthDeclarationMenu();
                    break;
                case 3:
                    vaccinationMenu();
                    break;
                case 4:
                    medicineMenu();
                    break;
                case 5:
                    systemHealthCheck();
                    break;
                case 6:
                    showStatistics();
                    break;
                case 0:
                    System.out.println("\n👋 Cảm ơn bạn đã sử dụng hệ thống!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }
    
    /**
     * Menu quản lý người dùng
     */
    private static void userManagementMenu() {
        UserDAO userDAO = new UserDAO();
        
        while (true) {
            System.out.println("\n👥 QUẢN LÝ NGƯỜI DÙNG:");
            System.out.println("1. 📋 Xem danh sách người dùng");
            System.out.println("2. 🔍 Tìm người dùng theo ID");
            System.out.println("3. ➕ Thêm người dùng mới");
            System.out.println("4. 🗑️ Xóa người dùng");
            System.out.println("0. ⬅️ Quay lại menu chính");
            System.out.print("\n👉 Chọn chức năng: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    listAllUsers(userDAO);
                    break;
                case 2:
                    findUserById(userDAO);
                    break;
                case 3:
                    addNewUser(userDAO);
                    break;
                case 4:
                    deleteUser(userDAO);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }
    
    /**
     * Hiển thị danh sách tất cả người dùng
     */
    private static void listAllUsers(UserDAO userDAO) {
        try {
            List<User> users = userDAO.getAllUsers();
            System.out.println("\n📋 DANH SÁCH NGƯỜI DÙNG:");
            System.out.println("=================================================");
            
            if (users.isEmpty()) {
                System.out.println("Không có người dùng nào trong hệ thống.");
            } else {
                for (User user : users) {
                    System.out.printf("ID: %d | %-15s | %-20s | %-10s | %s%n",
                        user.getId(),
                        user.getUsername(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getUserType(),
                        user.getEmail()
                    );
                }
                System.out.println("=================================================");
                System.out.println("📊 Tổng cộng: " + users.size() + " người dùng");
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi lấy danh sách người dùng: " + e.getMessage());
        }
    }
    
    /**
     * Tìm người dùng theo ID
     */
    private static void findUserById(UserDAO userDAO) {
        System.out.print("📝 Nhập ID người dùng: ");
        int id = getIntInput();
        
        try {
            User user = userDAO.getUserById(id);
            if (user != null) {
                System.out.println("\n✅ Tìm thấy người dùng:");
                System.out.println("=================================================");
                System.out.println("ID: " + user.getId());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Họ tên: " + user.getFirstName() + " " + user.getLastName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Điện thoại: " + user.getPhone());
                System.out.println("Loại người dùng: " + user.getUserType());
                System.out.println("Ngày tạo: " + user.getCreatedDate());
                System.out.println("=================================================");
            } else {
                System.out.println("❌ Không tìm thấy người dùng với ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi tìm người dùng: " + e.getMessage());
        }
    }
    
    /**
     * Thêm người dùng mới
     */
    private static void addNewUser(UserDAO userDAO) {
        System.out.println("\n➕ THÊM NGƯỜI DÙNG MỚI:");
        
        System.out.print("📝 Username: ");
        String username = scanner.nextLine();
        
        System.out.print("📝 Password: ");
        String password = scanner.nextLine();
        
        System.out.print("📝 Họ: ");
        String firstName = scanner.nextLine();
        
        System.out.print("📝 Tên: ");
        String lastName = scanner.nextLine();
        
        System.out.print("📝 Email: ");
        String email = scanner.nextLine();
        
        System.out.print("📝 Điện thoại: ");
        String phone = scanner.nextLine();
        
        System.out.print("📝 Loại người dùng (admin/teacher/student): ");
        String userType = scanner.nextLine();
        
        try {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setUserType(userType);
            
            boolean success = userDAO.registerUser(newUser);
            if (success) {
                System.out.println("✅ Thêm người dùng thành công!");
            } else {
                System.out.println("❌ Thêm người dùng thất bại!");
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi thêm người dùng: " + e.getMessage());
        }
    }
    
    /**
     * Xóa người dùng
     */
    private static void deleteUser(UserDAO userDAO) {
        System.out.print("📝 Nhập ID người dùng cần xóa: ");
        int id = getIntInput();
        
        try {
            // Kiểm tra người dùng có tồn tại không
            User user = userDAO.getUserById(id);
            if (user == null) {
                System.out.println("❌ Không tìm thấy người dùng với ID: " + id);
                return;
            }
            
            System.out.printf("⚠️ Bạn có chắc muốn xóa người dùng '%s' (ID: %d)? (y/N): ", 
                user.getUsername(), id);
            String confirm = scanner.nextLine();
            
            if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
                boolean success = userDAO.deleteUser(id);
                if (success) {
                    System.out.println("✅ Xóa người dùng thành công!");
                } else {
                    System.out.println("❌ Xóa người dùng thất bại!");
                }
            } else {
                System.out.println("🚫 Đã hủy thao tác xóa.");
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi xóa người dùng: " + e.getMessage());
        }
    }
    
    /**
     * Menu quản lý khai báo sức khỏe
     */
    private static void healthDeclarationMenu() {
        System.out.println("\n🏥 QUẢN LÝ KHAI BÁO SỨC KHỎE:");
        System.out.println("(Chức năng sẽ được phát triển...)");
        pressEnterToContinue();
    }
    
    /**
     * Menu quản lý tiêm chủng
     */
    private static void vaccinationMenu() {
        System.out.println("\n💉 QUẢN LÝ TIÊM CHỦNG:");
        System.out.println("(Chức năng sẽ được phát triển...)");
        pressEnterToContinue();
    }
    
    /**
     * Menu quản lý thuốc
     */
    private static void medicineMenu() {
        System.out.println("\n💊 QUẢN LÝ THUỐC:");
        System.out.println("(Chức năng sẽ được phát triển...)");
        pressEnterToContinue();
    }
    
    /**
     * Kiểm tra tình trạng hệ thống
     */
    private static void systemHealthCheck() {
        System.out.println("\n🔍 KIỂM TRA TÌNH TRẠNG HỆ THỐNG:");
        System.out.println("=================================================");
        
        // Test database connection
        System.out.print("🔗 Database Connection: ");
        if (testDatabaseConnection()) {
            System.out.println("✅ OK");
        } else {
            System.out.println("❌ FAILED");
        }
        
        // Test DAO classes
        System.out.print("👥 UserDAO: ");
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.getAllUsers();
            System.out.println("✅ OK");
        } catch (Exception e) {
            System.out.println("❌ FAILED - " + e.getMessage());
        }
        
        System.out.print("🏥 HealthDeclarationDAO: ");
        try {
            HealthDeclarationDAO healthDAO = new HealthDeclarationDAO();
            System.out.println("✅ OK");
        } catch (Exception e) {
            System.out.println("❌ FAILED - " + e.getMessage());
        }
        
        System.out.print("💉 VaccinationDAO: ");
        try {
            VaccinationDAO vaccinationDAO = new VaccinationDAO();
            System.out.println("✅ OK");
        } catch (Exception e) {
            System.out.println("❌ FAILED - " + e.getMessage());
        }
        
        System.out.print("💊 MedicineRecordDAO: ");
        try {
            MedicineRecordDAO medicineDAO = new MedicineRecordDAO();
            System.out.println("✅ OK");
        } catch (Exception e) {
            System.out.println("❌ FAILED - " + e.getMessage());
        }
        
        System.out.println("=================================================");
        pressEnterToContinue();
    }
    
    /**
     * Hiển thị thống kê tổng quan
     */
    private static void showStatistics() {
        System.out.println("\n📊 THỐNG KÊ TỔNG QUAN:");
        System.out.println("=================================================");
        
        try {
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.getAllUsers();
            
            System.out.println("👥 Tổng số người dùng: " + users.size());
            
            // Thống kê theo loại người dùng
            long adminCount = users.stream().filter(u -> "admin".equals(u.getUserType())).count();
            long teacherCount = users.stream().filter(u -> "teacher".equals(u.getUserType())).count();
            long studentCount = users.stream().filter(u -> "student".equals(u.getUserType())).count();
            
            System.out.println("👑 Admin: " + adminCount);
            System.out.println("👨‍🏫 Giáo viên: " + teacherCount);
            System.out.println("👨‍🎓 Học sinh: " + studentCount);
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi lấy thống kê: " + e.getMessage());
        }
        
        System.out.println("=================================================");
        pressEnterToContinue();
    }
    
    /**
     * Đọc input số nguyên từ console
     */
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("❌ Vui lòng nhập số hợp lệ: ");
            }
        }
    }
    
    /**
     * Chờ người dùng nhấn Enter để tiếp tục
     */
    private static void pressEnterToContinue() {
        System.out.print("\n⏸️ Nhấn Enter để tiếp tục...");
        scanner.nextLine();
    }
}
