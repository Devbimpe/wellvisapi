package com.medviser.services;

import com.medviser.dto.UserGroupDTO;
import com.medviser.models.Response;
import com.medviser.models.User;

/**
 * Created by Longbridge on 13/06/2018.
 */
public interface UserGroupService {
    public Response addGroup(UserGroupDTO userGroupDTO);

    public Response getGroupByUser(User user);

    public Response getAllGroups();
}
