package com.medviser.repository;


import com.medviser.models.Comments;
import com.medviser.models.Question;
import com.medviser.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Longbridge on 10/11/2017.
 */
public interface CommentRepository extends PagingAndSortingRepository<Comments, Long> {
    List<Comments> findByQuestion(Question question);

    List<Comments> findByUser(User user);
}
