package com.medviser.security.service;

import com.medviser.security.JwtUserFactory;
import com.medviser.security.repository.Authority;
import com.medviser.security.repository.User;
import com.medviser.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
         com.medviser.models.User user1 =  userRepository.findByEmail(username);
         ArrayList<Authority> arrayList = new ArrayList<>();
         if(user1!=null){
             user = new User(user1.email,user1.password,true,null,arrayList);
         }
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }


}
