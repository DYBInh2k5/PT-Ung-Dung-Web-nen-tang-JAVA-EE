# Hướng Dẫn Ôn Thi Trắc Nghiệm Java EE (JPA, Hibernate, MVC)

Tài liệu này tổng hợp các kiến thức trọng tâm từ các bài Lab và bài giảng để chuẩn bị cho kỳ thi trắc nghiệm Java EE.

---

## 1. Tổng Quan về ORM & Hibernate

### Kiến thức trọng tâm:
- **ORM (Object-Relational Mapping):** Kỹ thuật ánh xạ đối tượng Java sang các bảng trong CSDL quan hệ.
- **Hibernate:** Là một Framework ORM mã nguồn mở phổ biến nhất, thực thi các đặc tả của JPA.
- **Lợi ích:** Giảm code JDBC lặp lại, độc lập với hệ quản trị CSDL, quản lý tài nguyên tự động.

### Câu hỏi trắc nghiệm tiêu biểu:
1. **Câu hỏi:** ORM là viết tắt của cụm từ nào?
   - **Đáp án:** Object-Relational Mapping.
2. **Câu hỏi:** Hibernate đóng vai trò gì trong ứng dụng Java?
   - **Đáp án:** Là tầng trung gian kết nối giữa ứng dụng Java và Database, che giấu các chi tiết SQL phức tạp.
3. **Câu hỏi:** Tệp cấu hình chính của Hibernate thường có tên là gì?
   - **Đáp án:** `hibernate.cfg.xml`.

---

## 2. Java Persistence API (JPA) & Entity

### Kiến thức trọng tâm:
- **Entity:** Một class Java đại diện cho một bảng trong DB.
- **Annotation cơ bản:**
  - `@Entity`: Đánh dấu class là một thực thể JPA.
  - `@Table(name="...")`: Chỉ định tên bảng tương ứng.
  - `@Id`: Đánh dấu khóa chính (Primary Key).
  - `@GeneratedValue`: Cấu hình tự động tăng cho khóa chính.
  - `@Column`: Cấu hình chi tiết cho cột (nullable, length, name).

### Câu hỏi trắc nghiệm tiêu biểu:
1. **Câu hỏi:** Annotation nào là bắt buộc cho một class Entity trong JPA?
   - **Đáp án:** `@Entity` và `@Id`.
2. **Câu hỏi:** Sự khác biệt giữa JPA và Hibernate là gì?
   - **Đáp án:** JPA là một đặc tả (specification/interface), còn Hibernate là một bản thực thi (implementation) của đặc tả đó.

---

## 3. Truy vấn dữ liệu (JPQL & Native Query)

### Kiến thức trọng tâm:
- **JPQL (Java Persistence Query Language):**
  - Thao tác trên **Entity và thuộc tính**, không thao tác trên bảng và cột SQL.
  - Ví dụ: `SELECT u FROM User u` (User là tên Class).
- **Native Query:** Viết SQL thuần túy dành cho các truy vấn phức tạp hoặc đặc thù của CSDL.
- **Tham số trong JPQL:** Sử dụng dấu `:` (ví dụ: `:email`) để tránh SQL Injection.

### Câu hỏi trắc nghiệm tiêu biểu:
1. **Câu hỏi:** JPQL truy vấn dựa trên cái gì?
   - **Đáp án:** Các Persistent Objects (Entities) và các thuộc tính của chúng.
2. **Câu hỏi:** Để thực hiện sắp xếp trong JPQL, ta dùng từ khóa nào?
   - **Đáp án:** `ORDER BY`.
3. **Câu hỏi:** Cách truyền tham số an toàn trong JPQL là gì?
   - **Đáp án:** Sử dụng Named Parameters (ví dụ: `.setParameter("name", value)`).

---

## 4. Quản lý Transaction & EntityManager

### Kiến thức trọng tâm:
- **EntityManager:** Interface chính để quản lý vòng đời của Entity (find, persist, merge, remove).
- **Transaction:**
  - `begin()`: Bắt đầu.
  - `commit()`: Lưu thay đổi xuống DB.
  - `rollback()`: Hủy bỏ thay đổi nếu có lỗi.
- **Phương thức quan trọng:**
  - `persist(entity)`: Thêm mới.
  - `merge(entity)`: Cập nhật.
  - `remove(entity)`: Xóa.
  - `find(Class, ID)`: Tìm theo khóa chính.

### Câu hỏi trắc nghiệm tiêu biểu:
1. **Câu hỏi:** Khi một ngoại lệ xảy ra trong quá trình cập nhật dữ liệu, ta nên gọi phương thức nào của Transaction?
   - **Đáp án:** `rollback()`.
2. **Câu hỏi:** Phương thức nào dùng để lưu một đối tượng mới vào Database?
   - **Đáp án:** `persist()`.

---

## 5. Mô hình Web MVC trong Java EE

### Kiến thức trọng tâm:
- **Model:** Chứa dữ liệu và logic nghiệp vụ (Entities, Services, DAO).
- **View:** Hiển thị dữ liệu (JSP, JSTL, HTML).
- **Controller:** Tiếp nhận request, điều hướng và gọi Model (Servlet).
- **Luồng đi:** Browser -> Servlet (Controller) -> Service (Model) -> Hibernate -> DB.

### Câu hỏi trắc nghiệm tiêu biểu:
1. **Câu hỏi:** Trong mô hình MVC, thành phần nào chịu trách nhiệm xử lý các HTTP Request?
   - **Đáp án:** Controller (thường là Servlet).
2. **Câu hỏi:** Thư viện nào thường dùng trong JSP để duyệt danh sách mà không cần dùng code Java thuần (scriptlet)?
   - **Đáp án:** JSTL (JavaServer Pages Standard Tag Library).

---

## 6. So sánh JDBC và JPA

| Đặc điểm | JDBC | JPA / Hibernate |
| :--- | :--- | :--- |
| **Mức độ** | Thấp (Low-level) | Cao (High-level) |
| **Truy vấn** | SQL thuần | JPQL / HQL |
| **Chuyển đổi** | Thủ công (ResultSet -> Object) | Tự động (ORM) |
| **Bảo trì** | Khó (nhiều code lặp) | Dễ (code gọn, rõ ràng) |

---

## 7. Trắc Nghiệm Về Code & Cú Pháp (Phần Quan Trọng)

Phần này tập trung vào các câu hỏi yêu cầu phân biệt đúng/sai trong các đoạn code.

### A. Annotation & Entity Code
1. **Câu hỏi:** Đoạn code nào sau đây định nghĩa đúng một khóa chính tự tăng?
   - **A.** `@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;` (ĐÚNG)
   - **B.** `@Id @AutoIncrement private int id;` (SAI - không có `@AutoIncrement`)
2. **Câu hỏi:** Để ánh xạ thuộc tính `birthDate` vào cột `NGAY_SINH` trong DB, ta dùng:
   - **Đáp án:** `@Column(name = "NGAY_SINH") private Date birthDate;`

### B. JPQL Syntax (Cú pháp truy vấn)
1. **Câu hỏi:** Đoạn code nào là truy vấn JPQL đúng để lấy User theo tên?
   - **A.** `SELECT u FROM User u WHERE u.name = :name` (ĐÚNG)
   - **B.** `SELECT * FROM User WHERE name = ?` (SAI - JPQL không dùng `*` và thường dùng tham số `:name`)
2. **Câu hỏi:** Trong JPQL, làm sao để thực hiện JOIN?
   - **Đáp án:** `SELECT t FROM Toy t JOIN t.brand b WHERE b.name = 'Lego'`

### C. Transaction & EntityManager Code
1. **Câu hỏi:** Thứ tự đúng khi thực hiện thêm mới (insert) là gì?
   - **Đáp án:** `em.getTransaction().begin();` -> `em.persist(user);` -> `em.getTransaction().commit();`
2. **Câu hỏi:** Phương thức nào dùng để tìm một đối tượng theo khóa chính mà không cần viết JPQL?
   - **Đáp án:** `em.find(User.class, id);`
3. **Câu hỏi:** Sự khác biệt giữa `persist()` và `merge()`?
   - **Đáp án:** `persist()` dùng để thêm mới hoàn toàn, `merge()` dùng để cập nhật (hoặc thêm mới nếu chưa tồn tại) một đối tượng đã bị tách rời (detached).

### D. Các lỗi (Exception) thường gặp
1. **Câu hỏi:** Lỗi `EntityNotFoundException` xảy ra khi nào?
   - **Đáp án:** Khi gọi `em.getReference()` hoặc thao tác trên một ID không tồn tại trong DB.
2. **Câu hỏi:** Lỗi `NonUniqueResultException` xảy ra khi nào?
   - **Đáp án:** Khi dùng `getSingleResult()` nhưng câu truy vấn trả về nhiều hơn 1 bản ghi.

---

**Mẹo làm bài trắc nghiệm code:**
- Luôn kiểm tra xem tên Class trong truy vấn có viết hoa đúng như Entity không (JPQL phân biệt hoa thường với tên Class).
- Kiểm tra xem đã có `getTransaction().begin()` trước khi thực hiện `persist/merge/remove` chưa.
- Nhớ rằng JPQL làm việc với **Object**, SQL làm việc với **Table**.
