/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Toy;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sessionbeans.ToyFacade;

/**
 *
 * @author PHT
 */
/**
 * LỚP CONTROLLER: ToyController
 * Điều hướng yêu cầu liên quan đến quản lý đồ chơi (Toy).
 */
@WebServlet(name = "ToyController", urlPatterns = {"/toy"})
public class ToyController extends HttpServlet {

    /**
     * @EJB: Tiêm Session Bean (ToyFacade) vào Controller.
     * Nhờ đó Controller có thể gọi các hàm findAll, create, remove...
     */
    @EJB(name = "tf")
    private ToyFacade tf;

    /**
     * PHƯƠNG THỨC processRequest:
     * LUỒNG ĐI: Client gửi yêu cầu đến /toy -> Servlet này tiếp nhận.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // 1. LUỒNG ĐI: Gọi Facade để đọc toàn bộ dữ liệu từ bảng Toy trong DB
        List<Toy> list = tf.findAll();
        
        // 2. LUỒNG ĐI: Lưu danh sách Toy vào request scope với tên "list"
        request.setAttribute("list", list);
        
        // 3. LUỒNG ĐI: Chuyển yêu cầu (forward) sang trang View (toy.jsp) để hiển thị danh sách
        request.getRequestDispatcher("/toy.jsp").forward(request, response);
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
