package com.medviser.dto;

import java.util.List;

/**
 * Created by Longbridge on 04/01/2018.
 */
public class QuestionResDTO {
    public Long id;
    public String userId;
    public String userFullName;
    public String title;
    public String description;
    public String category;
    public String anonymous;
   // public String image;
    public List<CommentsDTO> comments;
    public List<LikesDTO> likes;
    public Long likesCount;

    public String liked;

    public QuestionResDTO(Long id, String userId, String userFullName, String title, String description, String category, String anonymous, List<CommentsDTO> comments, List<LikesDTO> likes, Long likesCount, String liked) {
        this.id = id;
        this.userId = userId;
        this.userFullName = userFullName;
        this.title = title;
        this.description = description;
        this.category = category;
        this.anonymous = anonymous;
        this.comments = comments;
        this.likes = likes;
        this.likesCount = likesCount;
        this.liked = liked;
    }


    public QuestionResDTO() {
    }



}
