package com.medviser.dto;

/**
 * Created by Longbridge on 10/11/2017.
 */
public class CommentLikesDTO {
    private String comment;
    private Long questionId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public CommentLikesDTO(String comment, Long questionId) {
        this.comment = comment;
        this.questionId = questionId;
    }

    public CommentLikesDTO() {
    }
}
