/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Toy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PHT
 */
/**
 * LỚP TOY FACADE:
 * Đây là Session Bean thực hiện các thao tác dữ liệu cho Entity Toy.
 * Nó kế thừa các hàm CRUD từ AbstractFacade.
 */
@Stateless // Đánh dấu đây là một Stateless Session Bean (EJB)
public class ToyFacade extends AbstractFacade<Toy> {

    /**
     * @PersistenceContext: Tiêm (Inject) EntityManager vào để làm việc với DB.
     * unitName: Phải trùng với tên khai báo trong file persistence.xml
     */
    @PersistenceContext(unitName = "jpa2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ToyFacade() {
        super(Toy.class); // Truyền class Toy vào lớp cha để biết bảng nào cần xử lý
    }
    
}
