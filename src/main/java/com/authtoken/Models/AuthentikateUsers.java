package com.authtoken.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authentikateusers")
public class AuthentikateUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;
    @Column(name = "username")
    String userName;
    @Column(name = "useremail")
    String userEmail;
    @Column(name = "userpassword")
    String userPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authentikateusersroles", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
    List<AuthentikateRoles> userRoles;

    public List<AuthentikateRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<AuthentikateRoles> userRoles) {
        this.userRoles = userRoles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
