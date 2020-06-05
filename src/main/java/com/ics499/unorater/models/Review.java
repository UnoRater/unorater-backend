package com.ics499.unorater.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Model for the Reviews entity.
 *
 * @author UNO TEAM
 */
@Entity
@Table(name="REVIEWS")
public class Review {

    @Id
    @GeneratedValue
    @Column(name="reviewID", columnDefinition = "INT")
    private int reviewID;

    @Column(name="USERNAME")
    private String userName;

    @Column(name="EMAIL")
    private String email;

    @ManyToOne
    @JoinColumn(name="USERID", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="SERVICEID", nullable=false)
    private Service service;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String isReviewText() {
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
}
