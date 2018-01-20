package com.medviser.controller;
import com.medviser.models.User;
import com.medviser.security.JwtUser;
import com.medviser.security.repository.UserRepository;
import com.medviser.services.TokenService;
import com.medviser.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by longbridge on 11/4/17.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/medviser")
public class UserController {
    Logger logger = Logger.getLogger(UserController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/signin")
    public Object SignIn(@RequestBody User passedUser, Device device){
        return userService.validateUser(passedUser,device);
    }

    @PostMapping(value = "/register")
    public Object Register(@RequestBody User passedUser,Device device){
        return userService.registerUser(passedUser,device);
    }


    @GetMapping(value = "/getuserdetails")
    public Object fetchUserDetails(HttpServletRequest request){
        /*
        This is needed on any Endpoint that requires authorization.
         Any method you want to implement this should
        have the HttpServletRequest as param.
         */
        //======================================================
        String token = request.getHeader(tokenHeader);
        JwtUser user = userService.getAuthenticationDetails(token);
        if(token==null || user.getUsername()==null){
            return userService.tokenNullOrInvalidResponse(token);
        }
        //======================================================
        return userService.fetchUserDetails(user.getUsername(),token);
    }
//
//    @PostMapping(value = "/validateToken")
//    public Object validateToken(@RequestBody UserEmailTokenDTO userEmailTokenDTO){
//        User user = userService.getUserByEmail(userEmailTokenDTO.getEmail());
//        return tokenService.validateToken(user,userEmailTokenDTO.getToken());
//    }
//


    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
