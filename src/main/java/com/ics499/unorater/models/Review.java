package com.ics499.unorater.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * Model for the Reviews entity.
 *
 * @author UNO TEAM
 */
@Entity
@JsonInclude
@Table(name="REVIEWS")
public class Review {

    @Id
    @GeneratedValue
    @Column(name="REVIEWID", columnDefinition = "INT")
    private int reviewID;

    @Column(name="USERID")
    private int userID;

    @Column(name="SERVICEID")
    private int serviceID;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="USERID", insertable = false, updatable = false)
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="SERVICEID", nullable=false, insertable = false, updatable = false)
    private Service service;

    @Transient
    private String serviceName;

    @Column(name="REVIEWTEXT")
    private String reviewText;

    @Column(name="DATECREATED")
    private Date dateCreated;

    @Column(name="DATEMODIFIED")
    private Date dateModified;

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    @JsonInclude
    public String getServiceName() {
        return service.getServiceName();
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "Review {" +
                "userID=" + userID +
                ", serviceID='" + serviceID + '\'' +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
