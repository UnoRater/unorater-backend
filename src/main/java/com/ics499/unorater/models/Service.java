package com.ics499.unorater.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Model for the Services entity.
 *
 * @author UNO TEAM
 */
@Entity
@Table(name="SERVICES")
public class Service {

    @Id
    @Column(name = "SERVICEID", columnDefinition = "BIGINT")
    private int serviceID;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENTID", nullable = false, insertable=false, updatable=false)
    private Department department;

    @Column(name = "SERVICENAME")
    private String serviceName;

    @OneToMany(mappedBy="service")
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "SERVICEID", nullable = false, insertable=false, updatable=false)
    private Service service;


    @Column(name = "DATECREATED")
    private Date dateCreated;

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
