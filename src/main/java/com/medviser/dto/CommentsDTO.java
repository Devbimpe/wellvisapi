package com.medviser.dto;

import java.util.List;

/**
 * Created by Longbridge on 10/11/2017.
 */
public class CommentsDTO {

    private Long id;


    private UserDTO user;

    private String comment;

    private String createdDate;

    public List<CommentActionDTO> likes;

    private String liked;

    private String flagged;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getFlagged() {
        return flagged;
    }

    public void setFlagged(String flagged) {
        this.flagged = flagged;
    }
}
