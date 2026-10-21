package org;

import dao.CustomerDao;
import dao.AccountDao;
import entity.*;
import jakarta.persistence.EntityManager;
import util.JPAUtill;

public class main {

    public static void main(String[] args) {

        EntityManager em = JPAUtill.getEntityManager();

        CustomerDao customerDao = new CustomerDao(em);
        AccountDao accountDao = new AccountDao(em);

        Customer customer =
                new Customer("John","john@gmail.com","9876543210");

        Account account =
                new Account("ACC1001","Savings",10000);

        account.setCustomer(customer);

        customer.getAccounts().add(account);

        customerDao.save(customer);

        accountDao.deposit(1,5000);

        accountDao.withdraw(1,2000);

        em.close();
        JPAUtill.close();

    }
}