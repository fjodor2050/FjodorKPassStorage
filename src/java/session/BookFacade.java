/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "SPTV17WebLibrary2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

   
    public List<Book> findNewBooks() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-2);
        try {
            return em.createQuery("SELECT b FROM Book b WHERE b.date > :date")
                    .setParameter("date", c.getTime())
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
