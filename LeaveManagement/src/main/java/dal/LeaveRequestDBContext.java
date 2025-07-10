/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LeaveRequest;
import model.User;

/**
 *
 * @author PC
 */
public class LeaveRequestDBContext extends DBContext {

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

    public List<LeaveRequest> findForAgenda(int departmentId, LocalDate startDate, LocalDate endDate) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user.department.did = :departmentId AND lr.status IN ('approved', 'rejected') AND lr.fromDate <= :endDate AND lr.toDate >= :startDate ORDER BY lr.fromDate", LeaveRequest.class)
                    .setParameter("departmentId", departmentId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<LeaveRequest> findForAgenda(int departmentId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user.department.did = :departmentId AND lr.status IN ('approved', 'rejected') ORDER BY lr.fromDate", LeaveRequest.class)
                    .setParameter("departmentId", departmentId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<LeaveRequest> findForAgendaWithEmployeeFilter(int departmentId, LocalDate startDate, LocalDate endDate, String employeeFilter) {
        EntityManager em = getEntityManager();
        try {
            String query = "SELECT lr FROM LeaveRequest lr WHERE lr.user.department.did = :departmentId AND lr.status IN ('approved', 'rejected') AND lr.fromDate <= :endDate AND lr.toDate >= :startDate";
            if (employeeFilter != null && !employeeFilter.trim().isEmpty()) {
                query += " AND lr.user.fullName LIKE :employeeFilter";
            }
            query += " ORDER BY lr.fromDate";
            var emQuery = em.createQuery(query, LeaveRequest.class)
                           .setParameter("departmentId", departmentId)
                           .setParameter("startDate", startDate)
                           .setParameter("endDate", endDate);
            if (employeeFilter != null && !employeeFilter.trim().isEmpty()) {
                emQuery.setParameter("employeeFilter", "%" + employeeFilter + "%");
            }
            return emQuery.getResultList();
        } finally {
            em.close();
        }
    }

    public List<User> findUsersByDepartment(int departmentId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.department.did = :departmentId ORDER BY u.fullName", User.class)
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

    public List<LeaveRequest> getLeaveRequestsBySubordinates(int managerUid) {
        List<Integer> subordinateUids = new UserDBContext().getAllSubordinateUids(managerUid);
        if (subordinateUids.isEmpty()) {
            return Collections.emptyList();
        }

        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.user.uid IN :uids", LeaveRequest.class)
                    .setParameter("uids", subordinateUids)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
