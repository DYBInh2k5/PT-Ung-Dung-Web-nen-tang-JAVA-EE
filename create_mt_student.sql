-- Script: Create the MT-Student database, create the Student table, insert sample data, and display all records.
-- Target platform: SQL Server
-- Note: The Vietnamese column name has been changed to the English column name: bonus.

IF DB_ID(N'MT-Student') IS NULL
BEGIN
    CREATE DATABASE [MT-Student];
    PRINT 'Database MT-Student created successfully.';
END
ELSE
BEGIN
    PRINT 'Database MT-Student already exists.';
END
GO

USE [MT-Student];
GO

-- Recreate the Student table to provide a clean sample dataset.
IF OBJECT_ID(N'dbo.Student', N'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.Student;
    PRINT 'Existing Student table dropped.';
END
GO

CREATE TABLE dbo.Student
(
    id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    dateofbirth DATE NOT NULL,
    address NVARCHAR(255) NOT NULL,
    email NVARCHAR(150) NOT NULL UNIQUE,
    bonus DECIMAL(12,2) NOT NULL CHECK (bonus >= 0)
);
GO

-- Insert 10 sample students.
INSERT INTO dbo.Student (name, dateofbirth, address, email, bonus)
VALUES
(N'Nguyen Van An', '2003-01-15', N'12 Nguyen Trai Street, District 1, Ho Chi Minh City', N'an.nguyen@example.com', 1500000),
(N'Tran Thi Binh', '2002-05-20', N'25 Le Loi Street, District 3, Ho Chi Minh City', N'binh.tran@example.com', 1800000),
(N'Le Minh Chau', '2003-09-08', N'45 Hai Ba Trung Street, Hanoi', N'chau.le@example.com', 1200000),
(N'Pham Quoc Dung', '2001-12-11', N'88 Tran Hung Dao Street, Da Nang', N'dung.pham@example.com', 900000),
(N'Hoang Thu Ha', '2002-03-29', N'19 Ly Thuong Kiet Street, Hue', N'ha.hoang@example.com', 2000000),
(N'Vo Thanh Khoa', '2003-07-14', N'70 Nguyen Hue Street, Can Tho', N'khoa.vo@example.com', 1100000),
(N'Dang My Linh', '2002-10-05', N'31 Phan Dinh Phung Street, Da Lat', N'linh.dang@example.com', 1750000),
(N'Bui Anh Minh', '2001-04-23', N'56 Cach Mang Thang Tam Street, Ho Chi Minh City', N'minh.bui@example.com', 800000),
(N'Do Gia Nhan', '2003-11-17', N'102 Vo Van Kiet Street, Nha Trang', N'nhan.do@example.com', 1600000),
(N'Phan Ngoc Oanh', '2002-08-02', N'9 Dien Bien Phu Street, Hai Phong', N'oanh.phan@example.com', 2200000);
GO

PRINT 'Database MT-Student is ready to use.';
SELECT * FROM dbo.Student;
GO
