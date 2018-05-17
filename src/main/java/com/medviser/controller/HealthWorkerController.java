package com.medviser.controller;

import com.medviser.models.HealthWorker;
import com.medviser.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Lob;

/**
 * Created by Longbridge on 15/05/2018.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/medviser/hw")
public class HealthWorkerController {

//    @PostMapping(value = "/register")
//    public Object Register(@RequestBody HealthWorker healthWorker, Device device){
//        return userService.registerUser(healthWorker,device);
//    }




    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
