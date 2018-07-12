package com.medviser.services;

import com.medviser.models.Follower;
import com.medviser.models.User;

/**
 * Created by Longbridge on 10/07/2018.
 */
public interface FollowerService {
    Object followHealthWorker(Follower follower,User user);
}
