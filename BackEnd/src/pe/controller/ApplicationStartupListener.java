package pe.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ApplicationStartupListener - Lắng nghe sự kiện khởi động ứng dụng
 * Tự động khởi tạo hệ thống khi web application start
 */
@WebListener
public class ApplicationStartupListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🌟 School Medical Management System is starting up...");
        
        // Khởi tạo ứng dụng
        ApplicationController.initializeApplication();
        
        // Lưu thời gian start vào context
        sce.getServletContext().setAttribute("startupTime", System.currentTimeMillis());
        
        System.out.println("🎉 School Medical Management System started successfully!");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("🔽 School Medical Management System is shutting down...");
        
        // Cleanup ứng dụng
        ApplicationController.cleanupApplication();
        
        System.out.println("👋 School Medical Management System shutdown completed!");
    }
}
