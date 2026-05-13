/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PHT
 */
@Stateless
public class StudentFacade extends AbstractFacade<Student> {

    @PersistenceContext(unitName = "jpa-paginationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }

    public List<Student> findAll(int page, int pageSize) {
        em = getEntityManager();

        //JPQL
        List<Student> list = em.createQuery("SELECT s FROM Student s", Student.class)
                .setFirstResult((page - 1) * pageSize) // offset
                .setMaxResults(pageSize) // limit
                .getResultList();

        return list;
    }

}
