package com.medviser.dto;

import java.util.List;

/**
 * Created by Longbridge on 13/06/2018.
 */
public class UserGroupDTO {
    private Long id;
    private String name;
    private List<UserDTO> userDTOS;

    private List<Long> userIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDTO> getUserDTOS() {
        return userDTOS;
    }

    public void setUserDTOS(List<UserDTO> userDTOS) {
        this.userDTOS = userDTOS;
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


