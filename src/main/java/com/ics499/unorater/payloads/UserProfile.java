package com.ics499.unorater.payloads;

import com.ics499.unorater.models.Review;

import java.util.List;

/**
 * POJO representing a user profile.
 */
public class UserProfile {
    private Integer id;
    private String userName;
    private Integer numOfReviews;
    private List <Review> allApprovedReviews;

    public UserProfile(Integer id, String userName, int numOfReviews, List<Review> allReviews) {
        this.id = id;
        this.userName = userName;
        this.numOfReviews = numOfReviews;
        this.allApprovedReviews = allReviews;
    }


    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumOfReviews() {
        return numOfReviews;
    }

    public void setNumOfReviews(Integer numOfReviews) {
        this.numOfReviews = numOfReviews;
    }

    public List<Review> getAllApprovedReviews() {
        return allApprovedReviews;
    }

    public void setAllApprovedReviews(List<Review> allApprovedReviews) {
        this.allApprovedReviews = allApprovedReviews;
    }
}
