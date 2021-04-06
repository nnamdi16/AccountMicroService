package com.nnamdi.account.payload.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class JwtResponse {
    @ApiModelProperty(position = 0)
    private String token;

    @ApiModelProperty(position = 1)
    private String type = "Bearer";

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 3)
    private String username;

    @ApiModelProperty(position = 3)
    private String email;

    @ApiModelProperty(position = 4)
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
