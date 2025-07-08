/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import model.Feature;
import model.Role;

/**
 *
 * @author PC
 */
public class RoleFeatureDBContext extends DBContext{
     public List<Role> getRolesByUserId(int uid) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT ur.role FROM UserRole ur WHERE ur.user.uid = :uid", Role.class)
                    .setParameter("uid", uid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Feature> getFeaturesByRoleId(int rid) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT rf.feature FROM RoleFeature rf WHERE rf.role.rid = :rid", Feature.class)
                    .setParameter("rid", rid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean hasAccess(int uid, String entryPoint) {
        EntityManager em = getEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(rf) FROM RoleFeature rf " +
                    "JOIN UserRole ur ON rf.role.rid = ur.role.rid " +
                    "WHERE ur.user.uid = :uid AND rf.feature.entryPoint = :entryPoint", Long.class)
                    .setParameter("uid", uid)
                    .setParameter("entryPoint", entryPoint)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public List<Feature> getFeaturesByUserId(int uid) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT rf.feature FROM RoleFeature rf " +
                    "JOIN UserRole ur ON rf.role.rid = ur.role.rid " +
                    "WHERE ur.user.uid = :uid", Feature.class)
                    .setParameter("uid", uid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Kiểm tra xem path có tồn tại trong database không
     * @param path đường dẫn cần kiểm tra
     * @return true nếu path tồn tại, false nếu không
     */
    public boolean isPathExists(String path) {
        EntityManager em = getEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(f) FROM Feature f WHERE f.entryPoint = :path", Long.class)
                    .setParameter("path", path)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    /**
     * Lấy tất cả các entry point từ database
     * @return danh sách các entry point
     */
    public ArrayList<String> getAllEntryPoints() {
        EntityManager em = getEntityManager();
        try {
            return (ArrayList<String>) em.createQuery("SELECT f.entryPoint FROM Feature f", String.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
