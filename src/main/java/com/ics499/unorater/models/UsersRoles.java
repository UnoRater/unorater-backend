package com.ics499.unorater.models;

import javax.persistence.*;

@Entity
@Table(name="USERSROLES")
public class UsersRoles {

    @Id
    @Column(name = "USERID")
    private int userID;

    @Column(name = "ROLEID")
    private int roleID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
