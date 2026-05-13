/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author PHT
 */
/**
 * LỚP TRỪU TƯỢNG (ABSTRACT FACADE):
 * Đây là lớp dùng chung cho tất cả các Entity. 
 * Nó chứa các phương thức CRUD cơ bản giúp tái sử dụng code.
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // Phương thức trừu tượng để lấy EntityManager từ lớp con
    protected abstract EntityManager getEntityManager();

    // THÊM MỚI (CREATE): Tương đương lệnh INSERT trong SQL
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    // CẬP NHẬT (EDIT): Tương đương lệnh UPDATE trong SQL
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    // XÓA (REMOVE): Tương đương lệnh DELETE trong SQL
    public void remove(T entity) {
        // Phải merge trước khi remove để đảm bảo đối tượng đang được EntityManager quản lý
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    // TÌM THEO ID (FIND): Tìm 1 bản ghi dựa trên khóa chính
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    // LẤY TẤT CẢ (FIND ALL): Lấy toàn bộ danh sách bản ghi trong bảng
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    // ... các phương thức bổ trợ khác (đếm, phân trang) ...
}
