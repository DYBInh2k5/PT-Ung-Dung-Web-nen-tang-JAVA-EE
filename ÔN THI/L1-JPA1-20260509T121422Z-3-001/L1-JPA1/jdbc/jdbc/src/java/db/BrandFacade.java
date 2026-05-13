/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PHT
 */
public class BrandFacade {
    public List<Brand> read() throws SQLException{
        List<Brand> list = null;
        //Tạo connection để kết nối vào db
        Connection con = DBContext.getConnection();
        //Tạo đối tượng Statement để thực hiện lệnh SQL
        Statement stm = con.createStatement();
        //Thực hiện câu lệnh SQL
        ResultSet rs = stm.executeQuery("select * from Brand");
        //Đọc từng record và lưu List<Brand>
        list = new ArrayList<>();
        while(rs.next()){
            //Đọc một record và lưu vào đối tượng brand
            Brand brand = new Brand();
            brand.setId(rs.getString("id"));
            brand.setName(rs.getString("name"));
            //Thêm brand vào list
            list.add(brand);
        }        
        //Đóng kết nối từ ứng dụng vào db để giải phóng tài nguyên
        con.close();
        return list;
    }
}
