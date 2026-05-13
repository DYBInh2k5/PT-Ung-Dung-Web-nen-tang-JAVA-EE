package service;

import model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

/**
 * LỚP DỊCH VỤ: StudentService
 * Chứa logic xử lý nghiệp vụ và thao tác CRUD bằng Hibernate API.
 */
public class StudentService {

    /**
     * LẤY DANH SÁCH:
     * LUỒNG ĐI: Mở Session -> Truy vấn HQL -> Trả về List -> Đóng Session.
     */
    public List<Student> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // HQL: "from Student" tương đương "SELECT * FROM Student" trong SQL
        List<Student> list = session.createQuery("from Student").list();
        session.close();
        return list;
    }

    /**
     * THÊM MỚI:
     * LUỒNG ĐI: Mở Session -> Bắt đầu Transaction -> Lưu đối tượng -> Commit -> Đóng Session.
     */
    public void add(String name, int age) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Student s = new Student();
        s.setName(name);
        s.setAge(age);

        session.save(s); // Lưu đối tượng vào DB
        tx.commit(); // Xác nhận lưu vĩnh viễn
        session.close();
    }

    /**
     * XÓA:
     * LUỒNG ĐI: Mở Session -> Bắt đầu Transaction -> Tìm đối tượng theo ID -> Xóa -> Commit.
     */
    public void delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // Tìm sinh viên có id tương ứng trước khi xóa
        Student s = (Student)session.get(Student.class, id);
        if (s != null) session.delete(s);

        tx.commit();
        session.close();
    }

    /**
     * CẬP NHẬT:
     * LUỒNG ĐI: Tìm đối tượng -> Thay đổi thuộc tính -> Gọi update() -> Commit.
     */
    public void update(int id, String name, int age) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Student s = (Student)session.get(Student.class, id);
        if (s != null) {
            s.setName(name);
            s.setAge(age);
            session.update(s); // Đồng bộ thay đổi xuống DB
        }

        tx.commit();
        session.close();
    }
}
