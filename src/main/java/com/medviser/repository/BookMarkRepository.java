package com.medviser.repository;

import com.medviser.models.BookMark;
import com.medviser.models.Question;
import com.medviser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Longbridge on 18/05/2018.
 */
@Repository
public interface BookMarkRepository extends JpaRepository<BookMark,Long>{
    BookMark findByUser(User user);
    BookMark findByUserAndQuestion(User user, Question q);

    @Query(value = "select question from BookMark WHERE user =:user",nativeQuery = true)
    List<Question> findQuestion(@Param("user") User user);
}
