/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
import java.io.Serializable;

public class UserRolePK implements Serializable {
    private User user;
    private Role role;

    // Getters and Setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRolePK that = (UserRolePK) o;
        return user.getUid() == that.user.getUid() && role.getRid() == that.role.getRid();
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.getUid() : 0;
        result = 31 * result + (role != null ? role.getRid() : 0);
        return result;
    }
}
