package com.ics499.unorater.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Review {

    @Id
    @GeneratedValue
    @Column(name="USERID", columnDefinition = "INT")
    private int userID;

    @Column(name="USERNAME")
    private String userName;

    @Column(name="EMAIL")
    private String email;

    @Column(name="ISADMIN", columnDefinition = "BIT")
    private boolean isAdmin;

}
