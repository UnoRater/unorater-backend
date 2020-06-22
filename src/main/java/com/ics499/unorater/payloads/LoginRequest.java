package com.ics499.unorater.payloads;

import javax.validation.constraints.NotBlank;

/**
 * POJO representing a login request
 */
public class LoginRequest {
    @NotBlank
    private String userNameOrEmail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail() {
        return userNameOrEmail;
    }

    public void setUsernameOrEmail(String userNameOrEmail) {
        this.userNameOrEmail = userNameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
