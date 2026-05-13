/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Student;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author PHT
 */
/**
 * LỚP STUDENT FACADE:
 * Chứa các phương thức truy vấn dữ liệu nâng cao (JPQL, Native Query) và quản lý giao dịch.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN) // Tự quản lý giao dịch (Bean Managed Transaction)
public class StudentFacade extends AbstractFacade<Student> {

    @PersistenceContext(unitName = "jpa-queriesPU")
    private EntityManager em;

    @Resource
    private UserTransaction utx; // Đối tượng dùng để điều khiển Transaction thủ công

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }

    /**
     * TRUY VẤN JPQL: Tìm theo tên
     * Dùng tham số đặt tên ':fn' để bảo mật và linh hoạt.
     */
    public List<Student> findByFirstName(String firstName) {
        return em.createQuery("SELECT s FROM Student s WHERE s.firstName=:fn", Student.class)
                .setParameter("fn", firstName)
                .getResultList();
    }

    /**
     * TRUY VẤN JPQL: Tìm kiếm gần đúng (LIKE)
     */
    public List<Student> findByEmail(String email) {
        return em.createQuery("SELECT s FROM Student s WHERE s.email LIKE :email", Student.class)
                .setParameter("email", String.format("%%%s%%", email)) // Tạo chuỗi %keyword%
                .getResultList();
    }

    /**
     * TRUY VẤN JPQL: Sắp xếp kết quả (ORDER BY)
     */
    public List<Student> orderByFirstName() {
        return em.createQuery("SELECT s FROM Student s ORDER BY s.firstName ASC", Student.class)
                .getResultList();
    }

    /**
     * NATIVE QUERY: Truy vấn SQL thuần túy
     * Dùng khi cần tận dụng các tính năng riêng của SQL Server.
     */
    public List<Student> useNativeQuery() {
        return em.createNativeQuery("SELECT * FROM Student WHERE id < 10", Student.class)
                .getResultList();
    }

    /**
     * QUẢN LÝ TRANSACTION: Cập nhật nhiều bản ghi đồng thời
     * LUỒNG ĐI: begin() -> thực hiện các thay đổi -> commit() để lưu tất cả.
     */
    public void mangeTransaction() {
        try {
            utx.begin(); // Bắt đầu giao dịch
            
            Student s1 = this.find(1);
            Student s5 = this.find(5);

            s1.setFirstName("Nam"); // Thay đổi dữ liệu trong bộ nhớ
            s5.setFirstName("Hai");

            utx.commit(); // Lưu tất cả thay đổi xuống DB
        } catch (Exception e) {
            try {
                utx.rollback(); // Nếu có lỗi thì hủy bỏ toàn bộ thay đổi
            } catch (Exception ex) {}
        }
    }
}
