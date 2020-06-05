package com.ics499.unorater.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Model for the Users entity.
 *
 * @author UNO TEAM
 */
@Entity
@Table(name="USERS", uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL")
)
public class User {

    @Id
    @GeneratedValue
    @Column(name="USERID", columnDefinition = "BIGINT")
    private int userID;

    @Column(name="USERNAME")
    private String userName;

    @Column(name="EMAIL")
    private String email;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="ISADMIN", columnDefinition = "BIT")
    private boolean isAdmin;

    @OneToMany(mappedBy="user")
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USERROLES",
            joinColumns = @JoinColumn(
                    name = "USERID", referencedColumnName = "USERID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLEID", referencedColumnName = "ROLEID"))
    private Collection<Role> roles;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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
