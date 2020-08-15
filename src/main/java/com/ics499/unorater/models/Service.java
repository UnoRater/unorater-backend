package com.ics499.unorater.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Model for the Services entity.
 *
 * @author UNO TEAM
 */
@Entity
@Table(name="SERVICES")
public class Service {

    @Id
    @GeneratedValue
    @Column(name = "SERVICEID", columnDefinition = "BIGINT")
    private int serviceID;

    @Column(name = "DEPARTMENTID")
    private int departmentID;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "DEPARTMENTID", nullable = false, insertable=false, updatable=false)
    private Department department;

    @Column(name = "SERVICENAME")
    private String serviceName;

    @Column(name = "SERVICEDESCRIPTION")
    private String serviceDescription;

    @OneToMany(mappedBy="service")
    private Set<Review> reviews;

    @Transient
    private Double aggregateScore;

    @Column(name = "DATECREATED")
    private Date dateCreated;

    @Column(name = "PUBLICSERVICE")
    private boolean publicService;

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

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    @JsonInclude
    public double getAggregateScore() {
        double totalScore = 0;
        double overallScore = 0;
        for (Review review : reviews) {
            totalScore += Double.parseDouble(review.getScore());
            overallScore += 5;
        }

        aggregateScore = (totalScore / overallScore) * 5;

        DecimalFormat formatter = new DecimalFormat("#0.0");

        return aggregateScore.isNaN() ? 0 : Double.parseDouble(formatter.format(aggregateScore));
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public boolean isPublicService() {
        return publicService;
    }

    public void setPublicService(boolean publicService) {
        this.publicService = publicService;
    }
}
