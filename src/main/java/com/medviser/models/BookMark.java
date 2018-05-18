package com.medviser.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by Longbridge on 29/04/2018.
 */
@Entity
public class BookMark extends CommonFields{
    @JsonIgnore
    @ManyToOne
    public Question question;

    @OneToOne
    public User user;
}
