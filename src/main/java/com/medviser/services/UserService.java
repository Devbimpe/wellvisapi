package com.medviser.services;

import com.medviser.Util.Hash;
import com.medviser.dto.PageableDetailsDTO;
import com.medviser.dto.UserDTO;
import com.medviser.exception.AppException;
import com.medviser.models.Email;
import com.medviser.models.Response;
import com.medviser.models.Token;
import com.medviser.models.User;
import com.medviser.repository.HealthWorkerRepository;
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
    HealthWorkerRepository healthWorkerRepository;

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


                if(passedUser.healthWorker != null){
                    passedUser.healthWorker.setCreatedOn(date);
                    passedUser.healthWorker.setUpdatedOn(date);
                    //passedUser.designer.userId = passedUser.id;
                    passedUser.healthWorker.user=passedUser;
//                    if(passedUser.healthWorker.licenseFile != null) {
//                        try {
//                            String fileName = passedUser.email.substring(0, 3) + getCurrentTime();
//                            String base64Img = passedUser.healthWorker.licenseFile;
//                            CloudinaryResponse c = generalUtil.uploadToCloud(base64Img,fileName,"designerlogos");
//                            passedUser.healthWorker.logo = c.getUrl();
//                            passedUser.healthWorker.publicId=c.getPublicId();
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                            Response response = new Response("99","Error occurred internally",responseMap);
//                            return response;
//                        }
//                    }
                }
                 healthWorkerRepository.save(passedUser.healthWorker);
                userRepository.save(passedUser);
                Token softToken= new Token();
                String tokenGen = UUID.randomUUID().toString().substring(0,10);
                softToken.setToken(tokenGen);
                softToken.setUser(passedUser);
                tokenRepository.save(softToken);

                String message = "";
                String name = passedUser.fullName;
                String mail = passedUser.email;
                try {
                    Context context = new Context();
                    context.setVariable("name", name);
                    context.setVariable("code", tokenGen);
                    if (passedUser.healthWorker != null) {
                        message = templateEngine.process("healthworkerwelcomeemail", context);
                    } else {
                        message = templateEngine.process("emailtemplate", context);
                    }


                    mailService.prepareAndSend(message, mail, messageSource.getMessage("host.sendtoken.subject", null, locale));

                }catch (MailException me){
                    me.printStackTrace();
                    throw new AppException("",passedUser.fullName, user.email,messageSource.getMessage("host.sendtoken.subject", null, locale),"");

                }
                final UserDetails userDetails = userDetailsService.loadUserByUsername(passedUser.email);
                System.out.println("userdetails is "+userDetails.toString());
                final String token = jwtTokenUtil.generateToken(userDetails, device);
                System.out.println("Token is "+token);
                //implement sessionid
                responseMap.put("token",token);


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


    public Object updateProfile(User passedUser, User userTemp){
        Map<String,Object> responseMap = new HashMap();
        try {
            Date date = new Date();
            userTemp.phoneNo = passedUser.phoneNo;
            userTemp.age = passedUser.age;
            userTemp.bloodPressure = passedUser.bloodPressure;
            userTemp.gender = passedUser.gender;
            userTemp.heartRate = passedUser.heartRate;
            userTemp.height = passedUser.height;
            userTemp.location = passedUser.location;
            userTemp.weight = passedUser.weight;
            userTemp.setUpdatedOn(date);
            userRepository.save(userTemp);
            Response response = new Response("Success","Update successful",responseMap);
            return response;

        }
        catch (Exception ex){
            ex.printStackTrace();
            Response response = new Response("Error","Unable to complete update",responseMap);
            return response;
        }

    }


    public Object forgotPassword(User passedUser){
        Map<String,Object> responseMap = new HashMap();
        String newPassword="";
        String name = "";
        String mail = "";
        String changePasswordLink="";
        try {

            User user = userRepository.findByEmail(passedUser.email);

            if(user!=null){
                //newPassword = generalUtil.getCurrentTime();
                //newPassword = RandomStringUtils.randomAlphanumeric(10);
                newPassword=UUID.randomUUID().toString().substring(0,10);
                user.password = Hash.createPassword(newPassword);

                name = user.fullName;
                mail = passedUser.email;
                String encryptedMail = Base64.getEncoder().encodeToString(mail.getBytes());
                //String encryptedMail = Hash.createEncryptedLink(mail);
                changePasswordLink = messageSource.getMessage("change.password.link",null,locale)+encryptedMail;
                System.out.println(changePasswordLink);

                Context context = new Context();
                context.setVariable("password", newPassword);
                context.setVariable("name", name);
                context.setVariable("link", changePasswordLink);
                String message = templateEngine.process("passwordemailtemplate", context);

                mailService.prepareAndSend(message,mail,messageSource.getMessage("password.reset.subject", null, locale));

                userRepository.save(user);
                Response response = new Response("00","Operation Successful, Password successfully sent to email",responseMap);
                return response;
            }else{
                Response response = new Response("99","Error occurred",responseMap);
                return response;
            }
        }catch (MailException me) {
            me.printStackTrace();
            throw new AppException(newPassword,name,mail,messageSource.getMessage("password.reset.subject", null, locale),changePasswordLink);


        }
        catch (Exception e) {
            e.printStackTrace();
            Response response = new Response("99", "Error occured internally", responseMap);
            return response;
        }
    }


    public Object validateUser(User passedUser, Device device){
        Map<String,Object> responseMap = new HashMap();
        try {
            User user = userRepository.findByEmail(passedUser.email);
            boolean valid = false;
            if(user!=null){
                try{
                    //valid = Hash.checkPassword(passedUser.password,user.password);

                    if(user.socialFlag.equalsIgnoreCase("Y")){
                        valid=true;
                    }
                    else {
                        //If N, carry go
                        valid = Hash.checkPassword(passedUser.password, user.password);
                    }
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
                responseMap.put("userDetails",convertUserEntityToUserDTO(user));
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


    public Object fetchUserProfile(String email, PageableDetailsDTO pageableDetailsDTO){
        Map<String,Object> responseMap = new HashMap();
        try {
            User user = userRepository.findByEmail(email);
            if(user!=null){
                responseMap.put("userDetails",convertUserEntityToUserDTO(user));
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



    private UserDTO convertUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.email);
        userDTO.setId(user.getId());
        userDTO.setFullName(user.fullName);
        userDTO.setPhoneNumber(user.phoneNo);
        userDTO.setGender(user.gender);

        return userDTO;
    }

    private UserDTO convertUserEntityToUserDTO2(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.email);
        userDTO.setId(user.getId());
        userDTO.setFullName(user.fullName);
        userDTO.setPhoneNumber(user.phoneNo);
        userDTO.setGender(user.gender);


        return userDTO;
    }


}
