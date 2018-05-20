package com.medviser.dto;

/**
 * Created by Longbridge on 20/05/2018.
 */
public class PassWordChangeDTO {

    private String code;
    private String email;
    private String newPassword;


    public PassWordChangeDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PassWordChangeDTO(String code, String email, String newPassword) {
        this.code = code;
        this.email = email;
        this.newPassword = newPassword;
    }
}
