package com.nnamdi.account.model;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRoles {
    @Id
    @Column(name = "user_role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userRoleId;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "role_id")
    private int roleId;

    public UserRoles(Long accountId, int roleId) {

        this.accountId = accountId;
        this.roleId = roleId;
    }

    public UserRoles() {

    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
