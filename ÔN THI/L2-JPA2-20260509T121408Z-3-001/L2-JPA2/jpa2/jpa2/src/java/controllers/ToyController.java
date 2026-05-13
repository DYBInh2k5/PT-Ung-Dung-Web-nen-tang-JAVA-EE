/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Brand;
import entities.Toy;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sessionbeans.BrandFacade;
import sessionbeans.ToyFacade;

/**
 *
 * @author PHT
 */
@WebServlet(name = "ToyController", urlPatterns = {"/toy"})
public class ToyController extends HttpServlet {

    @EJB(name = "bf")
    private BrandFacade bf;

    @EJB(name = "tf")
    private ToyFacade tf;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                //hiện view toy.jsp
                list(request, response);
                break;
            case "edit":
                //hiện form edit
                edit(request, response);
                break;
            case "edit_handler":
                //xử lý form edit
                edit_handler(request, response);
                break;
            case "delete":
                //hiện form delete
                delete(request, response);
                break;
            case "delete_handler":
                //xử lý form delete
                delete_handler(request, response);
                break;
        }

    }

    protected void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //doc toan bo table Toy
            List<Toy> list = tf.findAll();
            //luu list vao request de view toy.jsp truy cap
            request.setAttribute("list", list);
            //chuyen request & response cho view toy.jsp xu ly tiep
            request.getRequestDispatcher("/toy.jsp").forward(request, response);
        } catch (Exception ex) {
            //lưu thông báo lỗi vào request để view error.jsp truy cập
            request.setAttribute("errorMessage", "Can't read data");
            //chuyen request & response cho view error.jsp xu ly tiep
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void edit_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String op = request.getParameter("op");
            switch (op) {
                case "update":
                    //lấy thông tin từ client 
                    String id = request.getParameter("id");
                    String name = request.getParameter("name");
                    BigDecimal price = new BigDecimal(request.getParameter("price"));
                    String expDate = request.getParameter("expDate");
                    String brandId = request.getParameter("brandId");
                    //tạo object toy
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Brand brand = bf.find(brandId);
                    Toy toy = new Toy(id, name, price, sdf.parse(expDate), brand);
                    //cập nhật toy vào db
                    tf.edit(toy);
                    //hiện toy list
                    request.getRequestDispatcher("toy?action=list").forward(request, response);
                    break;
                case "cancel":
                    //chuyen request & response cho view toy.jsp xu ly tiep
                    request.getRequestDispatcher("toy?action=list").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            //lưu thông báo lỗi vào request để view error.jsp truy cập
            request.setAttribute("errorMessage", "Can't read data");
            //chuyen request & response cho view error.jsp xu ly tiep
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //lấy thông tin từ client(lấy id)
            String id = request.getParameter("id");
            //đọc mẫu toy tương ứng với id
            Toy toy = tf.find(id);
            //đọc toàn bộ table Brand để tạo combo box
            List<Brand> list = bf.findAll();
            //truyền toy và list cho view edit.jsp 
            request.setAttribute("toy", toy);
            request.setAttribute("list", list);
            //chuyen request & response cho view edit.jsp xu ly tiep
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        } catch (Exception ex) {
            //lưu thông báo lỗi vào request để view error.jsp truy cập
            request.setAttribute("errorMessage", "Can't read data");
            //chuyen request & response cho view error.jsp xu ly tiep
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //hiện form delete
        request.getRequestDispatcher("/delete.jsp").forward(request, response);
    }

    protected void delete_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String op = request.getParameter("op");
            switch (op) {
                case "yes":
                    //lấy thông tin từ client
                    String id = request.getParameter("id");
                    //lấy object toy tương ứng với id từ db
                    Toy toy = tf.find(id);
                    //xóa toy trong db
                    tf.remove(toy);                    
                    break;            
            }
            //cho hiện toy list
            request.getRequestDispatcher("toy?action=list").forward(request, response);
        } catch (Exception ex) {
            //lưu thông báo lỗi vào request để view error.jsp truy cập
            request.setAttribute("errorMessage", "Can't read data");
            //chuyen request & response cho view error.jsp xu ly tiep
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
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
