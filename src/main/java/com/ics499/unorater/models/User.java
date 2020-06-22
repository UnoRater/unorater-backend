package com.ics499.unorater.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Model for the Users entity.
 *
 * @author UNO-TEAM
 */
@Entity

@Table(name="USERS", catalog="UNODATABASE", schema="DBO", uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL")
)
public class User {

    @Id
    @GeneratedValue
    @Column(name="USERID", columnDefinition = "BIGINT")
    private int userID;

    @NotBlank
    @Size(max = 40)
    @Column(name="USERNAME")
    private String userName;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    @Column(name="EMAIL")
    private String email;

    @JsonIgnore
    @NotBlank
    @Size(max = 100)
    @Column(name="PASSWORD")
    private String password;

    @OneToMany(mappedBy="user")
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USERSROLES",
            joinColumns = @JoinColumn(
                    name = "USERID", referencedColumnName = "USERID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLEID", referencedColumnName = "ROLEID"))
    private Collection<Role> roles;

    @Column(name = "DATECREATED")
    private Date dateCreated;


    @Column(name = "DEPARTMENTNO")
    private int departmentNum;

    public User() {}

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email, String password, Collection <Role> roles) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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


    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getDepartmentNum() {
        return departmentNum;
    }

    public void setDepartmentNum(int departmentNum) {
        this.departmentNum = departmentNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userID +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + "*********" + '\'' +
                ", roles=" + roles +
                '}';
    }
}
