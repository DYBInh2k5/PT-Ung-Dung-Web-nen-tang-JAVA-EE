Microsoft cũng ghi rõ: muốn client app kết nối ổn định thì thường phải bật TCP/IP protocol trong SQL Server Configuration Manager và restart service sau khi bật.

Tao sẽ chỉ mày từ A tới Z đúng kiểu phòng máy, từng cú click chuột, kể cả đoạn Computer Management.

PHẦN 1 — KIỂM TRA SQL SERVER ĐÃ CÀI ĐÚNG CHƯA

Mày cần có:

SQL Server (2012/2014/2019 đều được)
SQL Server Management Studio (SSMS)
NetBeans 8.2
JDK 8
PHẦN 2 — MỞ COMPUTER MANAGEMENT NHƯ THẦY MỞ
Bước 1:

Nhấn:

Windows + R

gõ:

compmgmt.msc

Enter.

Nó mở:

Computer Management

Bên trái chọn:

Services and Applications

rồi chọn:

Services
Bước 2: tìm service SQL

Kéo xuống tìm:

SQL Server (MSSQLSERVER)

hoặc

SQL Server (SQLEXPRESS)

tùy bản cài.

Phải thấy status = Running

Nếu nó đang Stop:

chuột phải → Start.

Bước 3: tìm thêm SQL Server Browser

Tìm:

SQL Server Browser

chuột phải → Start luôn.

Cái này giúp máy khác hoặc app khác tìm thấy instance SQL.

PHẦN 3 — BẬT TÀI KHOẢN sa VÀ SQL AUTHENTICATION

Đây là chỗ nhiều đứa quên.

Mở:

SQL Server Management Studio (SSMS)

đăng nhập bằng:

Windows Authentication

vì lúc đầu chưa chắc sa login được.

Sau khi vào:

Bên Object Explorer:

chuột phải tên server trên cùng → Properties.

Chọn tab:

Security

Tick:

SQL Server and Windows Authentication mode

Đây gọi là Mixed Mode.

Nếu không bật cái này:

NetBeans dùng user sa sẽ không login được.

Cộng đồng SQL cũng gặp lỗi này suốt: SQL authentication fail nếu server chỉ để Windows auth.

Bấm OK.

Restart SQL Server

Chuột phải server → Restart.

PHẦN 4 — BẬT USER sa VÀ ĐẶT PASSWORD

Trong SSMS:

mở:

Security
   → Logins

thấy user:

sa

chuột phải sa → Properties.

Tab General:

đặt password:

1

hoặc gì dễ nhớ.

bỏ tick enforce password policy nếu cần.

Tab Status:

chọn:

Login: Enabled
Permission to connect: Grant

OK.

PHẦN 5 — MỞ SQL SERVER CONFIGURATION MANAGER (CỰC QUAN TRỌNG)

Đây là chỗ thầy m chắc chắn có mở.

Windows + R gõ một trong các lệnh:

nếu SQL 2019:

SQLServerManager15.msc

nếu SQL 2017:

SQLServerManager14.msc

nếu SQL 2016:

SQLServerManager13.msc

Microsoft có đúng danh sách file .msc theo version như vậy.

Nếu không biết version cứ search Start Menu:

SQL Server Configuration Manager
Trong đó vào:
SQL Server Network Configuration
   → Protocols for MSSQLSERVER

hoặc Protocols for SQLEXPRESS.

Bên phải sẽ thấy:

Shared Memory
Named Pipes
TCP/IP
Chuột phải TCP/IP → Enable

Bắt buộc bật.

Vì SQL client app như NetBeans JDBC thường dùng TCP/IP để kết nối. Sau khi bật phải restart service thì mới ăn.

Chuột phải TCP/IP → Properties

qua tab:

IP Addresses

kéo xuống dưới cùng IPAll

xem:

TCP Port = 1433

nếu trống thì điền 1433.

TCP Dynamic Ports xóa trống.

Apply.

Sau đó vào:
SQL Server Services

chuột phải:

SQL Server (MSSQLSERVER)

→ Restart.

PHẦN 6 — TEST SQL SERVER BẰNG SSMS TRƯỚC

Thoát SSMS.

Mở lại.

Login bằng:

SQL Server Authentication

User:

sa

Password:

1

Server name:

localhost

hoặc

localhost,1433

Nếu vào được:

=> SQL đã ổn.

PHẦN 7 — TẠO DATABASE CHO NETBEANS

Trong SSMS bấm New Query:

create database HBDemo
go

Execute.

Sau đó:

use HBDemo
go

create table Student(
   id int identity(1,1) primary key,
   name nvarchar(100),
   age int
)
PHẦN 8 — ADD JDBC DRIVER VÀO NETBEANS

Mở NetBeans.

Tạo Java Project hoặc Web Project.

Bên trái project:

Libraries

chuột phải:

Add JAR/Folder

thêm file:

sqljdbc4.jar

hoặc sqljdbc42.jar.

(Nếu chưa có tao gửi link tải sau.)

PHẦN 9 — TEST KẾT NỐI JDBC THUẦN TRƯỚC

Tạo class TestConnect.java

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

Run.

Nếu hiện:

KET NOI THANH CONG

thì NetBeans đã bắt tay được với SQL.

PHẦN 10 — SAU NÀY HIBERNATE/JPA CHỈ DÙNG LẠI CHUỖI NÀY
jdbc:sqlserver://localhost:1433;databaseName=HBDemo

User:

sa

Pass:

1
TỔNG KẾT NHỮNG THỨ THẦY MỞ LÀ ĐỂ LÀM GÌ
Công cụ	Mục đích
Computer Management	kiểm tra service SQL đang chạy
SSMS	bật mixed mode + sa + tạo DB
SQL Server Configuration Manager	bật TCP/IP port 1433
NetBeans	add JDBC và test code

Thiếu 1 trong 4 cái là dễ connect fail.
