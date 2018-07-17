package com.medviser.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by Longbridge on 17/07/2018.
 */
@Entity
public class FlagComment extends CommonFields{
    @JsonIgnore
    @ManyToOne
    public Comments comments;

    @OneToOne
    public User user;
}
