package com.medviser.repository;

import com.medviser.models.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Longbridge on 10/07/2018.
 */
@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
        Follower findByFollowerIdAndHealthWorkerId(Long followerId, Long healthWorkerId);
}
