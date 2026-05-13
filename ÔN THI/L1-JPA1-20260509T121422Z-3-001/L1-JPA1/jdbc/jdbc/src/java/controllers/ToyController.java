/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.Brand;
import db.BrandFacade;
import db.Toy;
import db.ToyFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PHT
 */
@WebServlet(name = "ToyController", urlPatterns = {"/toy"})
public class ToyController extends HttpServlet {

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
        String op = request.getParameter("op");
        switch (op) {
            case "create":
                //hiện form create (create.jsp)
                create(request, response);
                break;
            case "create_handler":
                //xử lý form create
                create_handler(request, response);
                break;
            case "edit":
                //hiện form edit (edit.jsp)
                edit(request, response);
                break;
            case "edit_handler":
                //xử lý form edit (edit.jsp)
                edit_handler(request, response);
                break;
            case "delete":
                //hiện form xác nhận delete
                delete(request, response);
                break;
            case "delete_handler":
                //xử lý form xác nhận delete
                delete_handler(request, response);
                break;
            default:
                index(request, response);
                break;
        }
    }

    protected void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ToyFacade tf = new ToyFacade();
            //Đọc table Toy
            List<Toy> list = tf.read();
            //Lưu list vào request để truyền list cho view toy.jsp
            request.setAttribute("list", list);
        } catch (SQLException ex) {
            //Lưu thông báo lỗi vào request để truyền thông báo lỗi cho view toy.jsp
            request.setAttribute("message", ex.getMessage());
            //In chi tiết lỗi
            ex.printStackTrace();
        }
        //Chuyển request & response cho view toy.jsp xử lý tiếp
        request.getRequestDispatcher("/toy.jsp").forward(request, response);
    }

    protected void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BrandFacade bf = new BrandFacade();
            List<Brand> list = bf.read();
            //truyền list cho view
            request.setAttribute("list", list);            
        } catch (SQLException ex) {
            //Lưu thông báo lỗi vào request để truyền thông báo lỗi cho view
            request.setAttribute("message", "Can't read table Brand.");
            //In chi tiết lỗi
            ex.printStackTrace();
        }
        //Chuyển request & response cho view create.jsp xử lý tiếp
        request.getRequestDispatcher("/create.jsp").forward(request, response);
    }

    protected void create_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String choice = request.getParameter("choice");
        switch (choice) {
            case "create":
                try {
                    //lấy dữ liệu từ form create
                    String id = request.getParameter("id");
                    String name = request.getParameter("name");
                    double price = Double.parseDouble(request.getParameter("price"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date expDate = sdf.parse(request.getParameter("expDate"));
                    String brand = request.getParameter("brand");
                    //tạo object toy
                    Toy toy = new Toy();
                    toy.setId(id);
                    toy.setName(name);
                    toy.setPrice(price);
                    toy.setExpDate(expDate);
                    toy.setBrand(brand);
                    //insert object toy vào table Toy
                    ToyFacade tf = new ToyFacade();
                    tf.create(toy);
                    //goi index() để hiện danh sách toy
                    index(request, response);
                } catch (Exception ex) {
                    //Lưu thông báo lỗi vào request để truyền thông báo lỗi cho view toy.jsp
                    request.setAttribute("message", "Can't insert new toy.");
                    //In chi tiết lỗi
                    ex.printStackTrace();
                    //cho hiện trang create.jsp để user xem lại
                    create(request, response);
                }
                break;
            default: //cancel
                //goi index() để hiện danh sách toy
                index(request, response);
        }
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        //Chuyển request & response cho view delete.jsp xử lý tiếp
        request.getRequestDispatcher("/delete.jsp").forward(request, response);
    }
    
    protected void delete_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String choice = request.getParameter("choice");
        switch (choice) {
            case "yes":
                try {
                    //lấy dữ liệu từ form delete
                    String id = request.getParameter("id");
                    //insert object toy vào table Toy
                    ToyFacade tf = new ToyFacade();
                    tf.delete(id);
                    //goi index() để hiện danh sách toy
                    index(request, response);
                } catch (Exception ex) {
                    //Lưu thông báo lỗi vào request để truyền thông báo lỗi cho view delete.jsp
                    request.setAttribute("message", "Can't delete toy.");
                    //In chi tiết lỗi
                    ex.printStackTrace();
                    //cho hiện trang delete.jsp để user xem lại
                    delete(request, response);
                }
                break;
            default: //cancel
                //goi index() để hiện danh sách toy
                index(request, response);
        }
    }
    
    protected void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //lấy id từ form
            String id = request.getParameter("id");
            //load toy từ db
            ToyFacade tf = new ToyFacade();
            Toy toy = tf.read(id);
            //load table Brand
            BrandFacade bf = new BrandFacade();
            List<Brand> list = bf.read();
            //truyền toy cho view
            request.setAttribute("toy", toy);
            //truyền list cho view
            request.setAttribute("list", list);            
        } catch (SQLException ex) {
            //Lưu thông báo lỗi vào request để truyền thông báo lỗi cho view
            request.setAttribute("message", "Can't load data from database.");
            //In chi tiết lỗi
            ex.printStackTrace();
        }
        //Chuyển request & response cho view edit.jsp xử lý tiếp
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }
    
    protected void edit_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String choice = request.getParameter("choice");
        switch (choice) {
            case "update":
                try {
                    //lấy dữ liệu từ form edit
                    String id = request.getParameter("id");
                    String name = request.getParameter("name");
                    double price = Double.parseDouble(request.getParameter("price"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date expDate = sdf.parse(request.getParameter("expDate"));
                    String brand = request.getParameter("brand");
                    //tạo object toy
                    Toy toy = new Toy();
                    toy.setId(id);
                    toy.setName(name);
                    toy.setPrice(price);
                    toy.setExpDate(expDate);
                    toy.setBrand(brand);
                    //update object toy vào table Toy
                    ToyFacade tf = new ToyFacade();
                    tf.update(toy);
                    //goi index() để hiện danh sách toy
                    index(request, response);
                } catch (Exception ex) {
                    //Lưu thông báo lỗi vào request để truyền thông báo lỗi cho view toy.jsp
                    request.setAttribute("message", "Can't update toy.");
                    //In chi tiết lỗi
                    ex.printStackTrace();
                    //cho hiện trang edit.jsp để user xem lại
                    edit(request, response);
                }
                break;
            default: //cancel
                //goi index() để hiện danh sách toy
                index(request, response);
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
