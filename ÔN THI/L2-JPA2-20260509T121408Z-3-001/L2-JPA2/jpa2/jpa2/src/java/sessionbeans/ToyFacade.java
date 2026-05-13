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
@Stateless
public class ToyFacade extends AbstractFacade<Toy> {

    @PersistenceContext(unitName = "jpa2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ToyFacade() {
        super(Toy.class);
    }
    
}
