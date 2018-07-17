package com.medviser.services;

import com.medviser.dto.CommentLikesDTO;
import com.medviser.dto.ModeratePostDTO;
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

    Object deleteQuestion(Long id, User user);

    Object getQuestions(PageableDetailsDTO pageableDetailsDTO);

    Object getAllQuestions(PageableDetailsDTO pageableDetailsDTO);

    Object getLatestQuestions(PageableDetailsDTO pageableDetailsDTO,User user);

    Object getTrendingQuestions(PageableDetailsDTO pageableDetailsDTO,User user);

    Object getByCategory(String category, PageableDetailsDTO pageableDetailsDTO,User user);

    Object searchQuestion(String searchString, PageableDetailsDTO pageableDetailsDTO,User user);

    Object addLike(CommentLikesDTO commentLikesDTO, User user);

    Object addComment(CommentLikesDTO commentLikesDTO, User user);

    Object flagQuestion(Long questionId, User user);


    Object likeComment(Long commentId,User user);

    Object flagComment(Long commentId,User user);

    Object bookMarkQuestion(Long questionId, User user);

    Object moderateQuestion(ModeratePostDTO moderatePostDTO);

    Object getBookmarkedFeeds(User user);

    Object getFlaggedFeeds(User user);
}
