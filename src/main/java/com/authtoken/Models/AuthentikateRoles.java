package com.authtoken.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authentikateroles")
public class AuthentikateRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String roleId;
    @Column(name = "rolename")
    String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authentikateusersroles", inverseJoinColumns = @JoinColumn(name = "userid"), joinColumns = @JoinColumn(name = "roleid"))
    List<AuthentikateRoles> associatedRoleUsers;

    public List<AuthentikateRoles> getAssociatedRoleUsers() {
        return associatedRoleUsers;
    }

    public void setAssociatedRoleUsers(List<AuthentikateRoles> associatedRoleUsers) {
        this.associatedRoleUsers = associatedRoleUsers;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
