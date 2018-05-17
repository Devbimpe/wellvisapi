package com.medviser.services;

import com.medviser.dto.CommentLikesDTO;
import com.medviser.dto.PageableDetailsDTO;
import com.medviser.dto.QuestionResDTO;
import com.medviser.models.Question;
import com.medviser.models.User;

import java.awt.print.Pageable;

/**
 * Created by Longbridge on 29/12/2017.
 */

public interface QuestionService {
    Object saveQuestion(QuestionResDTO question, User user);

    Object getQuestion(Long id, User user);

    Object getQuestions(PageableDetailsDTO pageableDetailsDTO);

    Object getLatestQuestions(PageableDetailsDTO pageableDetailsDTO);

    Object getTrendingQuestions(PageableDetailsDTO pageableDetailsDTO);

    Object getByCategory(String category, PageableDetailsDTO pageableDetailsDTO);

    Object addLike(CommentLikesDTO commentLikesDTO, User user);

    Object addComment(CommentLikesDTO commentLikesDTO, User user);
}
