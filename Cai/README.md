# HƯỚNG DẪN KẾT NỐI SQL SERVER VỚI NETBEANS CHI TIẾT TỪ A-Z

Microsoft cũng ghi rõ: muốn client app kết nối ổn định thì thường phải
bật TCP/IP protocol trong SQL Server Configuration Manager và restart
service sau khi bật.

Tài liệu này hướng dẫn đúng kiểu phòng máy, từng cú click chuột, kể cả
đoạn Computer Management.

------------------------------------------------------------------------

## PHẦN 1 --- KIỂM TRA SQL SERVER ĐÃ CÀI ĐÚNG CHƯA

Cần có:

-   SQL Server (2012/2014/2019 đều được)
-   SQL Server Management Studio (SSMS)
-   NetBeans 8.2
-   JDK 8

------------------------------------------------------------------------

## PHẦN 2 --- MỞ COMPUTER MANAGEMENT NHƯ THẦY MỞ

### Bước 1:

Nhấn:

`Windows + R`

gõ:

`compmgmt.msc`

Enter.

Nó mở:

**Computer Management**

Bên trái chọn:

`Services and Applications`

rồi chọn:

`Services`

------------------------------------------------------------------------

### Bước 2: tìm service SQL

Kéo xuống tìm:

`SQL Server (MSSQLSERVER)`

hoặc

`SQL Server (SQLEXPRESS)`

tùy bản cài.

Phải thấy:

`Status = Running`

Nếu đang Stop:

Chuột phải → **Start**

------------------------------------------------------------------------

### Bước 3: tìm thêm SQL Server Browser

Tìm:

`SQL Server Browser`

Chuột phải → **Start**

Cái này giúp app khác tìm thấy instance SQL.

------------------------------------------------------------------------

## PHẦN 3 --- BẬT TÀI KHOẢN sa VÀ SQL AUTHENTICATION

Mở:

**SQL Server Management Studio (SSMS)**

Đăng nhập bằng:

`Windows Authentication`

------------------------------------------------------------------------

Sau khi vào:

Bên Object Explorer:

Chuột phải tên server trên cùng → **Properties**

Chọn tab:

`Security`

Tick:

`SQL Server and Windows Authentication mode`

Đây gọi là **Mixed Mode**.

Bấm OK.

------------------------------------------------------------------------

### Restart SQL Server

Chuột phải server → **Restart**

------------------------------------------------------------------------

## PHẦN 4 --- BẬT USER sa VÀ ĐẶT PASSWORD

Trong SSMS mở:

`Security -> Logins`

Thấy user:

`sa`

Chuột phải `sa` → **Properties**

------------------------------------------------------------------------

### Tab General:

Đặt password:

`1`

hoặc password dễ nhớ.

Bỏ tick enforce password policy nếu cần.

------------------------------------------------------------------------

### Tab Status:

Chọn:

-   Login: `Enabled`
-   Permission to connect: `Grant`

Bấm OK.

------------------------------------------------------------------------

## PHẦN 5 --- MỞ SQL SERVER CONFIGURATION MANAGER

Windows + R gõ:

### nếu SQL 2019:

`SQLServerManager15.msc`

### nếu SQL 2017:

`SQLServerManager14.msc`

### nếu SQL 2016:

`SQLServerManager13.msc`

Hoặc search Start Menu:

`SQL Server Configuration Manager`

------------------------------------------------------------------------

Trong đó vào:

`SQL Server Network Configuration -> Protocols for MSSQLSERVER`

hoặc:

`Protocols for SQLEXPRESS`

Bên phải sẽ thấy:

-   Shared Memory
-   Named Pipes
-   TCP/IP

------------------------------------------------------------------------

### Chuột phải TCP/IP → Enable

Bắt buộc bật.

------------------------------------------------------------------------

### Chuột phải TCP/IP → Properties

Qua tab:

`IP Addresses`

Kéo xuống cuối `IPAll`

Xem:

`TCP Port = 1433`

nếu trống thì điền `1433`

`TCP Dynamic Ports` xóa trống.

Apply.

------------------------------------------------------------------------

### Sau đó vào:

`SQL Server Services`

Chuột phải:

`SQL Server (MSSQLSERVER)`

→ Restart.

------------------------------------------------------------------------

## PHẦN 6 --- TEST SQL SERVER BẰNG SSMS TRƯỚC

Thoát SSMS.

Mở lại.

Login bằng:

`SQL Server Authentication`

User:

`sa`

Password:

`1`

Server name:

`localhost`

hoặc

`localhost,1433`

Nếu vào được:

=\> SQL đã ổn.

------------------------------------------------------------------------

## PHẦN 7 --- TẠO DATABASE CHO NETBEANS

Trong SSMS bấm New Query:

``` sql
create database HBDemo
go
```

Execute.

Sau đó:

``` sql
use HBDemo
go

create table Student(
   id int identity(1,1) primary key,
   name nvarchar(100),
   age int
)
```

------------------------------------------------------------------------

## PHẦN 8 --- ADD JDBC DRIVER VÀO NETBEANS

Mở NetBeans.

Tạo Java Project hoặc Web Project.

Bên trái project:

`Libraries`

Chuột phải:

`Add JAR/Folder`

Thêm file:

`sqljdbc4.jar`

hoặc

`sqljdbc42.jar`

------------------------------------------------------------------------

## PHẦN 9 --- TEST KẾT NỐI JDBC THUẦN TRƯỚC

Tạo class `TestConnect.java`

``` java
import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnect {
    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=HBDemo";
            String user = "sa";
            String pass = "1";

            Connection con = DriverManager.getConnection(url, user, pass);

            System.out.println("KET NOI THANH CONG");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Run.

Nếu hiện:

`KET NOI THANH CONG`

thì NetBeans đã kết nối thành công.

------------------------------------------------------------------------

## PHẦN 10 --- SAU NÀY HIBERNATE/JPA CHỈ DÙNG LẠI CHUỖI NÀY

``` text
jdbc:sqlserver://localhost:1433;databaseName=HBDemo
```

User:

`sa`

Pass:

`1`

------------------------------------------------------------------------

## TỔNG KẾT NHỮNG THỨ THẦY MỞ LÀ ĐỂ LÀM GÌ

  Công cụ                            Mục đích
  ---------------------------------- --------------------------------
  Computer Management                kiểm tra service SQL đang chạy
  SSMS                               bật mixed mode + sa + tạo DB
  SQL Server Configuration Manager   bật TCP/IP port 1433
  NetBeans                           add JDBC và test code

------------------------------------------------------------------------

Thiếu 1 trong 4 cái là rất dễ connect fail.
