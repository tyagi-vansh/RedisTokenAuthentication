package com.example.Token.controller;

import com.example.Token.request.LoginRequest;
import com.example.Token.request.Request;
import com.example.Token.request.UpdateProfile;
import com.example.Token.response.Response;
import com.example.Token.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;


    @GetMapping("/view/{id}")
    public ResponseEntity getUser(@PathVariable int id,@RequestHeader("token") String token){
        return redisService.viewUser(id,token);
    }
    @PostMapping("/add")
    public String addUser(@RequestBody Request request){
        return redisService.addNewUser(request);
    }
    @PutMapping("/update/{id}")
    public String upateUser(@PathVariable int id, UpdateProfile updateProfile,@RequestHeader("token") String token ){
        return redisService.updateProfile(id,updateProfile,token);
    }
    @GetMapping("/login")
    public String userLogin(@RequestBody LoginRequest request){
        return redisService.login(request);
    }


}
