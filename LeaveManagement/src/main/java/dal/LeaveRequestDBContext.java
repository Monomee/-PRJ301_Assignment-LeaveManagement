/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import java.util.List;
import model.LeaveRequest;

/**
 *
 * @author PC
 */
public class LeaveRequestDBContext extends DBContext{
    public void create(LeaveRequest request) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(request);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<LeaveRequest> findByUserId(int uid) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user.uid = :uid", LeaveRequest.class)
                    .setParameter("uid", uid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<LeaveRequest> findByManagerId(int managerId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user.manager.uid = :managerId", LeaveRequest.class)
                    .setParameter("managerId", managerId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
