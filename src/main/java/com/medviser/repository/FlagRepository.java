package com.medviser.repository;

import com.medviser.models.Flag;
import com.medviser.models.Question;
import com.medviser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Longbridge on 18/05/2018.
 */
@Repository
public interface FlagRepository extends JpaRepository<Flag,Long>{
    Flag findByUser(User user);
    Flag findByUserAndQuestion(User user, Question q);
}
