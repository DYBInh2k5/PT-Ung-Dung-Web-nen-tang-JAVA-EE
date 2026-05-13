/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hbex;

import java.util.List;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author PHT
 */
public class HBEx {

    private static SessionFactory factory;
    
    public static void main(String[] args) {
        factory = HibernateUtil.getSessionFactory();
        create();
        getAll();
        factory.close();
    }

    public static void getAll() {
        Session session = factory.openSession();

        List<Student> list = session.createQuery("from Student").list();

        for (Student s : list) {
            System.out.println(s.getId() + " - " + s.getName());
        }

        session.close();

    }

    public static void create() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Student s = new Student("Nguyen Van A", 20);
        session.save(s);

        tx.commit();
        session.close();
    }

}
