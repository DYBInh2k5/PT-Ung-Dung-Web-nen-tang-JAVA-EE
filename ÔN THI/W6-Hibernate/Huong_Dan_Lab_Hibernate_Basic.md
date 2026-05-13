# Hướng Dẫn Lab: Hibernate Cơ Bản (Bám sát code thầy)

Tài liệu này hướng dẫn cách sử dụng Hibernate SE bám sát 100% cách viết của thầy trong dự án `HBEx`.

---

## LUỒNG HOẠT ĐỘNG (HIBERNATE SE FLOW)
1. **Khởi tạo:** `HibernateUtil` đọc file `hibernate.cfg.xml` để biết thông tin Server và Database.
2. **Session:** Ứng dụng mở một `Session` (phiên làm việc) để bắt đầu nói chuyện với DB.
3. **Transaction:** 
   - Với các lệnh thay đổi dữ liệu (Save/Update/Delete), bắt buộc phải mở `beginTransaction`.
   - Nếu mọi thứ ổn, gọi `commit` để đẩy dữ liệu xuống DB vĩnh viễn.
4. **HQL:** Hibernate tự động dịch câu lệnh HQL (from Student) sang SQL thuần tùy theo loại DB bạn dùng (SQL Server, MySQL,...).

---

## 1. Cấu hình Hibernate chuẩn của thầy (`hibernate.cfg.xml`)

Thầy luôn cấu hình trực tiếp các thuộc tính kết nối SQL Server và khai báo class Entity.

```xml
<hibernate-configuration>
    <session-factory>
        <!-- Thầy dùng SQLServerDriver -->
        <property name="hibernate.connection.driver_class">com.microsoft.sqlserver.jdbc.SQLServerDriver</property>
        <property name="hibernate.connection.url">jdbc:sqlserver://localhost:1433;databaseName=HBDemo</property>
        <property name="hibernate.connection.username">sa</property>
        <property name="hibernate.connection.password">1</property>

        <!-- Ngôn ngữ SQL Server -->
        <property name="hibernate.dialect">org.hibernate.dialect.SQLServerDialect</property>
        
        <!-- Khai báo Class Entity (Bắt buộc) -->
        <mapping class="model.Student"/>
    </session-factory>
</hibernate-configuration>
```

---

## 2. Lớp Tiện ích `HibernateUtil` chuẩn của thầy

Thầy dùng `StandardServiceRegistryBuilder` để khởi tạo `SessionFactory`.

```java
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
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

## 3. Cách thầy viết hàm Thao tác dữ liệu (`HBEx.java`)

```java
public class HBEx {
    private static SessionFactory factory;
    
    public static void main(String[] args) {
        factory = HibernateUtil.getSessionFactory();
        create();
        getAll();
    }

    // 1. Hàm lấy danh sách theo cách của thầy
    public static void getAll() {
        Session session = factory.openSession();
        // HQL: "from Student" (Student là tên Class)
        List<Student> list = session.createQuery("from Student").list();
        for (Student s : list) {
            System.out.println(s.getId() + " - " + s.getName());
        }
        session.close();
    }

    // 2. Hàm thêm mới theo cách của thầy
    public static void create() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction(); // Thầy luôn bắt đầu transaction

        Student s = new Student("Nguyen Van A", 20);
        session.save(s); // Thầy dùng save()

        tx.commit(); // Thầy luôn commit() để lưu dữ liệu
        session.close();
    }
}
```

---

## CÁCH THAY ĐỔI CODE KHI ĐI THI (CHI TIẾT)

| Tình huống thi | Cách sửa theo phong cách thầy |
| :--- | :--- |
| **Đổi tên bảng/class** | Sửa `mapping class` trong `hibernate.cfg.xml` và `from Student` trong câu lệnh HQL. |
| **Sửa Database** | Thay đổi `databaseName=HBDemo` và `password=1` trong file `.xml`. |
| **Lỗi không lưu dữ liệu** | Kiểm tra xem bạn đã có `tx.commit()` chưa. Nếu không có dòng này, Database sẽ không thay đổi. |
| **Lỗi `MappingException`** | Kiểm tra xem bạn đã khai báo `@Entity` trong class `Student` chưa. |

**Ghi chú:** Thầy thường dùng `System.out.println` để in dữ liệu ra màn hình Console để kiểm tra nhanh kết quả.
