package com.hanz.quiz.controller;


import com.hanz.quiz.dto.LoginRequest;
import com.hanz.quiz.entity.UserEntity;
import com.hanz.quiz.service.UserService;
import com.hanz.quiz.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserService userService;




    @PostMapping(value = "/reg", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        UserEntity temp = userService.getUserById(user.getUserName());
        if (temp == null) {
            UserEntity userEntity=userService.saveUserData(user);
            return ResponseEntity.ok(Map.of("message","Registration done succcessfully","data",userEntity));

        } else {
            return ResponseEntity.badRequest().body(Map.of("error","user already exist with this username"));
        }

    }


    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> userValidation(@RequestBody LoginRequest loginRequest) {
        UserEntity dbuser=userService.getUserById(loginRequest.getUserName());
        if(dbuser==null) return ResponseEntity.badRequest().body(Map.of("error","No such UserName exist"));
        else if(dbuser.getUserName().equals(loginRequest.getUserName()) && dbuser.getPassWord().equals(loginRequest.getPassWord()) && dbuser.getRole().equals(loginRequest.getRole())){
            List<String> roles=new ArrayList<>();
            if(dbuser.getRole().equals("ADMIN")){
                roles.add("ADMIN");
                roles.add("USER");
                String token=jwtUtil.generateToken(loginRequest.getUserName(), roles);
                return ResponseEntity.ok(Map.of("token",token));
            }
            else{
                roles.add("USER");
                roles.add("USER");
                String token=jwtUtil.generateToken(loginRequest.getUserName(), roles);
                return ResponseEntity.ok(Map.of("token",token));
            }


        }
        else return ResponseEntity.badRequest().body(Map.of("error","Incorrect UserName or PassWord"));
    }




}


