package com.medviser.repository;


import com.medviser.models.Token;
import com.medviser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Longbridge on 01/11/2017.
 */
public interface TokenRepository extends JpaRepository<Token,Long> {

    Token findByUser(User user);

    Token findByUserAndToken(User user, String token);

    Boolean findByUserAndTokenAndValidated(User user, String token, Boolean validated);
}
