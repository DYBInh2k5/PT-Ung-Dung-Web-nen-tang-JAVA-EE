/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PHT
 * Cung cấp các phương thức truy cập vào table Toy
 * CRUD
 */
public class ToyFacade {
    
    public List<Toy> read() throws SQLException{
        List<Toy> list = null;
        //Tạo connection để kết nối vào db
        Connection con = DBContext.getConnection();
        //Tạo đối tượng Statement để thực hiện lệnh SQL
        Statement stm = con.createStatement();
        //Thực hiện câu lệnh SQL
        ResultSet rs = stm.executeQuery("select * from Toy");
        //Đọc từng record và lưu List<Toy>
        list = new ArrayList<>();
        while(rs.next()){
            //Đọc một record và lưu vào đối tượng toy
            Toy toy = new Toy();
            toy.setId(rs.getString("id"));
            toy.setName(rs.getString("name"));
            toy.setPrice(rs.getDouble("price"));
            toy.setExpDate(rs.getDate("expDate"));
            toy.setBrand(rs.getString("brand"));
            //Thêm toy vào list
            list.add(toy);
        }        
        //Đóng kết nối từ ứng dụng vào db để giải phóng tài nguyên
        con.close();
        return list;
    }
    
    public Toy read(String id) throws SQLException{
        //Tạo connection để kết nối vào db
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement để thực hiện lệnh SQL có tham số
        PreparedStatement stm = con.prepareStatement("select * from Toy where id=?");
        //Truyền giá trị cho các tham số
        stm.setString(1, id);
        //Thực hiện câu lệnh SQL
        ResultSet rs = stm.executeQuery();
        Toy toy = null;
        if(rs.next()){
            //Đọc một record và lưu vào đối tượng toy
            toy = new Toy();
            toy.setId(rs.getString("id"));
            toy.setName(rs.getString("name"));
            toy.setPrice(rs.getDouble("price"));
            toy.setExpDate(rs.getDate("expDate"));
            toy.setBrand(rs.getString("brand"));
        }      
        //Đóng kết nối từ ứng dụng vào db để giải phóng tài nguyên
        con.close();
        return toy;
    }
    
    //Insert object toy vào table Toy
    public void create(Toy toy) throws SQLException{
        //Tạo connection để kết nối vào db
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement để thực hiện lệnh SQL có tham số
        PreparedStatement stm = con.prepareStatement("insert Toy values(?, ?, ?, ?, ?)");
        //Truyền giá trị cho các tham số
        stm.setString(1, toy.getId());
        stm.setString(2, toy.getName());
        stm.setDouble(3, toy.getPrice());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        stm.setString(4, sdf.format(toy.getExpDate()));
        stm.setString(5, toy.getBrand());
        //Thực hiện câu lệnh SQL
        int count = stm.executeUpdate();        
        //Đóng kết nối từ ứng dụng vào db để giải phóng tài nguyên
        con.close();
    }
    
    public void delete(String id) throws SQLException{
        //Tạo connection để kết nối vào db
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement để thực hiện lệnh SQL có tham số
        PreparedStatement stm = con.prepareStatement("delete from Toy where id=?");
        //Truyền giá trị cho các tham số
        stm.setString(1, id);
        //Thực hiện câu lệnh SQL
        int count = stm.executeUpdate();        
        //Đóng kết nối từ ứng dụng vào db để giải phóng tài nguyên
        con.close();
    }
    
    public void update(Toy toy) throws SQLException{
        //Tạo connection để kết nối vào db
        Connection con = DBContext.getConnection();
        //Tạo đối tượng PreparedStatement để thực hiện lệnh SQL có tham số
        PreparedStatement stm = con.prepareStatement("update Toy set name=?, price=?, expDate=?, brand=? where id=?");
        //Truyền giá trị cho các tham số        
        stm.setString(1, toy.getName());
        stm.setDouble(2, toy.getPrice());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        stm.setString(3, sdf.format(toy.getExpDate()));
        stm.setString(4, toy.getBrand());
        stm.setString(5, toy.getId());
        //Thực hiện câu lệnh SQL
        int count = stm.executeUpdate();        
        //Đóng kết nối từ ứng dụng vào db để giải phóng tài nguyên
        con.close();
    }
    
}
