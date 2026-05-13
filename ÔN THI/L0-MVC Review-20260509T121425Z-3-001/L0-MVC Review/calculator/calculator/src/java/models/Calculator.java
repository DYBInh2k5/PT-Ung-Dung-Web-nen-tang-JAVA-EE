/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author PHT
 * JavaBean
 */
/**
 * LỚP MODEL: Calculator
 * Đây là lớp JavaBean chứa dữ liệu và logic tính toán của ứng dụng.
 */
public class Calculator {
    // Khai báo các thuộc tính (fields) để lưu trữ 2 số và phép tính
    private double num1, num2;
    private String op;
    
    // Default constructor: Khởi tạo giá trị mặc định khi đối tượng được tạo mà không truyền tham số
    public Calculator() {
        this.num1 = this.num2 = 0;
        this.op = null;
    }
    
    // Constructor có tham số: Giúp khởi tạo nhanh đối tượng với dữ liệu từ Controller gửi sang
    public Calculator(double num1, double num2, String op) {
        this.num1 = num1;
        this.num2 = num2;
        this.op = op;
    }
    
    // CÁC PHƯƠNG THỨC GETTER/SETTER: Cho phép truy cập và thay đổi dữ liệu từ bên ngoài (Controller, JSP)
    public double getNum1() { return num1; }
    public void setNum1(double num1) { this.num1 = num1; }

    public double getNum2() { return num2; }
    public void setNum2(double num2) { this.num2 = num2; }

    public String getOp() { return op; }
    public void setOp(String op) { this.op = op; }
    
    /**
     * PHƯƠNG THỨC getResult():
     * Đây là nơi thực hiện logic tính toán chính.
     * JSP sẽ gọi ${model.result} để lấy giá trị này hiển thị ra màn hình.
     */
    public Object getResult() {
        switch(op){
            case "add": // Phép cộng
                return this.num1 + this.num2;
            case "sub": // Phép trừ
                return this.num1 - this.num2;
            case "mul": // Phép nhân
                return this.num1 * this.num2;
            case "div": // Phép chia
                if(this.num2 == 0){
                    return "Lỗi: Không thể chia cho 0"; // Kiểm tra chia cho 0
                } else {
                    return this.num1 / this.num2;
                } 
            default:
                return null;
        }
    }
}
