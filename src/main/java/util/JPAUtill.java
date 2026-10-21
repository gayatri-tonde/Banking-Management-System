package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JPAUtill
{
    private static final EntityManagerFactory emf =
            createEntityManagerFactory();

    private static EntityManagerFactory createEntityManagerFactory() {
        String password = System.getenv("BANK_DB_PASSWORD");
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("Set the BANK_DB_PASSWORD environment variable.");
        }

        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.username",
                System.getenv().getOrDefault("BANK_DB_USERNAME", "root"));
        properties.put("jakarta.persistence.jdbc.password", password);
        return Persistence.createEntityManagerFactory("bank", properties);
    }

    public static EntityManager getEntityManager(){

        return emf.createEntityManager();

    }

    public static void close(){

        emf.close();

    }
}
