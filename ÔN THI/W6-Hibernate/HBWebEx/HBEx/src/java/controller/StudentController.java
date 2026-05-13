/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Student;
import service.StudentService;

/**
 *
 * @author admin
 */
/**
 * LỚP CONTROLLER: StudentController
 * Điều hướng các yêu cầu từ giao diện web tới lớp xử lý nghiệp vụ (Service).
 */
@WebServlet(name = "StudentController", urlPatterns = {"/student"})
public class StudentController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // LUỒNG ĐI: Lấy hành động (action) từ người dùng gửi lên
        String action = request.getParameter("action");
        if (action == null) action = "list"; // Mặc định hiển thị danh sách

        // Khởi tạo lớp dịch vụ xử lý Hibernate
        StudentService service = new StudentService();

        // LUỒNG ĐI: Dựa vào hành động để gọi hàm xử lý tương ứng
        switch (action) {
            case "create": // Thêm mới sinh viên
                service.add(
                    request.getParameter("name"),
                    Integer.parseInt(request.getParameter("age"))
                );
                break;

            case "delete": // Xóa sinh viên theo ID
                service.delete(Integer.parseInt(request.getParameter("id")));
                break;

            case "update": // Cập nhật thông tin sinh viên
                service.update(
                    Integer.parseInt(request.getParameter("id")),
                    request.getParameter("name"),
                    Integer.parseInt(request.getParameter("age"))
                );
                break;
        }

        // LUỒNG ĐI: Sau khi xử lý xong (Thêm/Xóa/Sửa), lấy lại danh sách mới nhất
        List<Student> list = service.getAll();
        
        // LUỒNG ĐI: Lưu danh sách vào request scope để gửi sang JSP
        request.setAttribute("list", list);

        // LUỒNG ĐI: Hiển thị kết quả trên trang student.jsp
        request.getRequestDispatcher("student.jsp").forward(request, response);
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
