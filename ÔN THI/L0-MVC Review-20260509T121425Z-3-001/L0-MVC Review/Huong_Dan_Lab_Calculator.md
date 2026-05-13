# Hướng Dẫn Lab: Máy Tính Cơ Bản (MVC Review)

---

## HƯỚNG DẪN TẠO FILE NHANH TRÊN NETBEANS 8.2

### 1. Tạo Class Java (Model)
- Chuột phải **Source Packages** -> **New** -> **Java Class**.
- Tên: `Calculator`, Package: `models`.

### 2. Tạo Servlet (Controller)
- Chuột phải **Source Packages** -> **New** -> **Servlet**.
- Tên: `CalculatorController`, Package: `controllers`.
- **Lưu ý:** Tích chọn **"Add information to deployment descriptor (web.xml)"**.

### 3. Tạo trang JSP (View)
- Chuột phải **Web Pages** -> **New** -> **JSP**.
- Tên: `index`.

---

## LUỒNG HOẠT ĐỘNG CỦA MÁY TÍNH (MVC FLOW)

1. **View (index.jsp):** Người dùng nhập 2 số và chọn phép tính, nhấn "Tính toán". Form gửi dữ liệu sang Servlet.
2. **Controller (CalculatorController):**
   - Dùng `request.getParameter` để lấy 2 số và phép tính (Cộng/Trừ/...).
   - Ép kiểu dữ liệu từ String sang Double.
   - Khởi tạo đối tượng **Calculator** (Model).
   - Lưu đối tượng này vào `request.setAttribute`.
   - Chuyển tiếp (`forward`) về lại trang `index.jsp`.
3. **Model (Calculator.java):** Chứa công thức tính toán và trả về kết quả.
4. **View (index.jsp):** Dùng EL `${model.result}` để lấy kết quả từ Model và in ra màn hình.

---

## 1. Yêu cầu bài tập
Xây dựng ứng dụng máy tính thực hiện các phép tính: Cộng, Trừ, Nhân, Chia giữa hai số thực được nhập từ người dùng.

---

## 2. Cấu trúc Model (JavaBean)
Tạo class `models.Calculator` để xử lý logic tính toán.

```java
package models;

public class Calculator {
    private double num1, num2;
    private String op;

    // Constructors
    public Calculator() {}
    public Calculator(double num1, double num2, String op) {
        this.num1 = num1;
        this.num2 = num2;
        this.op = op;
    }

    // Getters và Setters cho các thuộc tính
    // ...

    // Logic tính toán chính
    public Object getResult() {
        switch(op){
            case "add": return num1 + num2;
            case "sub": return num1 - num2;
            case "mul": return num1 * num2;
            case "div": 
                if(num2 == 0) return "Lỗi: Không thể chia cho 0";
                return num1 / num2;
            default: return null;
        }
    }
}
```

---

## 3. Cấu trúc Controller (Servlet)
Tạo `controllers.CalculatorController` để nhận dữ liệu và điều hướng.

```java
@WebServlet(name = "CalculatorController", urlPatterns = {"/calculator"})
public class CalculatorController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Lấy dữ liệu từ JSP
        double n1 = Double.parseDouble(request.getParameter("num1"));
        double n2 = Double.parseDouble(request.getParameter("num2"));
        String op = request.getParameter("op");

        // 2. Tạo Model và tính toán
        Calculator model = new Calculator(n1, n2, op);

        // 3. Lưu Model vào request để JSP hiển thị
        request.setAttribute("model", model);

        // 4. Chuyển tiếp về trang index.jsp
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    // ... doGet và doPost ...
}
```

---

## 4. Giao diện (JSP)
Trong `index.jsp`, thiết kế form nhập liệu và hiển thị kết quả.

```jsp
<form action="calculator" method="POST">
    Số 1: <input type="text" name="num1" value="${model.num1}" /><br/>
    Số 2: <input type="text" name="num2" value="${model.num2}" /><br/>
    Phép tính: 
    <select name="op">
        <option value="add">+</option>
        <option value="sub">-</option>
        <option value="mul">*</option>
        <option value="div">/</option>
    </select><br/>
    <input type="submit" value="Tính toán" />
</form>

<hr/>
<h3>Kết quả: ${model.result}</h3>
```

---

## Lưu ý quan trọng:
1. **Kiểu dữ liệu:** Dữ liệu lấy từ `request.getParameter` luôn là String, cần dùng `Double.parseDouble` để chuyển đổi.
2. **Action của Form:** Phải khớp với `urlPatterns` của Servlet (`calculator`).
3. **EL Expression:** Dùng `${model.result}` để gọi hàm `getResult()` trong class Calculator.
