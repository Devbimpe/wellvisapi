package com.medviser.repository;

import com.medviser.models.Question;
import com.medviser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Longbridge on 29/12/2017.
 */
public interface QuestionRepository extends CommonRepository<Question,Long> {
    Page<Question> findAllByDelFlagOrderByCreatedOnDesc(String delFlag,Pageable pageable);
    Page<Question> findAllByDelFlagOrderByTrendingCountDesc(String delFlag,Pageable pageable);
    Page<Question> findByCategoryAndDelFlag(String category,String delFlag, Pageable pageable);
    Page<Question> findByUserAndDelFlag(User user, String delFlag,Pageable pageable);

}
