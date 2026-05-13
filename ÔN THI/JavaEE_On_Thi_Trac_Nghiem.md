# Tổng Hợp Kiến Thức Ôn Thi Java EE (Tuần 1 - Tuần 6)

Tài liệu này hệ thống lại toàn bộ kiến thức lý thuyết và các câu hỏi trắc nghiệm trọng tâm để chuẩn bị cho bài thi giữa kỳ.

---

## 1. Tổng Quan Web & Servlet (Tuần 1 - 2)

### Kiến thức trọng tâm:
- **Cấu trúc Project:** Thư mục `/web` chứa JSP, HTML. Thư mục `/WEB-INF` chứa `web.xml`, tệp này bảo mật (client không thể truy cập trực tiếp).
- **Servlet Lifecycle (Vòng đời):**
  1. `init()`: Chạy 1 lần duy nhất khi Servlet được load.
  2. `service()`: Gọi mỗi khi có request, nó sẽ phân phối tới `doGet()` hoặc `doPost()`.
  3. `destroy()`: Chạy 1 lần trước khi server tắt hoặc undeploy.
- **Request & Response:**
  - `request.getParameter("name")`: Lấy dữ liệu từ form/URL (luôn trả về **String**).
  - `request.setAttribute("key", object)`: Lưu đối tượng vào request để chuyển tiếp.
  - `request.getAttribute("key")`: Lấy đối tượng ra (trả về **Object**, cần ép kiểu).
- **Forward vs Redirect:**
  - **Forward** (`request.getRequestDispatcher("...").forward`): Chuyển tiếp phía server, URL không đổi, dùng được dữ liệu trong request.
  - **Redirect** (`response.sendRedirect("...")`): Chuyển tiếp phía client, URL thay đổi, tạo request mới (mất dữ liệu cũ).

### Câu hỏi trắc nghiệm tiêu biểu:
1. **Câu hỏi:** Phương thức nào của Servlet chỉ được gọi một lần duy nhất?
   - **Đáp án:** `init()` và `destroy()`.
2. **Câu hỏi:** Sự khác biệt chính giữa `getParameter` và `getAttribute`?
   - **Đáp án:** `getParameter` lấy từ client (String), `getAttribute` lấy từ server (Object).

---

## 2. JSP & JSTL (Tuần 3)

### Kiến thức trọng tâm:
- **JSP Elements:**
  - Directive: `<%@ ... %>` (Khai báo trang, thư viện).
  - Scriptlet: `<% ... %>` (Viết code Java).
  - Expression: `<%= ... %>` (In giá trị ra màn hình).
- **JSTL (Thư viện thẻ):**
  - Khai báo: `<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`
  - `<c:forEach var="item" items="${list}">`: Duyệt danh sách.
  - `<c:if test="${condition}">`: Kiểm tra điều kiện.
  - `<c:url>`: Tạo URL an toàn.

---

## 3. Mô hình MVC & Session Management (Tuần 4)

### Kiến thức trọng tâm:
- **MVC:**
  - **Model:** Xử lý dữ liệu (JavaBeans, DAO, Entity).
  - **View:** Hiển thị (JSP).
  - **Controller:** Điều hướng (Servlet).
- **Scope (Phạm vi lưu trữ):**
  - **Request:** Chỉ tồn tại trong 1 yêu cầu.
  - **Session:** Tồn tại từ khi đăng nhập đến khi đóng trình duyệt hoặc logout.
  - **Application:** Tồn tại suốt vòng đời của server.

---

## 4. JPA & Hibernate (Tuần 5 - 6)

### Kiến thức trọng tâm:
- **ORM:** Ánh xạ bảng trong DB sang Class Java.
- **Annotation:**
  - `@Entity`: Bắt buộc cho 1 Class thực thể.
  - `@Id`: Bắt buộc cho khóa chính.
  - `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Khóa chính tự tăng (SQL Server).
  - `@ManyToOne`: Quan hệ n-1 (ví dụ: nhiều Sản phẩm thuộc 1 Danh mục).
- **Vòng đời Entity (Lifecycle):**
  - **New/Transient:** Vừa `new`, chưa có trong DB.
  - **Managed:** Đang được EntityManager quản lý (có trong DB).
  - **Detached:** Đã từng Managed nhưng bị ngắt kết nối.
  - **Removed:** Đã bị xóa.
- **EntityManager Methods:**
  - `persist()`: Thêm mới.
  - `merge()`: Cập nhật (dùng cho đối tượng Detached).
  - `remove()`: Xóa.
  - `find()`: Tìm theo ID.

---

## 5. JPQL (Java Persistence Query Language)

### Kiến thức trọng tâm:
- Truy vấn trên **Entity Class**, không phải trên Table.
- **Đúng:** `SELECT s FROM Student s` (Student là tên Class).
- **Sai:** `SELECT * FROM Student_Table`.
- Tham số: Dùng `:name` (Named parameter).

---

## 6. CÁC CÂU HỎI "BẪY" THƯỜNG GẶP

1. **Câu hỏi:** JPQL có hỗ trợ `SELECT *` không?
   - **Đáp án:** Không. Phải dùng Alias (ví dụ: `SELECT p FROM Product p`).
2. **Câu hỏi:** Thư mục nào chứa tệp `persistence.xml`?
   - **Đáp án:** `src/conf` hoặc `META-INF`.
3. **Câu hỏi:** Annotation nào dùng để chỉ định tên bảng khác với tên Class?
   - **Đáp án:** `@Table(name = "tên_bảng")`.
4. **Câu hỏi:** Để thực hiện các thao tác thay đổi dữ liệu (Insert/Update/Delete) trong JPA, ta cần gì?
   - **Đáp án:** Phải có một **Transaction** (`begin()` và `commit()`).
5. **Câu hỏi:** Trong quan hệ `@ManyToOne`, thuộc tính nào thường dùng để chỉ định cột khóa ngoại?
   - **Đáp án:** `@JoinColumn`.
6. **Câu hỏi:** Làm thế nào để truy cập thuộc tính của một đối tượng liên kết trong JPQL (ví dụ lấy tên Brand của Toy)?
   - **Đáp án:** Sử dụng dấu chấm (dot notation), ví dụ: `SELECT t.brand.name FROM Toy t`.
7. **Câu hỏi:** `EntityManagerFactory` và `EntityManager` cái nào dùng chung cho toàn ứng dụng?
   - **Đáp án:** `EntityManagerFactory`. `EntityManager` thường được tạo mới cho mỗi request.
8. **Câu hỏi:** Khi sử dụng `getResultList()` trong JPQL, nếu không có dữ liệu trả về thì kết quả là gì?
   - **Đáp án:** Một danh sách rỗng (Empty List), không phải `null`.

---

## 7. KIẾN THỨC BỔ SUNG QUAN TRỌNG

### A. Phân biệt CMT và BMT (Transaction Management)
- **CMT (Container Managed Transaction):** Server (GlassFish) tự quản lý. Mặc định của EJB. Bạn không cần viết `begin()` hay `commit()`.
- **BMT (Bean Managed Transaction):** Lập trình viên tự viết code quản lý. Phải dùng `@TransactionManagement(TransactionManagementType.BEAN)` và `UserTransaction`.

### B. Các hàm gộp trong JPQL
- `SELECT COUNT(s) FROM Student s`: Đếm số sinh viên.
- `SELECT AVG(s.mark) FROM Student s`: Tính điểm trung bình.
- `SELECT MAX(s.mark) FROM Student s`: Tìm điểm cao nhất.

### C. Xử lý lỗi trong JSP
- Để chỉ định một trang là trang báo lỗi: `<%@page isErrorPage="true" %>`.
- Để chuyển hướng khi có lỗi: `<%@page errorPage="error.jsp" %>`.

---

**Mẹo ôn tập:**
- Nhớ kỹ sự khác biệt giữa **SQL** và **JPQL**.
- Nhớ các bước trong vòng đời Servlet.
- Tập trung vào cách dùng thẻ `<c:forEach>` và các annotation JPA cơ bản.

**Chúc bạn đạt điểm tuyệt đối phần trắc nghiệm!**
