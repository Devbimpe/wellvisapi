package com.medviser.models;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Question> questions;

}
