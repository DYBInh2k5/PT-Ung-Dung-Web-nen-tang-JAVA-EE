# HƯỚNG DẪN CHI TIẾT 100% BÀI TẬP LAB JAVA EE (PDF, DOCX & CODE THẦY)

Tài liệu này được tổng hợp từ việc đọc kỹ từng dòng code trong các dự án (`HBEx`, `jpa2`, `jpa-queries`, `calculator`) và các yêu cầu trong file bài tập của thầy.

---

## 1. TUẦN 1: ÔN TẬP MVC (Dựa trên `calculator` & `bai-tap.pdf`)

**Mục tiêu:** Hiểu luồng dữ liệu JSP -> Servlet -> JavaBean -> JSP.

### Các bước thực hiện chi tiết:
1. **Tạo Model (`models/Calculator.java`):**
   - Chuột phải **Source Packages** -> **New** -> **Java Class**.
   - Khai báo các thuộc tính: `num1`, `num2`, `op`.
   - Chuột phải chọn **Insert Code** -> **Getter and Setter** cho tất cả.
   - Viết hàm `getResult()`:
     ```java
     public Object getResult() {
         if (op.equals("add")) return num1 + num2;
         if (op.equals("div") && num2 == 0) return "Lỗi chia cho 0";
         // ... tương tự cho sub, mul ...
         return 0;
     }
     ```

2. **Tạo Controller (`controllers/CalculatorController.java`):**
   - Chuột phải **Source Packages** -> **New** -> **Servlet**.
   - **Tích chọn "Add information to deployment descriptor (web.xml)"**.
   - Trong `processRequest`:
     ```java
     // Lấy dữ liệu (ép kiểu từ String sang double)
     double n1 = Double.parseDouble(request.getParameter("num1"));
     double n2 = Double.parseDouble(request.getParameter("num2"));
     String op = request.getParameter("op");
     // Đưa vào Model
     Calculator c = new Calculator(n1, n2, op);
     // Lưu vào Request Scope
     request.setAttribute("model", c);
     // Forward về trang ban đầu
     request.getRequestDispatcher("/index.jsp").forward(request, response);
     ```

3. **Giao diện (`index.jsp`):**
   - Thẻ `<form action="calculator" method="POST">`.
   - Hiển thị kết quả bằng Expression Language (EL): `${model.result}`.

---

## 2. TUẦN 2-4: JPA & QUAN HỆ BẢNG (Dựa trên `jpa2`, `Lab1.docx`, `Lab2-Web.docx`)

**Mục tiêu:** Quản lý 2 bảng có quan hệ (ví dụ: `Toy` và `Brand`).

### Các bước thực hiện chi tiết:
1. **Tạo Database:** Phải có khóa ngoại (FK) giữa bảng `Toy` (cột `Brand`) và bảng `Brand` (cột `Id`).
2. **Tạo Entity (Wizard):**
   - New -> **Entity Classes from Database**. Chọn cả 2 bảng.
   - JPA sẽ tự tạo `@ManyToOne` trong class `Toy`.
3. **Tạo Facade (Wizard):**
   - New -> **Session Beans for Entity Classes**. Chọn cả 2 bảng.
4. **Xử lý Controller (`ToyController.java`):**
   - Dùng `@EJB` cho cả `ToyFacade` và `BrandFacade`.
   - **Thêm mới (Create):**
     ```java
     String bId = request.getParameter("brandId"); // Lấy ID thương hiệu từ dropdown
     Brand b = bf.find(bId); // 1. Tìm đối tượng Brand thực sự từ DB
     Toy t = new Toy(); 
     t.setBrand(b); // 2. Gán nguyên đối tượng Brand vào Toy
     tf.create(t); // 3. Lưu Toy
     ```
   - **Hiển thị (JSP):**
     ```jsp
     <td>${toy.brand.name}</td> <!-- Truy xuất qua đối tượng liên kết -->
     ```

---

## 3. TUẦN 5: QUERIES & TRANSACTION (Dựa trên `jpa-queries`, `jpa-transaction.pdf`)

**Mục tiêu:** Truy vấn phức tạp và an toàn dữ liệu.

### A. Truy vấn JPQL nâng cao (Trong `StudentFacade.java`):
- **Tìm kiếm gần đúng (LIKE):**
  ```java
  public List<Student> findByEmail(String email) {
      return em.createQuery("SELECT s FROM Student s WHERE s.email LIKE :email", Student.class)
               .setParameter("email", "%" + email + "%")
               .getResultList();
  }
  ```
- **Sắp xếp:** `ORDER BY s.firstName ASC`.

### B. Quản lý Giao dịch (Transaction):
- Dùng khi cần cập nhật nhiều bản ghi cùng lúc.
- Trong Facade:
  ```java
  @Resource private UserTransaction utx;
  public void multiUpdate() {
      try {
          utx.begin(); // Bắt đầu
          // ... lệnh 1 (update sv1)
          // ... lệnh 2 (update sv2)
          utx.commit(); // Thành công hết thì lưu
      } catch (Exception e) {
          utx.rollback(); // Một cái lỗi thì hủy hết
      }
  }
  ```

---

## 4. TUẦN 6: HIBERNATE (Dựa trên `HBEx`, `HBWebEx`, `Hibernate.pdf`)

**Mục tiêu:** Sử dụng Hibernate API thuần thay cho JPA.

### A. Cấu hình (`hibernate.cfg.xml`):
- Phải khai báo đủ: `driver_class`, `url`, `username`, `password`, `dialect` và quan trọng nhất là `<mapping class="..."/>`.

### B. HibernateUtil (Lớp tiện ích):
- Đây là "nhà máy" tạo Session. Thầy dùng `StandardServiceRegistryBuilder`.

### C. Lớp Service (`StudentService.java`):
- **Luồng xử lý:** Mở Session -> Bắt đầu Transaction -> Thực thi lệnh -> Commit -> Đóng Session.
- **Lệnh Hibernate:** `session.save()`, `session.update()`, `session.delete()`, `session.get()`.
- **HQL:** `session.createQuery("from Student").list()`.

---

## 5. TỔNG HỢP CÁC MẪU TRUY VẤN DỮ LIỆU (QUAN TRỌNG NHẤT)

Dưới đây là tất cả các kịch bản truy vấn xuất hiện trong các bài lab của thầy. Bạn chỉ cần copy và đổi tên biến.

### A. Truy vấn JPQL (Làm việc trên Class và Biến)

1. **Tìm kiếm chính xác:**
   ```java
   // Tìm theo mã sinh viên
   public Student findById(int id) {
       return em.createQuery("SELECT s FROM Student s WHERE s.id = :id", Student.class)
                .setParameter("id", id)
                .getSingleResult();
   }
   ```

2. **Tìm kiếm gần đúng (LIKE):**
   ```java
   // Tìm sinh viên có email chứa từ khóa
   public List<Student> findByEmail(String email) {
       return em.createQuery("SELECT s FROM Student s WHERE s.email LIKE :email", Student.class)
                .setParameter("email", "%" + email + "%")
                .getResultList();
   }
   ```

3. **Truy vấn theo quan hệ bảng (JOIN):**
   ```java
   // Tìm tất cả Đồ chơi thuộc một Thương hiệu cụ thể
   public List<Toy> findByBrandName(String bName) {
       return em.createQuery("SELECT t FROM Toy t WHERE t.brand.name = :name", Toy.class)
                .setParameter("name", bName)
                .getResultList();
   }
   ```

4. **Sắp xếp và Giới hạn:**
   ```java
   // Lấy 5 sinh viên có điểm cao nhất
   public List<Student> getTop5() {
       return em.createQuery("SELECT s FROM Student s ORDER BY s.mark DESC", Student.class)
                .setMaxResults(5)
                .getResultList();
   }
   ```

### B. Truy vấn Native Query (Dùng SQL thuần)
Dùng khi bạn muốn viết lệnh giống hệt trong SQL Server:
```java
public List<Student> useNative() {
    String sql = "SELECT * FROM Student_Table WHERE mark >= 5";
    return em.createNativeQuery(sql, Student.class).getResultList();
}
```

### C. Kỹ thuật Phân trang (Pagination)
Dùng để chia danh sách thành nhiều trang (ví dụ trang 1, trang 2...):
```java
public List<Student> getPage(int pageNumber, int pageSize) {
    return em.createQuery("SELECT s FROM Student s", Student.class)
             .setFirstResult((pageNumber - 1) * pageSize) // Vị trí bắt đầu lấy
             .setMaxResults(pageSize)                    // Số lượng lấy ra
             .getResultList();
}
```

---

## 6. QUẢN LÝ GIAO DỊCH (TRANSACTION)
Dùng khi bạn thực hiện **nhiều lệnh cùng lúc** và muốn đảm bảo an toàn (ví dụ: trừ tiền người A, cộng tiền người B).

```java
@Resource private UserTransaction utx;
public void transfer() {
    try {
        utx.begin(); // Mở cửa
        // Lệnh 1: Xóa sản phẩm cũ
        // Lệnh 2: Thêm nhật ký xóa
        utx.commit(); // Đóng cửa và lưu vĩnh viễn
    } catch (Exception e) {
        utx.rollback(); // Có lỗi thì hủy hết, quay về như chưa có gì xảy ra
    }
}
```

---

## TỔNG HỢP CÁC LỖI "TỬ THẦN" KHI ĐI THI

1. **Lỗi `web.xml`:** Quên tích chọn "Add to web.xml" khi tạo Servlet -> Trình duyệt báo lỗi 404.
2. **Lỗi JSTL:** Quên thêm thư viện JSTL vào **Libraries** hoặc thiếu dòng `<%@taglib...%>` -> Vòng lặp `c:forEach` không chạy.
3. **Lỗi Facade:** Quên dòng `@EJB` -> Báo lỗi `NullPointerException` khi gọi hàm `findAll`.
4. **Lỗi Database:** Quên khởi động SQL Server hoặc nhập sai tên Database trong `persistence.xml` / `hibernate.cfg.xml`.
5. **Lỗi Ép kiểu:** Lấy ID từ request (`String`) mà dùng trực tiếp cho hàm `find(int)` -> Phải dùng `Integer.parseInt()`.

**Mẹo của thầy:** Hãy luôn dùng tính năng **"Clean and Build"** mỗi khi sửa code để đảm bảo Server nhận bản mới nhất.

**BẠN ĐÃ CÓ ĐẦY ĐỦ VŨ KHÍ. HÃY BÌNH TĨNH LÀM BÀI!**
