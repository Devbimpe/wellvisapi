package com.medviser.dto;

import java.util.List;

/**
 * Created by Longbridge on 13/06/2018.
 */
public class UserGroupDTO {
    private Long id;
    private String name;
    private List<UserDTO> users;

    private List<Long> userIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public UserGroupDTO() {
    }
}


