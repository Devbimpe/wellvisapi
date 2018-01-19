package com.medviser.services.implementations;

import com.medviser.models.Response;
import com.medviser.models.Token;
import com.medviser.models.User;
import com.medviser.repository.TokenRepository;
import com.medviser.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Longbridge on 01/11/2017.
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    MessageSource messageSource;

    private Locale locale = LocaleContextHolder.getLocale();

    @Override
    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Token getToken(User host) {
        return tokenRepository.findByUser(host);
    }

    @Override
    public Object validateToken(User host, String token) {
        Map<String, Object> responseMap = new HashMap();
        Token token1 = tokenRepository.findByUserAndToken(host, token);
        if (token1 == null) {
            responseMap.put("null", null);
            Response response = new Response("Error", "Error occured while validating token", responseMap);
            return response;
        }
        else {
            if(token1.isValidated()){
                responseMap.put("null", null);
                Response response = new Response("Error", "Token already validated", responseMap);
                return response;
            }
            else {
                token1.setValidated(true);
                tokenRepository.save(token1);
                responseMap.put("null", null);
                Response response = new Response("Success", "Token successfully validated", responseMap);
                return response;
            }
        }

    }


    @Override
    public Object isValidated(User user, String token) {
        try {
            if (tokenRepository.findByUserAndTokenAndValidated(user, token,true)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

}
