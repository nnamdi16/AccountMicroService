package com.nnamdi.account.model;

import javax.persistence.*;

@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole name;

    public Role() {

    }

    public Role(UserRole name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public UserRole getName() {
        return name;
    }

    public void setName(UserRole name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return name;
    }

    public void setUserRole(UserRole name) {
        this.name = name;
    }
}
