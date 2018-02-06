package com.medviser.services.implementations;

import com.medviser.dto.*;
import com.medviser.models.*;
import com.medviser.repository.CommentRepository;
import com.medviser.repository.LikeRepository;
import com.medviser.repository.QuestionRepository;
import com.medviser.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Longbridge on 29/12/2017.
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    CommentRepository commentRepository;

    @Value("${s.wellvisimages.folder}")
    private String wellvisImagesFolder;

    @Override
    public Object saveQuestion(QuestionResDTO que, User user) {
        Map<String,Object> responseMap = new HashMap();
        try {

            Date date = new Date();
            Question question = new Question();
            question.setCreatedOn(date);
            question.setUpdatedOn(date);
            question.user= user;
            question.category = que.category;
            question.description = que.description;
            //question.title = que.title;
            if(que.anonymous.equalsIgnoreCase("yes")){
                question.anonymous=true;
            }
            else {
                question.anonymous=false;
            }

//            try {
//                String fileName = question.category +getCurrentTime();
//                String base64Img = que.image.split(",")[1];
//                byte[] imgBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Img);
//                ByteArrayInputStream bs = new ByteArrayInputStream(imgBytes);
//                File imgfilee =new File(wellvisImagesFolder + fileName);
//                question.evidenceName=fileName;
//                FileOutputStream f = new FileOutputStream(imgfilee);
//                int rd = 0;
//                final byte[] byt = new byte[1024];
//                while ((rd = bs.read(byt)) != -1) {
//                    f.write(byt, 0, rd);
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }

            questionRepository.save(question);
            //create dto for response for add question.
            responseMap.put("questionId",question.getId());
            Response response = new Response("Success","Operation Successful",responseMap);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occured",responseMap);
        return response;
    }


    @Override
    public Object addLike(CommentLikesDTO commentLikesDTO, User user) {
        Map<String,Object> responseMap = new HashMap();
        Date date = new Date();
        try {

            Question q = questionRepository.findOne(commentLikesDTO.getQuestionId());
            if(user != null && q !=null){
                Likes likes = likeRepository.findByUser(user);
                if(likes != null){
                    likeRepository.delete(likes);
                    Long count = likeRepository.countByQuestion(q);
                    responseMap.put("likes",count);
                    Response response = new Response("Success","Operation Successful",responseMap);
                    return response;
                }
                else {
                    Likes l = new Likes();
                    l.question = q;
                    l.user=user;
                    l.setCreatedOn(date);
                    l.setUpdatedOn(date);
                    likeRepository.save(l);
                    Long count = likeRepository.countByQuestion(q);
                    responseMap.put("likes",count);
                    Response response = new Response("Success","Operation Successful",responseMap);
                    return response;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occured",responseMap);
        return response;
    }

    @Override
    public Object addComment(CommentLikesDTO commentLikesDTO, User user) {
        Map<String,Object> responseMap = new HashMap();

        try {
            Date date = new Date();
            String comment = commentLikesDTO.getComment();

            Question q = questionRepository.findOne(commentLikesDTO.getQuestionId());

            if(user != null && q != null){
                Comments c = new Comments();
                c.question = q;
                c.comment = comment;
                c.user=user;
                c.setCreatedOn(date);
                c.setUpdatedOn(date);
                commentRepository.save(c);
                //List<Comments> comments=commentRepository.findByQuestion(q);
                //List<CommentsDTO> commentsDTOS = convEntsToDTOs(comments);
                //responseMap.put("comments",commentsDTOS);
                responseMap.put("success","success");
                Response response = new Response("Success","Operation Successful",responseMap);
                return response;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occurred",responseMap);
        return response;
    }

    @Override
    public Object getQuestion(Long id, User user) {

        Map<String,Object> responseMap = new HashMap();
        try {
            Question question = questionRepository.findOne(id);

            QuestionResDTO qdto = convertQuestionEntityToDTO(question);
            System.out.println("user is" + user);
            if(user != null) {
                Likes likes = likeRepository.findByUserAndQuestion(user, question);
                if (likes != null) {
                    qdto.liked="true";
                } else {
                    qdto.liked="false";
                }
            }
            responseMap.put("Question",qdto);
            Response response = new Response("Success","Operation Successful",responseMap);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occurred",responseMap);
        return response;
    }

    @Override
    public Object getQuestions(PageableDetailsDTO pageableDetailsDTO) {
        Map<String,Object> responseMap = new HashMap();
        try {
           // List<Question> questions = questionRepository.findAll();

            //List<QuestionResDTO> qdto = convertQuestionEntitiesToDTO(questions);
            Page<Question> questions = questionRepository.findAll(new PageRequest(pageableDetailsDTO.page,pageableDetailsDTO.size));
            List<QuestionResDTO> qdto = convertQuestionEntitiesToDTO(questions.getContent());
            responseMap.put("Questions",qdto);
            Response response = new Response("Success","Operation Successful",responseMap);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occurred",responseMap);
        return response;
    }


    @Override
    public Object getLatestQuestions(PageableDetailsDTO pageableDetailsDTO) {
        Map<String,Object> responseMap = new HashMap();
        try {
            Page<Question> latestQuestions = questionRepository.findAllByOrderByCreatedOnDesc(new PageRequest(pageableDetailsDTO.page,pageableDetailsDTO.size));
            List<QuestionResDTO> qdto = convertQuestionEntitiesToDTO(latestQuestions.getContent());
            responseMap.put("Questions",qdto);
            Response response = new Response("Success","Operation Successful",responseMap);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occured",responseMap);
        return response;
    }

    //----------CONVERT ENTITY TO DTOS-----------//
    private CommentsDTO convertEntityToDTO(Comments c){
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setComment(c.comment);
        commentsDTO.setId(c.getId());

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String stringDate = formatter.format(c.getCreatedOn());
        commentsDTO.setCreatedDate(stringDate);
        commentsDTO.setUser(convertUserEntityToUserDTO(c.user));

        return commentsDTO;

    }

    private List<CommentsDTO> convEntsToDTOs(List<Comments> c){
        List<CommentsDTO> commentsDTOS = new ArrayList<CommentsDTO>();

        for(Comments comments: c){
            CommentsDTO commentsDTO = convertEntityToDTO(comments);
            commentsDTOS.add(commentsDTO);
        }
        return commentsDTOS;
    }

    private LikesDTO convEntityToDTO(Likes l){
        LikesDTO likesDTO = new LikesDTO();

        likesDTO.setId(l.getId());
        likesDTO.setUser(convertUserEntityToUserDTO(l.user));
        return likesDTO;

    }

    private List<LikesDTO> convertEntsToDTOs(List<Likes> l){
        List<LikesDTO> likesDTOS = new ArrayList<LikesDTO>();

        for(Likes likes: l){
            LikesDTO likesDTO = convEntityToDTO(likes);
            likesDTOS.add(likesDTO);
        }
        return likesDTOS;

    }



    private UserDTO convertUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.email);
        userDTO.setId(user.getId());
        userDTO.setFullName(user.fullName);
        return userDTO;
    }


    private List<QuestionResDTO> convertQuestionEntitiesToDTO(List<Question> questions){
        List<QuestionResDTO> questionResDTOS= new ArrayList<QuestionResDTO>();

        for(Question question: questions){
            QuestionResDTO questionResDTO = convertQuestionEntityToDTO(question);
            questionResDTOS.add(questionResDTO);
        }
        return questionResDTOS;
    }

    private QuestionResDTO convertQuestionEntityToDTO(Question question){
        QuestionResDTO q = new QuestionResDTO();
        List<CommentsDTO> cmts = convEntsToDTOs(question.comments);
        List<LikesDTO> likes = convertEntsToDTOs(question.likes);
        q.id=question.getId();
        q.comments = cmts;
        q.likes=likes;
        Long count = likeRepository.countByQuestion(question);
        if(question.anonymous==true){
            q.anonymous="true";
        }
        else {
            q.anonymous="false";
        }
        //q.anonymous = question.anonymous;
        q.category = question.category;
        q.description = question.description;
        //q.title = question.title;
        q.userFullName = question.user.fullName;
        q.userId = question.user.getId().toString();
        q.likesCount =count;
        return q;
    }


    private UserDTO convertUserEntToDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.fullName);
        dto.setEmail(user.email);
        dto.setGender(user.gender);
        dto.setPhoneNumber(user.phoneNo);
        return dto;

    }


    private String getCurrentTime(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        String cTime = year+""+month+""+day+""+hour+""+minute+""+second+""+millis;
        return cTime;
    }



}
