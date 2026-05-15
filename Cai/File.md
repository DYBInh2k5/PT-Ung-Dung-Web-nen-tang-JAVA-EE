1. TẠO PROJECT WEB APPLICATION

File → New Project
Chọn Java Web → Web Application → Next
Đặt Project Name (ví dụ: QuanLySanPham) → Next
Server: Chọn GlassFish Server (phải đang chạy)
Context Path: để mặc định → Finish


2. TẠO ENTITY (Model) - Entity Classes from Database

Right-click vào Project → New → Entity Classes from Database
Data Source: Chọn kết nối Database của bạn (nếu chưa có thì tạo trước)
Bên trái hiện danh sách bảng → Chọn bảng cần dùng (ví dụ: Product) → Nhấn Add → Next
Package: Nhập entities → Finish

NetBeans sẽ tự tạo class Entity với @Entity, @Id, @Table...

3. TẠO FACADE (Session Bean)

Right-click Project → New → Session Beans for Entity Classes
Chọn Entity vừa tạo (ví dụ Product) → Nhấn Add → Next
Package: Nhập sessionbeans → Finish

Bạn sẽ có 2 file:
AbstractFacade.java
ProductFacade.java


4. TẠO SERVLET (Controller)
Đây là bước rất quan trọng, dễ bị mất điểm

Right-click Source Packages → New → Servlet
Class Name: Ví dụ ProductController
Package: controllers
Nhấn Next
QUAN TRỌNG: Phải tích chọn ô "Add information to deployment descriptor (web.xml)"
Nhấn Finish

Sau khi tạo xong, mở file Servlet và viết code processRequest().

5. TẠO FILE JSP (View)
Có 2 cách tạo:
Cách 1 (Khuyến nghị):

Right-click vào Web Pages → New → JSP
Đặt tên file:
list.jsp hoặc product.jsp
form.jsp

Package/Thư mục: Có thể tạo thư mục con (ví dụ: product/list.jsp)

Cách 2: Right-click Web Pages → New → Folder để tạo thư mục trước, sau đó tạo JSP bên trong.

6. TẠO CÁC FILE KHÁC (Hibernate)
Hibernate Configuration (hibernate.cfg.xml):

Right-click Source Packages → New → Other
Chọn XML → XML Document → Next
File Name: hibernate.cfg.xml → Finish
Xóa hết nội dung mặc định và dán code cấu hình của thầy vào.

HibernateUtil.java:

Right-click package (thường là util) → New → Java Class
Tên: HibernateUtil


Bảng Tóm Tắt Cách Tạo File Nhanh















































Loại FileCách Tạo (Menu)Package/Folder khuyến nghịLưu ý quan trọngEntityEntity Classes from DatabaseentitiesChọn đúng Data SourceFacade (Session Bean)Session Beans for Entity ClassessessionbeansPhải tạo sau EntityServlet (Controller)New → ServletcontrollersPhải tích web.xmlJSPNew → JSPWeb Pages hoặc thư mục conThêm JSTL taglibhibernate.cfg.xmlNew → XML DocumentSource PackagesKhai báo mapping classJava Class (Model, Service...)New → Java ClassTùy theo (models, service...)-
