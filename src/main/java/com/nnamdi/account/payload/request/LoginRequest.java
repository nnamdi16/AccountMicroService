package com.nnamdi.account.payload.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    @ApiModelProperty(position = 0, dataType = "String", required = true)
    private String username;

    @NotBlank
    @ApiModelProperty(position = 1, dataType = "String",required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
