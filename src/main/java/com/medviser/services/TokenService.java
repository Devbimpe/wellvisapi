package com.medviser.services;


import com.medviser.models.Token;
import com.medviser.models.User;

/**
 * Created by Longbridge on 01/11/2017.
 */
public interface TokenService {
    Token saveToken(Token token);

    Token getToken(User user);

    Object validateToken(User user, String token);

    Object isValidated(User user, String token);

}
