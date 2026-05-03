# Hướng Dẫn Thực Hành Java EE: NetBeans 8.2 + SQL Server + Hibernate

Tài liệu này hướng dẫn chi tiết từng bước để bạn hoàn thành bài thi thực hành Java EE, từ khâu chuẩn bị CSDL đến khi chạy ứng dụng Web MVC.

---

## Bước 1: Chuẩn bị Cơ sở dữ liệu (SQL Server)

1. Mở **SQL Server Management Studio (SSMS)**.
2. Tạo mới một Database (ví dụ: `HBDemo`).
3. Tạo bảng dữ liệu (ví dụ: bảng `Student`):
   ```sql
   CREATE TABLE Student (
       id INT PRIMARY KEY IDENTITY(1,1),
       name NVARCHAR(100),
       email VARCHAR(100),
       mark FLOAT
   );
   ```
4. **Quan trọng:** Đảm bảo SQL Server đã bật cổng **1433** và cho phép đăng nhập bằng tài khoản (SQL Server Authentication).

---

## Bước 2: Tạo Project trong NetBeans 8.2

1. **File** -> **New Project**.
2. Chọn **Java Web** -> **Web Application**.
3. Tên Project: `HBWeb`.
4. Server: Chọn **GlassFish Server**.
5. Java EE Version: **Java EE 7 Web**.

---

## Bước 3: Thêm Thư viện (Libraries)

Click chuột phải vào thư mục **Libraries** trong Project -> **Add Library/JAR**:
1. **Hibernate 4.3.x** (hoặc bản có sẵn trong NetBeans).
2. **SQL Server JDBC Driver** (tệp `.jar` để kết nối Java với SQL Server).

---

## Bước 4: Cấu hình Hibernate (hibernate.cfg.xml)

1. Chuột phải vào **Source Packages** -> **New** -> **Other**.
2. Chọn **Hibernate** -> **Hibernate Configuration File (cfg.xml)**.
3. Cấu hình nội dung kết nối:
   ```xml
   <hibernate-configuration>
     <session-factory>
       <!-- Driver & URL -->
       <property name="hibernate.connection.driver_class">com.microsoft.sqlserver.jdbc.SQLServerDriver</property>
       <property name="hibernate.connection.url">jdbc:sqlserver://localhost:1433;databaseName=HBDemo</property>
       <property name="hibernate.connection.username">sa</property>
       <property name="hibernate.connection.password">123</property>
       
       <!-- Dialect (Ngôn ngữ SQL Server) -->
       <property name="hibernate.dialect">org.hibernate.dialect.SQLServerDialect</property>
       
       <!-- Tiện ích -->
       <property name="show_sql">true</property>
       <property name="hibernate.format_sql">true</property>
     </session-factory>
   </hibernate-configuration>
   ```

---

## Bước 5: Tạo Entity (Tự động từ DB)

1. Chuột phải vào Project -> **New** -> **Other**.
2. Chọn **Persistence** -> **Entity Classes from Database**.
3. Tạo **Data Source** mới trỏ về SQL Server của bạn.
4. Chọn bảng `Student` -> Nhấn **Add**.
5. Đặt tên Package (ví dụ: `entity`) -> **Finish**.

---

## Bước 6: Tạo Lớp Tiện ích (HibernateUtil)

Tạo class `util.HibernateUtil` để quản lý `SessionFactory`:
```java
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
```

---

## Bước 7: Viết Code Xử lý (Service/DAO) - Đầy đủ CRUD & Query

Tạo class `service.StudentService` (hoặc `ToyService`) để thực hiện đầy đủ các yêu cầu trong bài tập:

```java
public class StudentService {
    // 1. Lấy danh sách (Bài 3: ORDER BY)
    public List<Student> getAllSorted() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // HQL: Sắp xếp theo mark tăng dần
        List<Student> list = session.createQuery("FROM Student s ORDER BY s.mark ASC").list();
        session.close();
        return list;
    }

    // 2. Truy vấn với điều kiện (Bài 1: WHERE, Bài 4: BETWEEN)
    public List<Student> getByMarkRange(double min, double max) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Student s WHERE s.mark BETWEEN :min AND :max ORDER BY s.mark DESC";
        List<Student> list = session.createQuery(hql)
                                    .setParameter("min", min)
                                    .setParameter("max", max)
                                    .list();
        session.close();
        return list;
    }

    // 3. Tìm kiếm chuỗi (Bài 2: LIKE)
    public List<Student> searchByName(String keyword) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Student s WHERE s.name LIKE :kw";
        List<Student> list = session.createQuery(hql)
                                    .setParameter("kw", "%" + keyword + "%")
                                    .list();
        session.close();
        return list;
    }

    // 4. Thêm mới (Create)
    public void save(Student s) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(s);
            t.commit();
        } catch (Exception e) { t.rollback(); }
        finally { session.close(); }
    }

    // 5. Cập nhật (Update)
    public void update(Student s) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(s);
            t.commit();
        } catch (Exception e) { t.rollback(); }
        finally { session.close(); }
    }

    // 6. Xóa (Delete)
    public void delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            Student s = (Student) session.get(Student.class, id);
            if (s != null) session.delete(s);
            t.commit();
        } catch (Exception e) { t.rollback(); }
        finally { session.close(); }
    }

    // 7. Truy vấn JOIN (Bài tập Nâng cao)
    // Giả sử Toy có Brand (Many-to-One)
    public List<Object[]> getToysWithBrand() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // JOIN giữa 2 Entity
        String hql = "SELECT t.name, b.name FROM Toy t JOIN t.brand b";
        List<Object[]> list = session.createQuery(hql).list();
        session.close();
        return list;
    }
}
```

---

## Bước 8: Xử lý Quan hệ Entity (One-to-Many / Many-to-One)

Khi làm bài tập về **Toy** và **Brand** (như trong `bai-tap.pdf`):
1. **Entity Brand:** Sẽ có `@OneToMany(mappedBy = "brand")` trỏ đến danh sách Toys.
2. **Entity Toy:** Sẽ có `@ManyToOne` và `@JoinColumn(name = "brandId")` trỏ về Brand.
3. **Lưu ý:** Khi gen code tự động từ DB trong NetBeans, các quan hệ này sẽ được tự động tạo dưới dạng `Set<Toy>` hoặc `Brand brand`.

---

## Bước 9: Native Query (Truy vấn SQL thuần)

Nếu đề bài yêu cầu dùng **Native Query**:
```java
public List<Student> getByNativeSQL() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    String sql = "SELECT * FROM Student WHERE mark > 5";
    List<Student> list = session.createSQLQuery(sql)
                                .addEntity(Student.class)
                                .list();
    session.close();
    return list;
}
```

---

## Bước 10: Controller (Servlet)

1. Chuột phải vào Package `controller` -> **New** -> **Servlet**.
2. Trong hàm `processRequest` hoặc `doGet`:
   ```java
   StudentService service = new StudentService();
   // Gọi phương thức phù hợp (ví dụ: getAllSorted)
   List<Student> list = service.getAllSorted(); 
   request.setAttribute("students", list);
   request.getRequestDispatcher("index.jsp").forward(request, response);
   ```

---

## Bước 11: Hiển thị (JSP với JSTL)

Trong `index.jsp`, sử dụng thẻ `c:forEach` để hiển thị:
```jsp
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Mark</th>
    </tr>
    <c:forEach var="s" items="${students}">
        <tr>
            <td>${s.id}</td>
            <td>${s.name}</td>
            <td>${s.mark}</td>
        </tr>
    </c:forEach>
</table>
```

---

### Mẹo nhỏ khi đi thi:
- **Lỗi kết nối:** Luôn kiểm tra lại Username/Password và Database name trong `hibernate.cfg.xml`.
- **Lỗi JSTL:** Đảm bảo đã thêm thư viện `JSTL 1.2` vào Libraries.
- **Clean and Build:** Nếu code không ăn, hãy dùng chức năng **Clean and Build** project.
