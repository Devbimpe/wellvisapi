package com.medviser.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 * Created by Longbridge on 29/04/2018.
 */
@Entity
public class HealthWorker extends CommonFields{
//    public String firstName;
//    public String lastName;
//    public String phoneNo;
//    public String email;
//    public String password;
    public String licenseNo;
//    public String gender;
    public String dob;
    public String areaOfExpertise;
    @Lob
    public String licenseFile;

    public String publicId;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    public User user;



    public HealthWorker() {
    }

    public HealthWorker(String licenseNo, String dob, String areaOfExpertise, String licenseFile, User user) {
        this.licenseNo = licenseNo;
        this.dob = dob;
        this.areaOfExpertise = areaOfExpertise;
        this.licenseFile = licenseFile;
        this.user = user;
    }
}
