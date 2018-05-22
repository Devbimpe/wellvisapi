package com.medviser.repository;

import com.medviser.models.Flag;
import com.medviser.models.Question;
import com.medviser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Longbridge on 18/05/2018.
 */
@Repository
public interface FlagRepository extends JpaRepository<Flag,Long>{
    Flag findByUser(User user);
    Flag findByUserAndQuestion(User user, Question q);


    @Query(value = "select question from Flag WHERE user =:user",nativeQuery = true)
    List<Question> findUserQuestions(@Param("user") User user);
}
