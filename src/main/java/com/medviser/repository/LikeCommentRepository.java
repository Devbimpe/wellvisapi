package com.medviser.repository;

import com.medviser.models.Comments;
import com.medviser.models.LikeComment;
import com.medviser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Longbridge on 17/07/2018.
 */
public interface LikeCommentRepository extends JpaRepository<LikeComment,Long> {
        LikeComment findByUserAndComments(User user, Comments comments);
        List<LikeComment> findByComments(Comments comments);
}

