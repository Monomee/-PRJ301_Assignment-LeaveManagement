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

public class RoleFeaturePK implements Serializable {
    private Role role;
    private Feature feature;

    // Getters and Setters
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Feature getFeature() { return feature; }
    public void setFeature(Feature feature) { this.feature = feature; }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleFeaturePK that = (RoleFeaturePK) o;
        return (role != null && that.role != null && role.getRid() == that.role.getRid()) &&
               (feature != null && that.feature != null && feature.getFid() == that.feature.getFid());
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.getRid() : 0;
        result = 31 * result + (feature != null ? feature.getFid() : 0);
        return result;
    }
}
