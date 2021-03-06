package com.nnamdi.account.model;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class Account extends RepresentationModel<Account> {

    @Column(name = "account_id")
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long accountId;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")

    private String phoneNumber;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "password")
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> userRoles = new HashSet<>();


    public Account() {
    }

    public Account(@NotBlank @Size String username, String name, String address, String phoneNumber, String email, String password) {
        this.username = username;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public  boolean equals(Object o) {
        if (this == o) {
            return  true;
        }
        if (!(o instanceof Account))
            return false;

        Account account = (Account) o;
        return Objects.equals(this.accountId,account.accountId) && Objects.equals(this.name,account.name);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.accountId, this.name, this.address, this.password, this.email, this.phoneNumber);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + accountId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }


    public Set<Roles> getRoles() {
        return userRoles;
    }

    public void setRoles(Set<Roles> userRoles) {
        this.userRoles = userRoles;
    }
}
