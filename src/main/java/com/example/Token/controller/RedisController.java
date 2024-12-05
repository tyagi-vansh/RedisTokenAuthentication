package com.example.Token.controller;

import com.example.Token.request.LoginRequest;
import com.example.Token.request.Request;
import com.example.Token.request.UpdateProfile;
import com.example.Token.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;


    @GetMapping("/view")
    public ResponseEntity getUser(@RequestHeader("token") String token){
        return redisService.viewUser(token);
    }
    @PostMapping("/add")
    public String addUser(@RequestBody Request request){
        return redisService.addNewUser(request);
    }
    @PutMapping("/update")
    public String upateUser(@RequestBody UpdateProfile updateProfile,@RequestHeader("token") String token ){
        return redisService.updateProfile(updateProfile,token);
    }
    @GetMapping("/login")
    public String userLogin(@RequestBody LoginRequest request){
        return redisService.login(request);
    }


}
