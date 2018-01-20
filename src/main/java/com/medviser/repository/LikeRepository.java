package com.medviser.repository;

import com.medviser.models.Likes;
import com.medviser.models.Question;
import com.medviser.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Longbridge on 10/11/2017.
 */
public interface LikeRepository extends PagingAndSortingRepository<Likes, Long> {
    long countByQuestion(Question question);
    Likes findByUser(User user);
    Likes findByUserAndQuestion(User user, Question q);

}
