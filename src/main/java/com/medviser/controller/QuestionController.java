package com.medviser.controller;

import com.medviser.dto.CommentLikesDTO;
import com.medviser.dto.ModeratePostDTO;
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
    public Object getLatest(@RequestBody PageableDetailsDTO pageableDetailsDTO, HttpServletRequest request){

        return questionService.getLatestQuestions(pageableDetailsDTO);
    }

    @PostMapping(value = "/gettrending")
    public Object getTrending(@RequestBody PageableDetailsDTO pageableDetailsDTO, HttpServletRequest request){

        return questionService.getTrendingQuestions(pageableDetailsDTO);
    }

    @PostMapping(value = "/{category}/getbycategory")
    public Object getByCategory(@RequestBody PageableDetailsDTO pageableDetailsDTO, @PathVariable String category,  HttpServletRequest request){

        return questionService.getByCategory(category,pageableDetailsDTO);
    }

    @PostMapping(value = "/{searchString}/searchquestion")
    public Object searchQuestion(@RequestBody PageableDetailsDTO pageableDetailsDTO, @PathVariable String searchString,  HttpServletRequest request){

        return questionService.searchQuestion(searchString,pageableDetailsDTO);
    }


    @GetMapping(value = "/{id}/getquestion")
    public Object getQuestion(@PathVariable Long id, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        System.out.println("token is" + token);
        User userTemp = null;
        if(token != null){
            userTemp = userUtil.fetchUserDetails2(token);
        }

        System.out.println("user is" + userTemp);
        return questionService.getQuestion(id,userTemp);
    }

    @GetMapping(value = "/getbookmarkedfeeds")
    public Object getBookmarkedFeeds(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User userTemp = userUtil.fetchUserDetails2(token);
        if(token==null || userTemp==null){
            return userUtil.tokenNullOrInvalidResponse(token);
        }

        return questionService.getBookmarkedFeeds(userTemp);
    }

    @GetMapping(value = "/getflaggedfeeds")
    public Object getFlaggedFeeds(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User userTemp = userUtil.fetchUserDetails2(token);
        if(token==null || userTemp==null){
            return userUtil.tokenNullOrInvalidResponse(token);
        }
        return questionService.getFlaggedFeeds(userTemp);
    }

    @PostMapping(value = "/flagThread/{questionId}")
    public Object flagQuestion(@PathVariable Long questionId, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User user = userUtil.fetchUserDetails2(token);
        if(token==null || user==null){
            return userUtil.tokenNullOrInvalidResponse(token);
        }
        return questionService.flagQuestion(questionId,user);
    }

    @PostMapping(value = "/bookmarkThread/{questionId}")
    public Object bookMarkQuestion(@PathVariable Long questionId, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User user = userUtil.fetchUserDetails2(token);
        if(token==null || user==null){
            return userUtil.tokenNullOrInvalidResponse(token);
        }
        return questionService.bookMarkQuestion(questionId,user);
    }


    @PostMapping(value = "/admin/getall")
    public Object getAll(@RequestBody PageableDetailsDTO pageableDetailsDTO, HttpServletRequest request){

        return questionService.getAllQuestions(pageableDetailsDTO);
    }


    @PostMapping(value = "/moderatepost")
    public Object moderateQuestion(@RequestBody ModeratePostDTO moderatePostDTO, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        User user = userUtil.fetchUserDetails2(token);
        if(token==null || user==null){
            return userUtil.tokenNullOrInvalidResponse(token);
        }
        return questionService.moderateQuestion(moderatePostDTO);
    }

    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
