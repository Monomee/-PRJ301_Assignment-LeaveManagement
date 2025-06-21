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
@Table(name = "Features")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fid;

    @Column(name = "feature_name", nullable = false)
    private String featureName;

    @Column(name = "EntryPoint", nullable = false)
    private String entryPoint;

    // Getters and Setters
    public int getFid() { return fid; }
    public void setFid(int fid) { this.fid = fid; }
    public String getFeatureName() { return featureName; }
    public void setFeatureName(String featureName) { this.featureName = featureName; }
    public String getEntryPoint() { return entryPoint; }
    public void setEntryPoint(String entryPoint) { this.entryPoint = entryPoint; }
}
