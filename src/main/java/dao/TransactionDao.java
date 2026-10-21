package dao;

import entity.Transaction;
import jakarta.persistence.*;
import java.util.List;

public class TransactionDao {

    private EntityManager em;

    public TransactionDao(EntityManager em){
        this.em=em;
    }

    public List<Transaction> getTransactions(int accountId){

        TypedQuery<Transaction> query=em.createQuery(
                "FROM Transaction t WHERE t.account.accountId=:id",
                Transaction.class);

        query.setParameter("id",accountId);

        return query.getResultList();

    }

}