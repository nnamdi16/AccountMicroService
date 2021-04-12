package com.nnamdi.account.payload.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @ApiModelProperty(position = 0)
    private String username;


    @NotBlank
    @Size(max = 50)
    @Email
    @ApiModelProperty(position = 1,dataType = "String",required = true)
    private String email;

    @ApiModelProperty(position = 2,dataType = "String",required = true)
    private String name;

    @ApiModelProperty(position = 3, dataType = "String",required = true)
    private String phoneNumber;

    @ApiModelProperty(position = 4,dataType = "String", required = true)
    private String address;

    @ApiModelProperty(position = 5, dataType = "Set<String>")
    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    @ApiModelProperty(position = 6, dataType = "String", required = true)
    private String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
