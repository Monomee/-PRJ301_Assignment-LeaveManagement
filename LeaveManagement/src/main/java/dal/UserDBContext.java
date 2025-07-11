/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import model.User;
import model.UserDTO;

/**
 *
 * @author PC
 */
public class UserDBContext extends DBContext{
    public User findByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public User findById(int uid) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, uid);
        } finally {
            em.close();
        }
    }
    public ArrayList<Integer> getAllSubordinateUids(int managerUid) {
        EntityManager em = getEntityManager();
        try {
            ArrayList<Integer> result = new ArrayList<>();
            findSubordinatesRecursive(managerUid, result, em);
            return result;
        } finally {
            em.close();
        }
    }

    private void findSubordinatesRecursive(int managerUid, ArrayList<Integer> result, EntityManager em) {
        ArrayList<Integer> subordinates = (ArrayList<Integer>) em.createQuery(
                "SELECT u.uid FROM User u WHERE u.manager.uid = :managerUid", Integer.class)
                .setParameter("managerUid", managerUid)
                .getResultList();

        for (Integer uid : subordinates) {
            result.add(uid);
            findSubordinatesRecursive(uid, result, em); // đệ quy
        }
    }

    public List<UserDTO> getAllUsersWithRoleAndDepartment() {
        EntityManager em = getEntityManager();
        try {
            List<Object[]> results = em.createQuery(
                "SELECT u.fullName, u.email, r.roleName, d.departmentName " +
                "FROM User u " +
                "JOIN UserRole ur ON ur.user.uid = u.uid " +
                "JOIN Role r ON ur.role.rid = r.rid " +
                "JOIN Department d ON u.department.did = d.did"
            ).getResultList();
            List<UserDTO> users = new ArrayList<>();
            for (Object[] row : results) {
                UserDTO dto = new UserDTO();
                dto.setFullName((String) row[0]);
                dto.setEmail((String) row[1]);
                dto.setRoleName((String) row[2]);
                dto.setDepartmentName((String) row[3]);
                users.add(dto);
            }
            return users;
        } finally {
            em.close();
        }
    }
}
