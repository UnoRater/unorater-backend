package com.ics499.unorater.models;

import javax.persistence.*;
import java.util.List;

/**
 * Model for the Departments entity.
 *
 * @author UNO TEAM
 */
@Entity
@Table (name="DEPARTMENTS")
public class Department {
    @Id
    @GeneratedValue
    @Column(name = "DEPARTMENTID", columnDefinition = "INT")
    private int departmentID;

    @Column(name="DEPARTMENTNAME")
    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<Service> services;

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
