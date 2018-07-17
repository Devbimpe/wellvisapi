package com.medviser.services.implementations;

import com.medviser.dto.QuestionResDTO;
import com.medviser.models.Follower;
import com.medviser.models.Question;
import com.medviser.models.Response;
import com.medviser.models.User;
import com.medviser.repository.FollowerRepository;
import com.medviser.services.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Longbridge on 10/07/2018.
 */
@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    FollowerRepository followerRepository;

    @Override
    public Object followHealthWorker(Follower follower, User user) {
        Map<String,Object> responseMap = new HashMap();
        try {
            if(followerRepository.findByFollowerIdAndHealthWorkerId(user.getId(),follower.getHealthWorkerId()) == null){
                follower.setFollowerId(user.getId());
                follower.setHealthWorkerId(follower.getHealthWorkerId());
                followerRepository.save(follower);
            }
            Response response = new Response("Success","Operation Successful",responseMap);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = new Response("Error","error occurred",responseMap);
        return response;
    }
}
