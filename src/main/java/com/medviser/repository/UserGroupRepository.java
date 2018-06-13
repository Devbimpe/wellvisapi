package com.medviser.repository;

import com.medviser.models.User;
import com.medviser.models.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Longbridge on 13/06/2018.
 */
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup,Long>{
    List<UserGroup> findByUsers(User user);
}
