package com.medviser.models;

import javax.persistence.Entity;

/**
 * Created by Longbridge on 10/07/2018.
 */
@Entity
public class Follower extends  CommonFields{
    private Long healthWorkerId;
    private Long followerId;


    public Long getHealthWorkerId() {
        return healthWorkerId;
    }

    public void setHealthWorkerId(Long healthWorkerId) {
        this.healthWorkerId = healthWorkerId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Follower(Long healthWorkerId, Long followerId) {
        this.healthWorkerId = healthWorkerId;
        this.followerId = followerId;
    }

    public Follower() {
    }
}
