package com.medviser.services.implementations;

import com.medviser.dto.UserDTO;
import com.medviser.dto.UserGroupDTO;
import com.medviser.models.Response;
import com.medviser.models.User;
import com.medviser.models.UserGroup;
import com.medviser.repository.UserGroupRepository;
import com.medviser.security.repository.UserRepository;
import com.medviser.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Longbridge on 13/06/2018.
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Response addGroup(UserGroupDTO userGroupDTO) {
        Map<String, Object> responseMap = new HashMap();
        try {
            UserGroup userGroup = new UserGroup();
            List<User> userList=new ArrayList<>();
            System.out.println(userGroupDTO.getUserIds());
            for(Long id: userGroupDTO.getUserIds()){
               User user = userRepository.findById(id);
               if(user != null) {
                   userList.add(user);
               }
            }
            System.out.println(userList);
            userGroup.setName(userGroupDTO.getName());
            userGroup.setUsers(userList);
            userGroupRepository.save(userGroup);
            Response response = new Response("Success", "Group successfully created", responseMap);
            return response;

        }catch (Exception e){
            Response response = new Response("Error", "Error occurred internally", responseMap);
            return response;
        }
    }

    @Override
    public Response getGroupByUser(User user) {
        Map<String, Object> responseMap = new HashMap();
        try {
            List<UserGroup> grps=userGroupRepository.findByUsers(user);
            List<UserGroupDTO> groups = convEntitiesToDTOs(grps);

            Response response = new Response("Success", "Successful", groups);
            return response;

        }catch (Exception e){
            Response response = new Response("Error", "Error occurred internally", responseMap);
            return response;
        }
    }

    @Override
    public Response getAllGroups() {
        Map<String, Object> responseMap = new HashMap();
        try {
            List<UserGroup> groups=userGroupRepository.findAll();

            Response response = new Response("Success", "Successful", convEntitiesToDTOs(groups));
            return response;

        }catch (Exception e){
            Response response = new Response("Error", "Error occurred internally", responseMap);
            return response;
        }
    }



    //----------CONVERT ENTITY TO DTOS-----------//
    private UserGroupDTO convertEntityToDTO(UserGroup userGroup){
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setId(userGroup.getId());
        userGroupDTO.setName(userGroup.getName());
        List<UserDTO> users = new ArrayList<>();
        userGroup.getUsers().forEach(user -> {
           users.add(convertUserEntityToUserDTO2(user));
        });
        userGroupDTO.setUsers(users);
        return userGroupDTO;

    }

    private List<UserGroupDTO> convEntitiesToDTOs(List<UserGroup> userGroups){
        List<UserGroupDTO> userGroupDTOS = new ArrayList<UserGroupDTO>();

        for(UserGroup userGroup: userGroups){
            UserGroupDTO userGroupDTO = convertEntityToDTO(userGroup);
            userGroupDTOS.add(userGroupDTO);
        }
        return userGroupDTOS;
    }


    private UserDTO convertUserEntityToUserDTO2(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.email);
        userDTO.setFullName(user.fullName);
        return userDTO;
    }

}
