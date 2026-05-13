# Hướng Dẫn Lab: Truy Vấn JPA Nâng Cao (Bám sát code thầy)

Tài liệu này hướng dẫn các kỹ thuật truy vấn dữ liệu nâng cao bằng JPQL, Native Query và Transaction bám sát 100% mã nguồn `StudentFacade.java` của thầy.

---

## LUỒNG HOẠT ĐỘNG (QUERY FLOW)
1. **Controller:** Nhận từ khóa tìm kiếm hoặc yêu cầu sắp xếp.
2. **Facade:** 
   - Nhận tham số từ Controller.
   - Xây dựng câu lệnh JPQL (làm việc trên tên Class và tên Biến).
   - Truyền tham số an toàn qua `setParameter`.
   - Thực thi và trả về danh sách `List<Student>`.
3. **Controller:** Lưu danh sách vào `setAttribute` và chuyển sang JSP hiển thị.

---

## 1. Truy vấn JPQL theo điều kiện (Cách thầy viết)

Thầy luôn dùng `em.createQuery` và truyền tham số bằng `:name`.

```java
// 1. Tìm theo tên (Khớp chính xác)
public List<Student> findByFirstName(String firstName) {
    return em.createQuery("SELECT s FROM Student s WHERE s.firstName=:fn", Student.class)
             .setParameter("fn", firstName)
             .getResultList();
}

// 2. Tìm theo Email (Dùng LIKE)
public List<Student> findByEmail(String email) {
    return em.createQuery("SELECT s FROM Student s WHERE s.email LIKE :email", Student.class)
             .setParameter("email", String.format("%%%s%%", email)) // Thầy dùng format để thêm dấu %
             .getResultList();
}

// 3. Sắp xếp danh sách (ORDER BY)
public List<Student> orderByFirstName() {
    return em.createQuery("SELECT s FROM Student s ORDER BY s.firstName ASC", Student.class)
             .getResultList();
}
```

---

## 2. Truy vấn dùng Named Query & Native Query (Theo thầy)

```java
// 4. Dùng Named Query (Khai báo sẵn trong Entity)
public Student usingNamedQuery() {
    return em.createNamedQuery("Student.findById", Student.class)
             .setParameter("id", 10)
             .getSingleResult();
}

// 5. Dùng Native Query (SQL thuần)
public List<Student> useNativeQuery() {
    return em.createNativeQuery("SELECT * FROM Student WHERE id < 10", Student.class)
             .getResultList();
}
```

---

## 3. Quản lý Giao dịch (Transaction) theo cách của thầy

Thầy sử dụng `@TransactionManagement(TransactionManagementType.BEAN)` và `UserTransaction`.

```java
@Resource
private UserTransaction utx;

public void mangeTransaction() {
    try {
        utx.begin(); // Bắt đầu giao dịch
        
        Student s1 = this.find(1);
        s1.setFirstName("Nam"); // Cập nhật dữ liệu
        
        utx.commit(); // Lưu thay đổi
    } catch (Exception e) {
        try { utx.rollback(); } catch (Exception ex) {} // Hủy nếu lỗi
    }
}
```

---

## CÁCH THAY ĐỔI CODE KHI ĐI THI (CHI TIẾT)

| Yêu cầu của đề | Chỗ cần sửa trong code thầy |
| :--- | :--- |
| **Tìm kiếm gần đúng** | Sửa `WHERE s.name = :name` thành `WHERE s.name LIKE :name` và dùng `String.format("%%%s%%", value)`. |
| **Sắp xếp giảm dần** | Sửa `ASC` thành `DESC` trong câu lệnh JPQL. |
| **Lấy 1 kết quả duy nhất** | Thay `.getResultList()` bằng `.getSingleResult()`. |
| **Lỗi Transaction** | Kiểm tra xem đã có `@Resource private UserTransaction utx;` chưa. |

**Mẹo của thầy:** Thầy hay in kết quả ra Console để kiểm tra: `System.out.println("Id: " + s.getId());`. Bạn có thể dùng cách này để debug nhanh lúc thi.
