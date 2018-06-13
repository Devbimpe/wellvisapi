package com.medviser.models;

/**
 * Created by Longbridge on 30/04/2018.
 */
public class LogInResp {
    private String token;
    private String role;

    public LogInResp(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LogInResp() {
    }
}
