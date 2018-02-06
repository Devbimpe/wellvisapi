package com.medviser.Util;

import com.medviser.config.BaseProvider;
//import com.medviser.models.User;
import com.medviser.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.social.facebook.api.User;

/**
 * Created by Longbridge on 23/01/2018.
 */
@Service
public class FacebookProvider {
    private static final String FACEBOOK = "facebook";
    private static final String REDIRECT_LOGIN = "redirect:/login";

    @Autowired
    BaseProvider baseProvider;


    public UserDTO getFacebookUserData(UserDTO userForm) {
        ConnectionRepository connectionRepository = baseProvider.getConnectionRepository();
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            //return REDIRECT_LOGIN;
            return null;
        }
        populateUserDetailsFromFacebook(userForm);
        //model.addAttribute("loggedInUser",userForm);
        return userForm;
    }


    protected void populateUserDetailsFromFacebook(UserDTO userForm) {
        Facebook facebook = baseProvider.getFacebook();
        User user = facebook.userOperations().getUserProfile();
        userForm.setEmail(user.getEmail());
        userForm.setFullName(user.getFirstName()+user.getLastName());
        userForm.setProvider(FACEBOOK);
    }


}
