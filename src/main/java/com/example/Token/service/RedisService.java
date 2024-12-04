package com.example.Token.service;

import com.example.Token.entity.User;
import com.example.Token.repository.UserRepository;
import com.example.Token.request.LoginRequest;
import com.example.Token.request.Request;
import com.example.Token.request.UpdateProfile;
import com.example.Token.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@Service
public class RedisService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Jedis jedis;


    public String login(LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        User user = userRepository.findByEmail(email);
        if(user!=null){
            String oldpassword = user.getPassword();
            if(oldpassword.equals(loginRequest.getPassword())){
                String random= UUID.randomUUID().toString();
                int timeout= 120;
                jedis.setex(email,timeout,random);
                return  "Token : "+random;
            }
        }
        return "invalid Credentials";
    }
    public String addNewUser(Request request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setAge(request.getAge());
        user.setDepartment(request.getDepartment());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return "user added succesfully";
    }
    public ResponseEntity viewUser(int id,String token){

        User user = userRepository.findById(id);
        if(user!=null) {
            String email = user.getEmail();
            if (token.equals(jedis.get(email))) {
                Response result = new Response();
                result.setId(user.getId());
                result.setEmail(user.getEmail());
                result.setAge(user.getAge());
                result.setDepartment(user.getDepartment());
                result.setName(user.getName());
                result.setMobile(user.getMobile());
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.ok("inavlid access token");
        }
        return ResponseEntity.ok("User not found");
    }
    public String updateProfile(int id, UpdateProfile updateProfile,String token) {
        User user = userRepository.findById(id);
        String email = user.getEmail();
        if (user != null) {
            if(token.equals(jedis.get(email))) {
                user.setEmail(updateProfile.getEmail());
                user.setName(updateProfile.getName());
                user.setAge(updateProfile.getAge());
                user.setMobile(updateProfile.getMobile());
                user.setDepartment(updateProfile.getDepartment());
                userRepository.save(user);
                return "user updated successfully";
            }
            return "invalid access token";
        }
        return "user not found";
    }
}
