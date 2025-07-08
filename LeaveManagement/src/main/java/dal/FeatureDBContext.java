/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import model.Feature;

/**
 *
 * @author PC
 */
public class FeatureDBContext extends DBContext{
    
    /**
     * Lấy tất cả features
     */
    public ArrayList<Feature> getAllFeatures() {
        EntityManager em = getEntityManager();
        try {
            return (ArrayList<Feature>) em.createQuery("SELECT f FROM Feature f", Feature.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Thêm feature mới
     */
    public void addFeature(String featureName, String entryPoint) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            
            Feature feature = new Feature();
            feature.setFeatureName(featureName);
            feature.setEntryPoint(entryPoint);
            
            em.persist(feature);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    /**
     * Kiểm tra xem entry point có tồn tại không
     */
    public boolean isEntryPointExists(String entryPoint) {
        EntityManager em = getEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(f) FROM Feature f WHERE f.entryPoint = :entryPoint", Long.class)
                    .setParameter("entryPoint", entryPoint)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
    
    /**
     * Lấy feature theo entry point
     */
    public Feature getByEntryPoint(String entryPoint) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT f FROM Feature f WHERE f.entryPoint = :entryPoint", Feature.class)
                    .setParameter("entryPoint", entryPoint)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
