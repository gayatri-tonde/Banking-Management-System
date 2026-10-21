
package dao;

import entity.Customer;
import jakarta.persistence.*;

public class CustomerDao {

    private EntityManager em;

    public CustomerDao(EntityManager em){
        this.em=em;
    }
    public void save(Customer customer){

        EntityTransaction tx=em.getTransaction();

        tx.begin();
        em.persist(customer);
        tx.commit();

    }

    public Customer find(int id){

        return em.find(Customer.class,id);

    }

}
