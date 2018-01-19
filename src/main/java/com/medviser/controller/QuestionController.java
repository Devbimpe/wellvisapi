package com.medviser.controller;

import com.medviser.dto.CommentLikesDTO;
import com.medviser.dto.PageableDetailsDTO;
import com.medviser.dto.QuestionResDTO;
import com.medviser.models.Question;
import com.medviser.models.User;
import com.medviser.services.QuestionService;
import com.medviser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Longbridge on 29/12/2017.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/medviser/que")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userUtil;

    @Value("${jwt.header}")
    private String tokenHeader;


    @PostMapping(value = "/submitquestion")
    public Object saveQuestion(@RequestBody QuestionResDTO question, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User user = userUtil.fetchUserDetails2(token);
        if(token==null || user==null){
            return userUtil.tokenNullOrInvalidResponse(token);
        }
        return questionService.saveQuestion(question,user);
    }

    @PostMapping(value = "/like")
    public Object likeQuestion(@RequestBody CommentLikesDTO commentLikesDTO, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User user = userUtil.fetchUserDetails2(token);
        if(token==null || user==null){
            return userUtil.tokenNullOrInvalidResponse(token);
        }
        return questionService.addLike(commentLikesDTO,user);
    }

    @PostMapping(value = "/comment")
    public Object addComment(@RequestBody CommentLikesDTO commentLikesDTO, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User userTemp = userUtil.fetchUserDetails2(token);
        if(token==null || userTemp==null){
            return userUtil.tokenNullOrInvalidResponse(token);
        }

        return questionService.addComment(commentLikesDTO, userTemp);
    }

    @PostMapping(value = "/getlatest")
    public Object addComment(@RequestBody PageableDetailsDTO pageableDetailsDTO, HttpServletRequest request){

        return questionService.getLatestQuestions(pageableDetailsDTO);
    }



    @GetMapping(value = "/{id}/getquestion")
    public Object getQuestion(@PathVariable Long id, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User userTemp = userUtil.fetchUserDetails2(token);
        if(token==null || userTemp==null){
            return userUtil.tokenNullOrInvalidResponse(token);
        }

        return questionService.getQuestion(id);
    }
    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
