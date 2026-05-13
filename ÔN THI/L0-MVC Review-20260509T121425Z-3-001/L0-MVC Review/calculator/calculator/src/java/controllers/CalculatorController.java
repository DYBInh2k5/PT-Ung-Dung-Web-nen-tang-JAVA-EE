/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Calculator;

/**
 * LỚP CONTROLLER: CalculatorController
 * Đây là thành phần điều hướng (Controller) trong mô hình MVC.
 * Nhiệm vụ: Nhận request từ Client, xử lý dữ liệu và gửi kết quả về View.
 */
@WebServlet(name = "CalculatorController", urlPatterns = {"/calculator"})
public class CalculatorController extends HttpServlet {

    /**
     * PHƯƠNG THỨC processRequest:
     * Đây là nơi tập trung xử lý cho cả yêu cầu GET và POST từ trình duyệt.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // 1. LUỒNG ĐI: Lấy dữ liệu (tham số) gửi lên từ trình duyệt (Form trong index.jsp)
        // Lưu ý: request.getParameter luôn trả về String, nên cần dùng Double.parseDouble để ép kiểu số.
        double num1 = Double.parseDouble(request.getParameter("num1"));
        double num2 = Double.parseDouble(request.getParameter("num2"));
        String op = request.getParameter("op");
        
        // 2. LUỒNG ĐI: Khởi tạo đối tượng Model (Calculator) và truyền dữ liệu vừa lấy được vào
        Calculator model = new Calculator(num1, num2, op);
        
        // 3. LUỒNG ĐI: Lưu đối tượng Model vào phạm vi request (request scope)
        // Với tên là "model", JSP sẽ truy cập dữ liệu thông qua tên này (ví dụ: ${model.num1})
        request.setAttribute("model", model);
        
        // 4. LUỒNG ĐI: Chuyển tiếp (forward) yêu cầu và dữ liệu sang trang View (index.jsp) để hiển thị kết quả
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
