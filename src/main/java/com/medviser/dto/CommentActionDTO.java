package com.medviser.dto;

/**
 * Created by Longbridge on 17/07/2018.
 */
public class CommentActionDTO {

    private Long id;

    private UserDTO user;

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

    public CommentActionDTO() {
    }
}
