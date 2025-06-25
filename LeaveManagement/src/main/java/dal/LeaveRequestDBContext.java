/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import java.util.List;
import model.LeaveRequest;
import model.User;

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
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user.uid = :uid ORDER BY lr.createdAt DESC", LeaveRequest.class)
                    .setParameter("uid", uid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<LeaveRequest> findByManagerId(int managerId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user.manager.uid = :managerId AND lr.status = 'inprogress' ORDER BY lr.createdAt DESC", LeaveRequest.class)
                    .setParameter("managerId", managerId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void updateStatus(int lid, String status, User processedBy, String processedReason) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            LeaveRequest request = em.find(LeaveRequest.class, lid);
            if (request != null) {
                request.setStatus(status);
                request.setProcessedBy(processedBy);
                request.setProcessedReason(processedReason);
                em.merge(request);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<LeaveRequest> findApprovedForAgenda(int departmentId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user.department.did = :departmentId AND lr.status = 'approved' ORDER BY lr.fromDate", LeaveRequest.class)
                    .setParameter("departmentId", departmentId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public LeaveRequest findById(int lid) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LeaveRequest.class, lid);
        } finally {
            em.close();
        }
    }
    
    public void update(LeaveRequest request) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(request);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(int lid) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            LeaveRequest request = em.find(LeaveRequest.class, lid);
            if (request != null) {
                em.remove(request);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
