package com.medviser.controller;
import com.medviser.Util.FacebookProvider;
import com.medviser.dto.ModeratePostDTO;
import com.medviser.dto.PageableDetailsDTO;
import com.medviser.dto.PassWordChangeDTO;
import com.medviser.dto.UserDTO;
import com.medviser.exception.AppException;
import com.medviser.models.Follower;
import com.medviser.models.MailError;
import com.medviser.models.Response;
import com.medviser.models.User;
import com.medviser.repository.MailErrorRepository;
import com.medviser.security.JwtUser;
import com.medviser.security.repository.UserRepository;
import com.medviser.services.FollowerService;
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
import java.util.HashMap;
import java.util.Map;

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
    FollowerService followerService;

    @Autowired
    TokenService tokenService;

    @Autowired
    MailErrorRepository mailErrorRepository;

    @Autowired
    FacebookProvider facebookProvider;

    @PostMapping(value = "/signin")
    public Object SignIn(@RequestBody User passedUser, Device device){
        return userService.validateUser(passedUser,device);
    }

    @PostMapping(value = "/register")
    public Object Register(@RequestBody User passedUser,Device device){
        try {
            return userService.registerUser(passedUser, device);
        }catch (AppException e){
            e.printStackTrace();
            String recipient = e.getRecipient();
            String subject = e.getSubject();
            MailError mailError = new MailError();
            mailError.setName(e.getName());
            mailError.setRecipient(recipient);
            mailError.setSubject(subject);
            mailError.setLink(e.getLink());
            mailError.setMailType("welcome");
            mailErrorRepository.save(mailError);
            Response response = new Response("00", "Registration successful, Trying to send welcome email", "success");
            return response;
        }
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


    @PostMapping(value = "/changepassword")
    public Object changePassword(@RequestBody PassWordChangeDTO passWordChangeDTO){

        return userService.changePassword(passWordChangeDTO);
    }



    @PostMapping(value = "/resetpassword")
    public Object resetPassword(@RequestBody PassWordChangeDTO passWordChangeDTO,HttpServletRequest request){

        String token = request.getHeader(tokenHeader);
        User userTemp = userService.fetchUserDetails2(token);
        if(token==null || userTemp==null){
            return userService.tokenNullOrInvalidResponse(token);
        }
        return userService.resetPassword(passWordChangeDTO,userTemp);
    }


    @PostMapping(value = "/forgotpassword")
    public Object forgotPassword(@RequestBody User user) {

        Map<String, Object> responseMap = new HashMap();
        try {
            System.out.println(user);
            return userService.forgotPassword(user);
        } catch (AppException e) {
            e.printStackTrace();
            String recipient = e.getRecipient();
            String subject = e.getSubject();

            MailError mailError = new MailError();
            mailError.setNewPassword(e.getNewPassword());
            mailError.setName(e.getName());
            mailError.setRecipient(recipient);
            mailError.setSubject(subject);
            mailError.setLink(e.getLink());
            mailErrorRepository.save(mailError);
            Response response = new Response("00", "Operation Successful, Trying to send password to email", responseMap);
            return response;
            //======================================================

        }
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




    @GetMapping(value = "/gethealthworkers")
    public Object fetchHealthWorkers(HttpServletRequest request,PageableDetailsDTO pageableDetailsDTO){
//
//        String token = request.getHeader(tokenHeader);
//        JwtUser user = userService.getAuthenticationDetails(token);
//        if(token==null || user.getUsername()==null){
//            return userService.tokenNullOrInvalidResponse(token);
//        }

        return userService.fetchHealthWorkers(pageableDetailsDTO);
    }


    @GetMapping(value = "/getallusers")
    public Object fetchUsers(HttpServletRequest request,PageableDetailsDTO pageableDetailsDTO){
//
//        String token = request.getHeader(tokenHeader);
//        JwtUser user = userService.getAuthenticationDetails(token);
//        if(token==null || user.getUsername()==null){
//            return userService.tokenNullOrInvalidResponse(token);
//        }

        return userService.getAllUsers(pageableDetailsDTO);
    }

    @GetMapping(value = "/useraction")
    public Object userAction(HttpServletRequest request, ModeratePostDTO moderatePostDTO){

        return userService.adminUpdateUser(moderatePostDTO);
    }

    @PostMapping(value = "/getuserprofile")
    public Object fetchUserProfile(HttpServletRequest request, @RequestBody PageableDetailsDTO pageableDetailsDTO){
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
        return userService.fetchUserProfile(user.getUsername(),pageableDetailsDTO);
    }




    @PostMapping(value = "/followhealthworker")
    public Object followHealthWorker(@RequestBody Follower follower, HttpServletRequest request){

        String token = request.getHeader(tokenHeader);
        User userTemp = userService.fetchUserDetails2(token);
        if(token==null || userTemp==null){
            return userService.tokenNullOrInvalidResponse(token);
        }
        return followerService.followHealthWorker(follower,userTemp);
    }


    @PostMapping(value = "/{id}/getuserbyid")
    public Object getUserById(HttpServletRequest request, @PathVariable Long id,@RequestBody PageableDetailsDTO pageableDetailsDTO){

        String token = request.getHeader(tokenHeader);
        User userTemp = userService.fetchUserDetails2(token);
        if(token==null || userTemp==null){
            return userService.tokenNullOrInvalidResponse(token);
        }
        User u=userService.getUserById(id);
        return userService.fetchUserProfile(u.email,pageableDetailsDTO);
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
