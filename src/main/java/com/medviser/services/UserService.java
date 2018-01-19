package com.medviser.services;

import com.medviser.Util.Hash;
import com.medviser.models.Email;
import com.medviser.models.Response;
import com.medviser.models.Token;
import com.medviser.models.User;
import com.medviser.repository.TokenRepository;
import com.medviser.security.JwtTokenUtil;
import com.medviser.security.JwtUser;
import com.medviser.security.repository.UserRepository;
import com.medviser.security.service.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;

/**
 * Created by Longbridge on 22/11/2017.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    MailService mailService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private Locale locale = LocaleContextHolder.getLocale();




    public Object registerUser(User passedUser,Device device){
        Map<String,Object> responseMap = new HashMap();
        try {

            User user = userRepository.findByEmail(passedUser.email);
            if(user==null){
                Date date = new Date();
                passedUser.password = Hash.createPassword(passedUser.password);
                passedUser.setCreatedOn(date);
                passedUser.setUpdatedOn(date);
                passedUser.accountVerified=false;

                //todo later generate unique token for new user and send to email
                //todo later for now i am sending hardcoded token
                String tokenGen = "ABC009";
                String name = passedUser.fullName;
                String mail = passedUser.email;

                Context context = new Context();
                context.setVariable("name", name);
                context.setVariable("code", tokenGen);
                String message = templateEngine.process("emailtemplate", context);

                mailService.prepareAndSend(message,mail,messageSource.getMessage("host.sendtoken.subject", null, locale));

                userRepository.save(passedUser);
                Token softToken= new Token();
                softToken.setToken(tokenGen);
                softToken.setUser(passedUser);
                tokenRepository.save(softToken);

                final UserDetails userDetails = userDetailsService.loadUserByUsername(passedUser.email);
                System.out.println("userdetails is "+userDetails.toString());
                final String token = jwtTokenUtil.generateToken(userDetails, device);
                System.out.println("Token is "+token);
                //implement sessionid
                responseMap.put("token",token);

                //responseMap.put("userId",passedUser.id);

                Response response = new Response("Success","Registration successful",responseMap);
                return response;
            }else{
                Response response = new Response("Error","Email already exists",responseMap);
                return response;
            }
        }catch (MailException me) {
            me.printStackTrace();
            Response response = new Response("Error","Email not sent",responseMap);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","Unable to complete Registration",responseMap);
        return response;
    }

    public Object validateUser(User passedUser, Device device){
        Map<String,Object> responseMap = new HashMap();
        try {
            User user = userRepository.findByEmail(passedUser.email);
            boolean valid = false;
            if(user!=null){
                try{
                    valid = Hash.checkPassword(passedUser.password,user.password);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(user!=null && valid){
                /*
                    Generating Token for user and this will be required for all request.
                 */
                final UserDetails userDetails = userDetailsService.loadUserByUsername(passedUser.email);
                System.out.println("userdetails is "+userDetails.toString());
                final String token = jwtTokenUtil.generateToken(userDetails, device);
                System.out.println("Token is "+token);
                //implement sessionid
                responseMap.put("token",token);
                Response response = new Response("Success","Login successful",responseMap);
                return response;
            }else{
                Response response = new Response("Error","Invalid username/password",responseMap);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","Error occurred internally",responseMap);
        return response;
    }
    public Object fetchUserDetails(String email, String token){
        Map<String,Object> responseMap = new HashMap();
        try {
            User user = userRepository.findByEmail(email);
            if(user!=null){

                /*
                todo : refreshing token needs to be discussed if necessary
                 */
//                refreshAuthenticationDetails(user,token);
                responseMap.put("userDetails",user);
                Response response = new Response("Success","User found",responseMap);
                return response;
            }else{
                Response response = new Response("Error","User not found",responseMap);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","Error occurred internally",responseMap);
        return response;
    }

    public User fetchUserDetails2(String token){
        JwtUser user = getAuthenticationDetails(token);
        if(user!=null){
            return userRepository.findByEmail(user.getUsername());
        }
        else{
            return null;
        }
    }

    public User getUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        return  user;
    }


    public JwtUser getAuthenticationDetails(String token){
        JwtUser user = null;
        if(token!=null) {
            String username = jwtTokenUtil.getUsernameFromToken(token.replace("Bearer ", ""));
            user = (JwtUser) userDetailsService.loadUserByUsername(username);
        }
        return user;
    }

    public Object tokenNullOrInvalidResponse(String token){
        Map<String,Object> responseMap = new HashMap();
        if(token!=null){
            responseMap.put("passed_token",token.replace("Bearer ", ""));
        }else{
            responseMap.put("passed_token",null);
        }
        Response response = new Response("Error","Invalid token passed",responseMap);
        return response;
    }



}
