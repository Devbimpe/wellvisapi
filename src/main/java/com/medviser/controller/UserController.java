package com.medviser.controller;
import com.medviser.Util.FacebookProvider;
import com.medviser.dto.UserDTO;
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
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
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

    @Autowired
    FacebookProvider facebookProvider;

    @PostMapping(value = "/signin")
    public Object SignIn(@RequestBody User passedUser, Device device){
        return userService.validateUser(passedUser,device);
    }

    @PostMapping(value = "/register")
    public Object Register(@RequestBody User passedUser,Device device){
        return userService.registerUser(passedUser,device);
    }

    @PostMapping(value = "/updateprofile")
    public Object Register(@RequestBody User passedUser,HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User userTemp = userService.fetchUserDetails2(token);
        if(token==null || userTemp==null){
            return userService.tokenNullOrInvalidResponse(token);
        }
        return userService.updateProfile(passedUser,userTemp);
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public Object loginToFacebook() {
        System.out.println(facebookProvider.getFacebookUserData(new UserDTO()));
        return facebookProvider.getFacebookUserData(new UserDTO());
    }



    //FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory("353313211832474","0d5651dc9ac7aff6376949b31d25c76a");

    //OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();

    //OAuth2Parameters params = new OAuth2Parameters();

    //params.setRedirectUri(REDIRECT_URI);

   // params.setScope(SCOPE);

   // String authorizeUrl = oauthOperations.buildAuthorizeUrl(params);


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
