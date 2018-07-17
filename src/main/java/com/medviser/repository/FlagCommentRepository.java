package com.medviser.repository;

import com.medviser.models.Comments;
import com.medviser.models.FlagComment;
import com.medviser.models.LikeComment;
import com.medviser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Longbridge on 17/07/2018.
 */
public interface FlagCommentRepository extends JpaRepository<FlagComment,Long> {
    FlagComment findByUserAndComments(User user, Comments comments);
}
