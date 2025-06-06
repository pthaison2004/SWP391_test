# Controller Package

Thư mục này chứa các lớp điều khiển chính của hệ thống School Medical Management System.

## Các class chính:

### 1. MainController.java
- **Mục đích**: Chứa hàm main() để chạy ứng dụng console
- **Chức năng**:
  - Test kết nối database
  - Menu quản lý người dùng
  - Menu quản lý khai báo sức khỏe
  - Menu quản lý tiêm chủng
  - Menu quản lý thuốc
  - Kiểm tra tình trạng hệ thống
  - Thống kê tổng quan

### 2. ApplicationController.java
- **Mục đích**: Quản lý khởi tạo và cấu hình ứng dụng web
- **Chức năng**:
  - Khởi tạo ứng dụng khi web server start
  - Kiểm tra kết nối database
  - Tạo dữ liệu mặc định (admin account)
  - Cleanup khi shutdown
  - Lấy thông tin trạng thái hệ thống

### 3. ApplicationStartupListener.java
- **Mục đích**: ServletContextListener để tự động khởi tạo ứng dụng
- **Chức năng**:
  - Lắng nghe sự kiện start/stop của web application
  - Tự động gọi ApplicationController khi khởi động
  - Cleanup resources khi shutdown

## Cách sử dụng:

### Chạy ứng dụng console:
```bash
# Từ thư mục BackEnd
run-console.bat
```

### Chạy ứng dụng web:
1. Compile project: `build.bat`
2. Deploy WAR file vào Tomcat
3. ApplicationStartupListener sẽ tự động khởi tạo ứng dụng

## Lưu ý:
- MainController có thể chạy độc lập để test và quản lý hệ thống
- ApplicationController được gọi tự động khi web application start
- Cần đảm bảo database đã được setup trước khi chạy
