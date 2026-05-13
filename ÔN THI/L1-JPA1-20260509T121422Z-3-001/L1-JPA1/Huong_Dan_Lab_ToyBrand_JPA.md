# Hướng Dẫn Lab: Quản Lý Đồ Chơi (Bám sát code thầy)

Bài lab này tập trung vào kỹ thuật CRUD sử dụng JPA trên hai bảng có quan hệ **Many-to-One** (Toy & Brand).

---

## LUỒNG HOẠT ĐỘNG (FLOW)
1. **Controller:** Nhận yêu cầu tạo Toy mới.
2. **Controller:** Lấy `brandId` từ Form, gọi `BrandFacade.find(id)` để lấy đối tượng Brand thực sự từ DB.
3. **Controller:** Gán đối tượng Brand vừa tìm được vào Toy (`toy.setBrand(brand)`).
4. **Facade:** Lưu Toy xuống DB. Khi này JPA tự động điền `brandId` vào cột khóa ngoại trong bảng `Toy`.
5. **View:** Khi hiển thị, nhờ quan hệ `@ManyToOne`, bạn chỉ cần dùng `${toy.brand.name}` để JPA tự động JOIN bảng và lấy tên thương hiệu.

---

## 1. Cấu trúc Entity chuẩn của thầy
Khi dùng NetBeans tạo Entity từ Database, thầy luôn giữ các annotation mặc định:

```java
@Entity
@Table(name = "Toy")
public class Toy implements Serializable {
    @Id
    @Column(name = "Id")
    private String id;
    
    @Column(name = "Name")
    private String name;

    @JoinColumn(name = "Brand", referencedColumnName = "Id")
    @ManyToOne
    private Brand brand; // Quan hệ với bảng Brand
}
```

---

## 2. Cấu trúc Facade (Session Bean) chuẩn của thầy
Thầy luôn dùng `@Stateless` và kế thừa `AbstractFacade`.

```java
@Stateless
public class ToyFacade extends AbstractFacade<Toy> {
    @PersistenceContext(unitName = "jpa2PU") // Nhớ kiểm tra đúng tên PU trong persistence.xml
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ToyFacade() {
        super(Toy.class);
    }
}
```

---

## 3. Xử lý Controller (ToyController) theo cách của thầy

```java
@WebServlet(name = "ToyController", urlPatterns = {"/toy"})
public class ToyController extends HttpServlet {
    @EJB
    private ToyFacade tf;
    @EJB
    private BrandFacade bf;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                // Thầy luôn gửi toàn bộ list sang JSP
                request.setAttribute("list", tf.findAll());
                request.getRequestDispatcher("toy.jsp").forward(request, response);
                break;
                
            case "create":
                // Thầy lấy ID Brand trước, sau đó find Brand đối tượng rồi mới gán vào Toy
                String bId = request.getParameter("brandId");
                Brand brand = bf.find(bId);

                Toy t = new Toy();
                t.setId(request.getParameter("id"));
                t.setName(request.getParameter("name"));
                t.setBrand(brand); // Gán đối tượng Brand vào Toy

                tf.create(t);
                response.sendRedirect("toy");
                break;
        }
    }
}
```

---

## CÁCH THAY ĐỔI CODE KHI ĐI THI (LƯU Ý CHI TIẾT)

| Tình huống | Cách sửa trong code của thầy |
| :--- | :--- |
| **Đổi bảng khác** | Thay `ToyFacade` thành `ProductFacade`, `BrandFacade` thành `CategoryFacade`. |
| **Hiển thị tên Brand** | Trong JSP, dùng `${toy.brand.name}` thay vì `${toy.brand}` (vì brand là một Object). |
| **Lỗi PU** | Nếu báo lỗi kết nối, hãy mở file `persistence.xml` và copy chính xác `persistence-unit name` dán vào `@PersistenceContext`. |
| **Thêm mới thất bại** | Kiểm tra xem khóa chính (ID) có bị trùng không. Thầy hay dùng `String` cho ID, nếu dùng `INT` thì phải parse. |

**Ghi chú:** Thầy thường dùng GlassFish Server, nên hãy đảm bảo server đã chạy trước khi deploy bài làm.
