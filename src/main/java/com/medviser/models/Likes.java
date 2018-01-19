package com.medviser.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by longbridge on 10/18/17.
 */
@Entity
public class Likes extends CommonFields {
    @JsonIgnore
    @ManyToOne
    public Question question;

    @OneToOne
    public User user;
}
