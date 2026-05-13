# Hướng Dẫn Lab: Hibernate trong Java Web (Bám sát code thầy)

Tài liệu này hướng dẫn xây dựng ứng dụng Web MVC với Hibernate bám sát 100% mã nguồn dự án `HBWebEx` của thầy.

---

## LUỒNG HOẠT ĐỘNG (HIBERNATE WEB FLOW)
1. **Servlet:** Nhận tham số `action` (ví dụ: `delete`).
2. **Service:** Servlet gọi lớp Service. Service chịu trách nhiệm mở Session, bắt đầu Transaction và thực hiện lệnh Hibernate (`session.delete`).
3. **Hibernate:** Ánh xạ từ Object `Student` sang dòng dữ liệu trong Database.
4. **Servlet:** Sau khi Service làm xong, Servlet gọi `service.getAll()` để lấy lại danh sách mới nhất.
5. **JSP:** Servlet đẩy danh sách mới sang trang JSP để vẽ lại bảng cho người dùng.

---

## 1. Cấu trúc lớp Service chuẩn của thầy (`StudentService.java`)

Thầy luôn viết các hàm CRUD tập trung trong lớp Service để Servlet gọi.

```java
public class StudentService {
    // 1. Lấy toàn bộ danh sách (Dùng HQL: from Student)
    public List<Student> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> list = session.createQuery("from Student").list();
        session.close();
        return list;
    }

    // 2. Thêm mới theo cách của thầy
    public void add(String name, int age) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student s = new Student();
        s.setName(name); s.setAge(age);
        session.save(s); // Thầy dùng save()
        tx.commit(); session.close();
    }

    // 3. Xóa theo cách của thầy (Phải find trước khi delete)
    public void delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student s = (Student)session.get(Student.class, id); // Thầy dùng get()
        if (s != null) session.delete(s);
        tx.commit(); session.close();
    }

    // 4. Cập nhật theo cách của thầy
    public void update(int id, String name, int age) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student s = (Student)session.get(Student.class, id);
        if (s != null) {
            s.setName(name); s.setAge(age);
            session.update(s); // Thầy dùng update()
        }
        tx.commit(); session.close();
    }
}
```

---

## 2. Cách thầy viết Servlet điều hướng (`StudentController.java`)

Thầy dùng tham số `action` để phân biệt các thao tác.

```java
protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
    String action = request.getParameter("action");
    if (action == null) action = "list";
    StudentService service = new StudentService();

    switch (action) {
        case "create":
            service.add(request.getParameter("name"), Integer.parseInt(request.getParameter("age")));
            break;
        case "delete":
            service.delete(Integer.parseInt(request.getParameter("id")));
            break;
        case "update":
            service.update(Integer.parseInt(request.getParameter("id")), 
                           request.getParameter("name"), 
                           Integer.parseInt(request.getParameter("age")));
            break;
    }
    // Thầy luôn lấy lại list và gửi sang JSP hiển thị
    request.setAttribute("list", service.getAll());
    request.getRequestDispatcher("student.jsp").forward(request, response);
}
```

---

## CÁCH THAY ĐỔI CODE KHI ĐI THI (CHI TIẾT)

| Đề bài yêu cầu | Cách sửa bám sát code thầy |
| :--- | :--- |
| **Đổi sang quản lý Toy** | Đổi tên class `Student` -> `Toy`, `StudentService` -> `ToyService`. |
| **Sửa tham số Form** | Kiểm tra `request.getParameter("tên_ô_input")` phải khớp với `name="..."` trong thẻ `<input>` bên JSP. |
| **Ép kiểu ID** | Nếu ID là kiểu chuỗi (String), hãy bỏ `Integer.parseInt()`. Thầy thường dùng ID kiểu số cho sinh viên. |
| **Chuyển trang** | Nếu file JSP nằm trong thư mục khác, sửa `request.getRequestDispatcher("folder/file.jsp")`. |

**Lời khuyên của thầy:** Khi chạy bài Web, hãy dùng chức năng **Clean and Build** thường xuyên để NetBeans cập nhật các thay đổi mới nhất vào Server.
