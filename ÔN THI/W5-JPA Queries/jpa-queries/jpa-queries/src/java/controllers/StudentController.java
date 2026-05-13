/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sessionbeans.StudentFacade;

/**
 *
 * @author PHT
 */
/**
 * LỚP CONTROLLER: StudentController
 * Xử lý các hành động liên quan đến Sinh viên (Student).
 */
@WebServlet(name = "StudentController", urlPatterns = {"/student"})
public class StudentController extends HttpServlet {

    @EJB(name = "sf")
    private StudentFacade sf; // Tiêm Facade để xử lý dữ liệu

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // LUỒNG ĐI: Lấy tham số 'action' từ URL (ví dụ: ?action=findByFirstName)
        String action = request.getParameter("action");
        
        switch (action) {
            case "findByFirstName": // Tìm theo tên
                findByFirstName(request, response);
                break;
            case "findByEmail": // Tìm theo email
                findByEmail(request, response);
                break;
            case "orderByFirstName": // Sắp xếp theo tên
                orderByFirstName(request, response);
                break;
            case "useNativeQuery": // Dùng SQL thuần
                useNativeQuery(request, response);
                break;
            case "manageTransaction": // Quản lý giao dịch
                manageTransaction(request, response);
                break;
            default:
                System.out.println("Hành động không hợp lệ");
        }
    }

    /**
     * CHI TIẾT LUỒNG ĐI:
     * 1. Lấy dữ liệu từ tham số 'firstName' trong request.
     * 2. Gọi Facade để thực hiện truy vấn JPQL.
     * 3. In kết quả ra màn hình Console (để kiểm tra).
     */
    protected void findByFirstName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        List<Student> list = sf.findByFirstName(firstName);
        for (Student s : list) {
            System.out.println("Id: " + s.getId() + " | Name: " + s.getFirstName());
        }
    }

    /**
     * CHI TIẾT LUỒNG ĐI:
     * 1. Lấy email từ request.
     * 2. Gọi Facade thực hiện truy vấn LIKE.
     * 3. In kết quả.
     */
    protected void findByEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        List<Student> list = sf.findByEmail(email);
        for (Student s : list) {
            System.out.println("Email: " + s.getEmail());
        }
    }

    /**
     * CHI TIẾT LUỒNG ĐI:
     * 1. Gọi Facade cập nhật nhiều sinh viên cùng lúc.
     * 2. Sau khi commit, gọi lại hàm hiển thị để kiểm tra dữ liệu mới.
     */
    protected void manageTransaction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sf.mangeTransaction();        
        System.out.println("Đã cập nhật dữ liệu của 2 sinh viên thông qua Transaction");
        this.useNativeQuery(request, response);
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
