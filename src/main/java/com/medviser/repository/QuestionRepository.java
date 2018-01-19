package com.medviser.repository;

import com.medviser.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Longbridge on 29/12/2017.
 */
public interface QuestionRepository extends JpaRepository<Question,Long> {
    Page<Question> findAllByOrderByCreatedOnDesc(Pageable pageable);
}
