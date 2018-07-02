package com.medviser.services.implementations;

import com.medviser.dto.*;
import com.medviser.models.*;
import com.medviser.repository.*;
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

    @Autowired
    FlagRepository flagRepository;

    @Autowired
    BookMarkRepository bookMarkRepository;

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
                Likes likes = likeRepository.findByUserAndQuestion(user,q);
                if(likes != null){
                    likeRepository.delete(likes);
                    Long count = likeRepository.countByQuestion(q);

                    if(q.trendingCount != 0) {
                        q.trendingCount = q.trendingCount - 1;
                    }
                    questionRepository.save(q);
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
                    q.trendingCount = q.trendingCount+1;
                    questionRepository.save(q);
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
    public Object flagQuestion(Long questionId, User user) {
        Map<String,Object> responseMap = new HashMap();

        try {
            Date date = new Date();
            Question q = questionRepository.findOne(questionId);
            if(user != null && q != null){
                Flag flag = flagRepository.findByUserAndQuestion(user,q);
                if(flag != null){
                    flagRepository.delete(flag);
                }
                else {
                    Flag f = new Flag();
                    f.question = q;
                    f.user = user;
                    f.setCreatedOn(date);
                    f.setUpdatedOn(date);
                    flagRepository.save(f);
                }
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
    public Object bookMarkQuestion(Long questionId, User user) {
        Map<String,Object> responseMap = new HashMap();

        try {
            Date date = new Date();
            Question q = questionRepository.findOne(questionId);
            if(user != null && q != null){
                BookMark bookMark = bookMarkRepository.findByUserAndQuestion(user,q);
                if(bookMark != null){
                    bookMarkRepository.delete(bookMark);
                }
                else {
                    BookMark b = new BookMark();
                    b.question = q;
                    b.user = user;
                    b.setCreatedOn(date);
                    b.setUpdatedOn(date);
                    bookMarkRepository.save(b);
                }
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
    public Object moderateQuestion(ModeratePostDTO moderatePostDTO) {
        Map<String,Object> responseMap = new HashMap();
        try{
        Question q = questionRepository.findOne(moderatePostDTO.questionId);
        if(q!=null){
            q.delFlag=moderatePostDTO.delFlag;
            questionRepository.save(q);
            responseMap.put("success","success");
            Response response = new Response("Success","Operation Successful",responseMap);
            return response;
        }
        }catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occurred",responseMap);
        return response;
    }

    @Override
    public Object getBookmarkedFeeds(User user) {
        Map<String,Object> responseMap = new HashMap();

        try {

                List<QuestionResDTO> q= convertQuestionEntitiesToDTO(bookMarkRepository.findQuestion(user),user);

                responseMap.put("questions",q);
                Response response = new Response("Success","Operation Successful",responseMap);
                return response;


        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occurred",responseMap);
        return response;
    }

    @Override
    public Object getFlaggedFeeds(User user) {
        Map<String,Object> responseMap = new HashMap();
        try {
            List<QuestionResDTO> q= convertQuestionEntitiesToDTO(flagRepository.findUserQuestions(user),user);
            responseMap.put("questions",q);
            Response response = new Response("Success","Operation Successful",responseMap);
            return response;
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

            QuestionResDTO qdto = convertQuestionEntityToDTO(question,user);
            System.out.println("user is" + user);
//            if(user != null) {
//                Likes likes = likeRepository.findByUserAndQuestion(user, question);
//                if (likes != null) {
//                    qdto.liked="true";
//                } else {
//                    qdto.liked="false";
//                }
//            }
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
            List<QuestionResDTO> qdto = convertQuestionEntitiesToDTO(questions.getContent(),null);
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
    public Object getAllQuestions(PageableDetailsDTO pageableDetailsDTO) {
        Map<String,Object> responseMap = new HashMap();
        try {
            Page<Question> questions = questionRepository.findAll(new PageRequest(pageableDetailsDTO.page,pageableDetailsDTO.size));

            List<QuestionResDTO> qdto = convertQuestionEntitiesToDTO(questions.getContent(),null);
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
    public Object getLatestQuestions(PageableDetailsDTO pageableDetailsDTO,User user) {
        Map<String,Object> responseMap = new HashMap();
        try {
            Page<Question> latestQuestions = questionRepository.findAllByDelFlagOrderByCreatedOnDesc("N",new PageRequest(pageableDetailsDTO.page,pageableDetailsDTO.size));
            List<QuestionResDTO> qdto = convertQuestionEntitiesToDTO(latestQuestions.getContent(),user);
            responseMap.put("Questions",qdto);
            Response response = new Response("Success","Operation Successful",responseMap);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occured",responseMap);
        return response;
    }

    @Override
    public Object getTrendingQuestions(PageableDetailsDTO pageableDetailsDTO,User user) {
        Map<String,Object> responseMap = new HashMap();
        try {
            Page<Question> trendingQuestions = questionRepository.findAllByDelFlagOrderByTrendingCountDesc("N",new PageRequest(pageableDetailsDTO.page,pageableDetailsDTO.size));
            List<QuestionResDTO> qdto = convertQuestionEntitiesToDTO(trendingQuestions.getContent(),user);
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
    public Object getByCategory(String category, PageableDetailsDTO pageableDetailsDTO,User user) {
        Map<String,Object> responseMap = new HashMap();
        try {
            Page<Question> questions = questionRepository.findByCategoryAndDelFlag(category, "N",new PageRequest(pageableDetailsDTO.page,pageableDetailsDTO.size));
            List<QuestionResDTO> qdto = convertQuestionEntitiesToDTO(questions.getContent(),user);
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
    public Object searchQuestion(String searchString, PageableDetailsDTO pageableDetailsDTO,User user) {
        Map<String,Object> responseMap = new HashMap();
        try {
            Page<Question> questions = questionRepository.findUsingPattern(searchString, new PageRequest(pageableDetailsDTO.page,pageableDetailsDTO.size));
            List<QuestionResDTO> qdto = convertQuestionEntitiesToDTO(questions.getContent(),user);
            responseMap.put("Questions",qdto);
            Response response = new Response("Success","Operation Successful",responseMap);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occurred",responseMap);
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


    private List<QuestionResDTO> convertQuestionEntitiesToDTO(List<Question> questions,User user){
        List<QuestionResDTO> questionResDTOS= new ArrayList<QuestionResDTO>();

        for(Question question: questions){
            QuestionResDTO questionResDTO = convertQuestionEntityToDTO(question,user);
            questionResDTOS.add(questionResDTO);
        }
        return questionResDTOS;
    }

    private QuestionResDTO convertQuestionEntityToDTO(Question question,User user){
        QuestionResDTO q = new QuestionResDTO();
        List<CommentsDTO> cmts = convEntsToDTOs(question.comments);
        List<LikesDTO> likes = convertEntsToDTOs(question.likes);
        q.id=question.getId();
        q.comments = cmts;
        q.likes=likes;
        Long count = likeRepository.countByQuestion(question);
        if(question.anonymous==true){
            q.anonymous="true";
            q.userFullName = "Anonymous";
        }
        else {
            q.userFullName = question.user.fullName;
            q.anonymous="false";
        }

        if(user != null) {
            Likes l = likeRepository.findByUserAndQuestion(user, question);
            if (l != null) {
                q.liked="true";
            } else {
                q.liked="false";
            }

            Flag flag = flagRepository.findByUserAndQuestion(user,question);
            if(flag != null){
                q.flagged=true;
            }
            else {
                q.flagged=false;
            }

            BookMark bookMark = bookMarkRepository.findByUserAndQuestion(user,question);
            if(bookMark != null){
                q.bookmarked=true;
            }
            else {
                q.bookmarked=false;
            }
        }
        q.role=question.user.role;
        //q.anonymous = question.anonymous;
        q.category = question.category;
        q.description = question.description;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        q.date=formatter.format(question.getCreatedOn());
        //q.title = question.title;


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
