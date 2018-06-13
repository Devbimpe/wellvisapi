package com.medviser.controller;

import com.medviser.dto.QuestionResDTO;
import com.medviser.dto.UserGroupDTO;
import com.medviser.models.User;
import com.medviser.models.UserGroup;
import com.medviser.services.UserGroupService;
import com.medviser.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Longbridge on 13/06/2018.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/medviser/group")
public class UserGroupController {
    Logger logger = Logger.getLogger(UserController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    UserService userService;

    @Autowired
    UserGroupService userGroupService;



    @PostMapping(value = "/add")
    public Object addGroup(@RequestBody UserGroupDTO userGroupDTO, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User userTemp = userService.fetchUserDetails2(token);
        if(token==null || userTemp==null){
            return userService.tokenNullOrInvalidResponse(token);
        }
        return userGroupService.addGroup(userGroupDTO);
    }

    @GetMapping(value = "/getmygroups")
    public Object getGroups(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User user = userService.fetchUserDetails2(token);
        if(token==null || user==null){
            return userService.tokenNullOrInvalidResponse(token);
        }
        return userGroupService.getGroupByUser(user);
    }


    @GetMapping(value = "/getallgroups")
    public Object getAllGroups(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User user = userService.fetchUserDetails2(token);
        if(token==null || user==null){
            return userService.tokenNullOrInvalidResponse(token);
        }
        return userGroupService.getAllGroups();
    }








    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
