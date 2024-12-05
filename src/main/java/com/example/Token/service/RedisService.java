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
                int timeout= 300;
                jedis.setex(random,timeout,email);
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
    public ResponseEntity viewUser(String token){
        if(token!=null) {
            String email = jedis.get(token);
            if (email != null) {
                    User user = userRepository.findByEmail(email);
                    if(user!=null) {
                        Response result = new Response();
                        result.setId(user.getId());
                        result.setEmail(user.getEmail());
                        result.setAge(user.getAge());
                        result.setDepartment(user.getDepartment());
                        result.setName(user.getName());
                        result.setMobile(user.getMobile());
                        return ResponseEntity.ok(result);
                    }
                    return ResponseEntity.ok("User Not Found");
            }
            return ResponseEntity.ok("Invalid Access token ");
        }
        return ResponseEntity.ok("Empty Token");
    }
    public String updateProfile(UpdateProfile updateProfile,String token) {
        if(token!=null) {
            String email = jedis.get(token);
            if (email != null) {
                User user = userRepository.findByEmail(email);
                if(user!=null) {
                    user.setEmail(updateProfile.getEmail());
                    user.setName(updateProfile.getName());
                    user.setAge(updateProfile.getAge());
                    user.setMobile(updateProfile.getMobile());
                    user.setDepartment(updateProfile.getDepartment());
                    userRepository.save(user);
                    return "User updated successfully";
                }
                return "User Not Found";
            }
            return "user not found";
        }
        return "Empty token";
    }
}
