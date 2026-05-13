/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;




/**
 *
 * @author admin
 */
/**
 * LỚP TIỆN ÍCH: HibernateUtil
 * Nhiệm vụ: Khởi tạo và quản lý SessionFactory - "nhà máy" tạo ra các phiên làm việc (Session) với DB.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // 1. LUỒNG ĐI: Tạo đối tượng cấu hình
            Configuration configuration = new Configuration();
            
            // 2. LUỒNG ĐI: Đọc file hibernate.cfg.xml để lấy thông tin kết nối DB
            configuration.configure("hibernate.cfg.xml");

            // 3. LUỒNG ĐI: Xây dựng ServiceRegistry để quản lý các dịch vụ của Hibernate
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            // 4. LUỒNG ĐI: Tạo SessionFactory từ cấu hình đã thiết lập
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           

        } catch (Throwable ex) {
            System.err.println("Lỗi khởi tạo SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Phương thức tĩnh để lấy SessionFactory dùng chung cho toàn ứng dụng.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

