package com.medviser.dto;

import java.util.List;

/**
 * Created by Longbridge on 06/02/2018.
 */
public class HomePageRespDTO {
    public List<QuestionResDTO> questions;
    public UserDTO userDTO;

    public HomePageRespDTO() {
    }

    public HomePageRespDTO(List<QuestionResDTO> questions, UserDTO userDTO) {
        this.questions = questions;
        this.userDTO = userDTO;
    }
}
