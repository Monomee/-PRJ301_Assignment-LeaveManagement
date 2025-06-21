/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
import jakarta.persistence.*;

@Entity
@Table(name = "User_roles")
@IdClass(UserRolePK.class)
public class UserRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "rid", nullable = false)
    private Role role;

    // Getters and Setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
