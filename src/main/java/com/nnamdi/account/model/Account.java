package com.nnamdi.account.model;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Account extends RepresentationModel<Account> {
    private @Id @GeneratedValue Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;

    public Account() {
    }

    public Account(String name, String address, String phoneNumber, String email, String password) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public  boolean equals(Object o) {
        if (this == o) {
            return  true;
        }
        if (!(o instanceof Account))
            return false;

        Account account = (Account) o;
        return Objects.equals(this.id,account.id) && Objects.equals(this.name,account.name);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.address, this.password, this.email, this.phoneNumber);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
