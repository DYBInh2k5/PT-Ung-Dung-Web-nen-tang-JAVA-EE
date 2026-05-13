# BÍ KÍP THỰC HÀNH JAVA EE: CRUD JPA (DÀNH RIÊNG CHO THI GIỮA KỲ)

Tài liệu này tổng hợp toàn bộ quy trình, mã nguồn chuẩn của thầy và hướng dẫn cách tùy chỉnh code cho mọi đề bài. **Chỉ cần làm đúng theo các bước này, bạn sẽ hoàn thành bài thi 90 phút một cách chính xác.**

---

## 1. HƯỚNG DẪN TẠO FILE TRÊN NETBEANS 8.2 (CỰC KỲ CHI TIẾT)

Khi đi thi, việc tạo file đúng vị trí và đúng loại là bước quan trọng nhất:

### A. Tạo Servlet (Controller)
1. Chuột phải vào **Source Packages** -> **New** -> **Servlet**.
2. **Class Name:** Ví dụ `ProductController`. **Package:** Ví dụ `controllers`. Nhấn **Next**.
3. **QUAN TRỌNG:** Tích vào ô **"Add information to deployment descriptor (web.xml)"**. Nếu không tích ô này, Servlet sẽ không chạy được.
4. Nhấn **Finish**.

### B. Tạo Entity từ Database (Model)
1. Chuột phải vào **Project Name** -> **New** -> **Entity Classes from Database**.
2. **Data Source:** Chọn kết nối SQL Server của bạn (ví dụ: `jdbc/ExamDS`).
3. Chọn bảng cần làm (ví dụ `Product`) -> Nhấn **Add** để đưa sang bên phải -> **Next**.
4. **Package:** Nhập `entities`. Nhấn **Finish**.

### C. Tạo Session Beans (Facade - Lớp xử lý của thầy)
1. Chuột phải vào **Project Name** -> **New** -> **Session Beans for Entity Classes**.
2. Chọn Entity vừa tạo ở bước B -> **Add** -> **Next**.
3. **Package:** Nhập `sessionbeans`.
4. Nhấn **Finish**. (Bạn sẽ có `AbstractFacade` và `ProductFacade`).

---

## 2. CẤU HÌNH QUAN TRỌNG (PHẢI LÀM TRƯỚC KHI VIẾT CODE)

1. **Thêm Thư viện:** Chuột phải vào thư mục **Libraries** -> **Add Library** -> Chọn **JSTL 1.2.1** và **SQL Server JDBC Driver**.
2. **Kiểm tra Persistence Unit:** Mở file `persistence.xml` (trong thư mục `Configuration Files`). 
   - Nhớ tên `persistence-unit name` (ví dụ: `ExamPU`).
   - Đảm bảo `Table Generation Strategy` là `None` để không làm hỏng Database của trường.

---

## 3. MÃ NGUỒN CHUẨN ĐẦY ĐỦ (TEMPLATES)

### A. Code Controller (Servlet) - Xử lý trọn bộ CRUD
Copy mẫu này và sửa các phần chú thích `[ĐỔI TÊN]`:

```java
@WebServlet(name = "ProductController", urlPatterns = {"/product"}) 
public class ProductController extends HttpServlet {

    @EJB
    private ProductFacade pf; // [ĐỔI TÊN] theo Facade của bạn

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                request.setAttribute("list", pf.findAll());
                request.getRequestDispatcher("list.jsp").forward(request, response);
                break;

            case "delete":
                String idDel = request.getParameter("id");
                pf.remove(pf.find(idDel)); // [LƯU Ý] Nếu ID là INT: pf.remove(pf.find(Integer.parseInt(idDel)));
                response.sendRedirect("product");
                break;

            case "showForm": // Dùng chung cho cả Thêm và Sửa
                String idEdit = request.getParameter("id");
                if (idEdit != null) {
                    request.setAttribute("item", pf.find(idEdit)); // Để sửa
                }
                request.getRequestDispatcher("form.jsp").forward(request, response);
                break;

            case "save": // Lưu dữ liệu (Thêm hoặc Cập nhật)
                String id = request.getParameter("txtId");
                String name = request.getParameter("txtName");
                // [MẸO] Dùng pf.find để kiểm tra xem đã tồn tại chưa
                Product p = pf.find(id);
                boolean isNew = (p == null);
                if (isNew) p = new Product();
                
                p.setId(id); // Nếu ID không tự tăng
                p.setName(name);
                // Xử lý các kiểu dữ liệu khác (BigDecimal cho tiền, Date cho ngày)
                // p.setPrice(new BigDecimal(request.getParameter("txtPrice")));

                if (isNew) pf.create(p); else pf.edit(p);
                response.sendRedirect("product");
                break;
        }
    }
    // Đừng quên gọi processRequest trong doGet và doPost
}
```

### B. Code JSP - Danh sách (list.jsp)
```jsp
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="product?action=showForm">Thêm mới</a>
<table border="1">
    <tr><th>ID</th><th>Tên</th><th>Hành động</th></tr>
    <c:forEach var="p" items="${list}">
        <tr>
            <td>${p.id}</td><td>${p.name}</td>
            <td>
                <a href="product?action=showForm&id=${p.id}">Sửa</a> |
                <a href="product?action=delete&id=${p.id}" onclick="return confirm('Xóa?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
```

### C. Code JSP - Form (form.jsp)
```jsp
<form action="product?action=save" method="POST">
    ID: <input type="text" name="txtId" value="${item.id}" ${empty item ? "" : "readonly"} /><br/>
    Tên: <input type="text" name="txtName" value="${item.name}" /><br/>
    <input type="submit" value="Lưu" />
</form>
```

---

## 4. XỬ LÝ KIỂU DỮ LIỆU ĐẶC BIỆT

1. **Tiền tệ (BigDecimal):** `p.setPrice(new java.math.BigDecimal(request.getParameter("txtPrice")));`
2. **Ngày tháng (Date):** 
   ```java
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   p.setExpDate(sdf.parse(request.getParameter("txtDate")));
   ```
3. **Số nguyên (Integer):** `Integer.parseInt(request.getParameter("txtQty"))`

## 6. KIẾN THỨC NÂNG CAO (QUERIES & PAGINATION)

### A. Phân trang (Pagination) - Dành cho danh sách lớn
Trong Facade, hãy thêm hàm này để chia trang:
```java
public List<Product> findPage(int page, int pageSize) {
    return em.createQuery("SELECT p FROM Product p", Product.class)
             .setFirstResult((page - 1) * pageSize) // Bỏ qua các bản ghi trang trước
             .setMaxResults(pageSize)               // Lấy đúng số lượng của 1 trang
             .getResultList();
}
```

### B. Các loại Truy vấn (Queries)
1. **Named Query:** Được khai báo sẵn ở đầu file Entity (ví dụ: `@NamedQuery(name="Product.findAll", ...)`). 
   - Cách gọi: `em.createNamedQuery("Product.findAll", Product.class).getResultList();`
2. **Native Query:** Viết SQL thuần túy của SQL Server.
   - Cách gọi: `em.createNativeQuery("SELECT * FROM Product", Product.class).getResultList();`

### C. Quản lý Giao dịch (Transaction)
Nếu đề bài yêu cầu cập nhật nhiều bảng cùng lúc, bạn phải dùng Transaction:
```java
@Resource private UserTransaction utx;
public void updateData() {
    try {
        utx.begin();
        // ... thực hiện pf.create, pf.edit ...
        utx.commit();
    } catch (Exception e) {
        utx.rollback();
    }
}
```

## 7. PHÒNG HỜ: HIBERNATE (HBEX & HBWEBEX)

Nếu đề bài yêu cầu dùng Hibernate thay vì JPA, hãy dùng các mẫu sau:

### A. Cấu hình Hibernate (`hibernate.cfg.xml`)
Đặt trong thư mục `Source Packages`:
```xml
<hibernate-configuration>
  <session-factory>
    <property name="hibernate.connection.driver_class">com.microsoft.sqlserver.jdbc.SQLServerDriver</property>
    <property name="hibernate.connection.url">jdbc:sqlserver://localhost:1433;databaseName=HBDemo</property>
    <property name="hibernate.connection.username">sa</property>
    <property name="hibernate.connection.password">1</property>
    <property name="hibernate.dialect">org.hibernate.dialect.SQLServerDialect</property>
    <mapping class="model.Student"/> <!-- KHAI BÁO ENTITY Ở ĐÂY -->
  </session-factory>
</hibernate-configuration>
```

### B. Lớp Dịch vụ Hibernate (StudentService.java)
```java
public class StudentService {
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    public List<Student> getAll() {
        Session session = factory.openSession();
        List<Student> list = session.createQuery("from Student").list();
        session.close();
        return list;
    }

    public void save(Student s) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(s); // Lưu hoặc cập nhật
        tx.commit();
        session.close();
    }
}
```

---

## 8. BẢNG TRA CỨU NHANH KHI ĐỔI ĐỀ BÀI

| Nếu đề là... | Sửa Entity | Sửa Facade | Sửa URL | Sửa biến JSP |
| :--- | :--- | :--- | :--- | :--- |
| **Sinh viên** | `Student` | `StudentFacade sf` | `/student` | `${p.studentId}`, `${p.fullName}` |
| **Đồ chơi** | `Toy` | `ToyFacade tf` | `/toy` | `${p.id}`, `${p.toyName}` |
| **Sản phẩm** | `Product` | `ProductFacade pf` | `/product` | `${p.id}`, `${p.name}` |

## 9. CÁC PHÍM TẮT VÀ MẸO NHANH TRÊN NETBEANS 8.2

1. **Gợi ý Code:** Nhấn `Ctrl + Space` để hiện gợi ý (rất hữu ích khi quên tên hàm).
2. **Tự động thêm Import:** Nhấn `Ctrl + Shift + I` để NetBeans tự động thêm các dòng `import`.
3. **Sinh Code tự động (Insert Code):** Nhấn **`Alt + Insert`**.
   - **Nên dùng lúc nào?**
     - **Constructor:** Khi cần tạo hàm khởi tạo nhanh.
     - **Getter and Setter:** Khi vừa khai báo xong các biến (fields) trong Entity hoặc Model.
     - **Call Enterprise Bean:** Khi đang ở Servlet, muốn gọi Facade mà quên cú pháp `@EJB`. NetBeans sẽ tự chèn code `@EJB private ...`.
     - **Persistence:** Khi cần chèn mã liên quan đến `EntityManager`.
4. **Căn chỉnh code thẳng hàng:** Nhấn `Alt + Shift + F`.

---

**BỘ TÀI LIỆU ĐÃ HOÀN TẤT 100%. CHÚC BẠN TỰ TIN CHIẾN THẮNG!**
