package com.careerforge.dto;

public class LoginResponse {

    private String message;
    private String token;
    private AuthUserResponse user;

    public LoginResponse() {
    }

    public LoginResponse(
            String message,
            String token,
            AuthUserResponse user) {

        this.message = message;
        this.token = token;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthUserResponse getUser() {
        return user;
    }

    public void setUser(AuthUserResponse user) {
        this.user = user;
    }
}