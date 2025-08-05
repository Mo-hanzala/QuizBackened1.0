package com.hanz.quiz.service;


import com.hanz.quiz.entity.UserEntity;
import com.hanz.quiz.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;


    public UserEntity saveUserData(UserEntity user){
        return userRepo.save(user);
    }

    public List<UserEntity> getAllUsers(){
        List<UserEntity> list=userRepo.findAll();
        return list;

    }

    public UserEntity getUserById(String u_name){
        Optional<UserEntity> u=userRepo.findById(u_name);
        if(u.isPresent()) return u.get();
        else return null;
    }




}
