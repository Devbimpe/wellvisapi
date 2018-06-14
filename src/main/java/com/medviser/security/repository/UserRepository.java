package com.medviser.security.repository;

import com.medviser.models.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends JpaRepository<com.medviser.models.User, Long> {
    com.medviser.models.User findByEmail(String username);
    com.medviser.models.User findById(Long Id);

    List<com.medviser.models.User> findByHealthWorkerNotNull(Pageable pageable);

    List<com.medviser.models.User> findByHealthWorkerIsNull(Pageable pageable);


}
