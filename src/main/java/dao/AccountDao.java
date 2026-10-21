package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import entity.Account;
import entity.Transaction;

public class AccountDao
{
    private EntityManager em;
    public  AccountDao(EntityManager em)
    {
        this.em=em;
    }
    public  void  deposit(int accountId,double amount)
    {
        EntityTransaction tx=em.getTransaction();
        tx.begin();

        Account account=em.find(Account.class,accountId);

        account.setBalance(account.getBalance()+amount);

        Transaction t=new Transaction("Deposit",amount);
        t.setAccount(account);

        em.persist(t);

        tx.commit();

    }

    public void withdraw(int accountId,double amount){

        EntityTransaction tx=em.getTransaction();
        tx.begin();

        Account account=em.find(Account.class,accountId);

        if(account.getBalance()>=amount){

            account.setBalance(account.getBalance()-amount);

            Transaction t=new Transaction("Withdraw",amount);
            t.setAccount(account);

            em.persist(t);

        }

        tx.commit();


    }
}
