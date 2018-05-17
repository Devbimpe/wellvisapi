package com.medviser.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by longbridge on 10/18/17.
 */
@Entity
public class User extends CommonFields{
    public String fullName;
    public String email;
    @Lob
    public String password;

    public String gender;

    /*todo later*/
    public String phoneNo;

    public boolean accountVerified;

    public String age;

    public String location;

    public String profilePicture;

    public String heartRate;

    public String bloodPressure;

    public String height;

    public String weight;

    public String socialFlag = "N";

    @OneToOne(cascade = CascadeType.ALL)
    public HealthWorker healthWorker;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Question> questions;

}
