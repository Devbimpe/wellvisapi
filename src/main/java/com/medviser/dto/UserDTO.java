package com.medviser.dto;

import java.util.List;

/**
 * Created by Longbridge on 10/11/2017.
 */
public class UserDTO {

    private Long id;
    private String fullName;
    private String email;
    private String provider;

    private String gender;
    private String phoneNumber;
    private List<QuestionResDTO> questions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<QuestionResDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResDTO> questions) {
        this.questions = questions;
    }

    public UserDTO(Long id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public UserDTO() {
    }



}
