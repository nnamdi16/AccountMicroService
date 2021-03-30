package com.nnamdi.account.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {
    @Column(name = "role_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(length = 20, name = "name")
    @Enumerated(EnumType.STRING)
    private RolesEnum name;

    public Roles() {

    }

    public Roles(RolesEnum name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RolesEnum getName() {
        return name;
    }

    public void setName(RolesEnum name) {
        this.name = name;
    }
}