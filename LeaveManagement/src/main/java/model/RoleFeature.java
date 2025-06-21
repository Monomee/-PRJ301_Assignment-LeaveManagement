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
@Table(name = "Role_features")
@IdClass(RoleFeaturePK.class)
public class RoleFeature {
    @Id
    @ManyToOne
    @JoinColumn(name = "rid", nullable = false)
    private Role role;

    @Id
    @ManyToOne
    @JoinColumn(name = "fid", nullable = false)
    private Feature feature;

    // Getters and Setters
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Feature getFeature() { return feature; }
    public void setFeature(Feature feature) { this.feature = feature; }
}
