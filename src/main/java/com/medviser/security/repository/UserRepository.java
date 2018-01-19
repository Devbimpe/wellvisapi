package com.medviser.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends JpaRepository<com.medviser.models.User, Long> {
    com.medviser.models.User findByEmail(String username);
    com.medviser.models.User findById(Long Id);
}
