/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author PC
 */
public abstract class DBContext {
    private static final EntityManagerFactory entityManagerFactory = 
        Persistence.createEntityManagerFactory("nvh_persistence");

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    protected void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
