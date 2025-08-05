package com.hanz.quiz.controller;


import com.hanz.quiz.entity.Questions;
import com.hanz.quiz.service.QuestionService;
import com.hanz.quiz.utility.JwtUtil;
import com.hanz.quiz.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("api/quiz")
public class QuizController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    Utility utility;

    @Autowired
    QuestionService questionService;


    @PostMapping(value = "/questionpost"  ,produces = "application/json",  consumes = "application/json")
    public ResponseEntity<?> uploadQuestion(@RequestBody Questions question,@RequestHeader(value = "Authorization", required = false) String authorizationHeader){
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token=authorizationHeader.substring(7);
            if(jwtUtil.isTokenValid(token)){
                List<String> roles=new ArrayList<>(jwtUtil.extractRoles(token));
                if(!roles.get(0).equals("ADMIN") || !roles.get(1).equals("USER")){
                    return ResponseEntity.badRequest().body(Map.of("error","Unauthorized user"));
                }
            }
            else{
                return ResponseEntity.badRequest().body(Map.of("error","Invalid token"));
            }
        }
        else{
            return ResponseEntity.badRequest().body(Map.of("error","token is missing or malformed"));
        }
        String newQ=utility.normalize(question.getQuestionText());
        List<Questions> allQuestions=questionService.getAllQuestions();
        boolean duplicate=allQuestions.stream().anyMatch(q -> utility.normalize(q.getQuestionText()).equals(newQ));
        if(duplicate) return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","⚠️ Duplicate question detected!"));
        Questions quizQuestion= questionService.saveQuestion(question);
        if(quizQuestion==null ) return ResponseEntity.badRequest().body(Map.of("error","Failed to Upload Question"));
        else return ResponseEntity.ok(Map.of("message","question uploaded successfully","data",quizQuestion));
    }


    @GetMapping(value = "/questionget" ,produces = "application/json" )
    public ResponseEntity<?> getAllQuesByType(@RequestParam String type,@RequestHeader(value = "Authorization", required = false) String authorizationHeader){
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token=authorizationHeader.substring(7);
            if(!jwtUtil.isTokenValid(token)){
                return ResponseEntity.badRequest().body(Map.of("error","Invalid token"));
            }

        }
        else{
            return ResponseEntity.badRequest().body(Map.of("error","token is missing or malformed"));
        }
        List<Questions> questions=new ArrayList<>(questionService.getAllQuestionsByType(type));
        return ResponseEntity.ok(questions);

    }


    @PostMapping(value = "/questionupdate" ,produces = "application/json" ,consumes = "application/json")
    public ResponseEntity<?> updateQuestion(@RequestBody Questions que ,@RequestHeader(value = "Authorization", required = false) String authorizationHeader){
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token=authorizationHeader.substring(7);
            if(jwtUtil.isTokenValid(token)){
                List<String> roles=new ArrayList<>(jwtUtil.extractRoles(token));
                if(!roles.get(0).equals("ADMIN") || !roles.get(1).equals("USER")){
                    return ResponseEntity.badRequest().body(Map.of("error","Unauthorized user"));
                }
            }
            else{
                return ResponseEntity.badRequest().body(Map.of("error","Invalid token"));
            }
        }
        else{
            return ResponseEntity.badRequest().body(Map.of("error","token is missing or malformed"));
        }
        Questions question=questionService.findById(que.getQueId());
        question.setQuestionText(que.getQuestionText());
        question.setCorrectAnswer(que.getCorrectAnswer());
        question.setOptionA(que.getOptionA());
        question.setOptionB(que.getOptionB());
        question.setOptionC(que.getOptionC());
        question.setOptionD(que.getOptionD());
        question.setQueType(que.getQueType());

        Questions quizQuestion= questionService.saveQuestion(question);
        if(quizQuestion==null) return ResponseEntity.badRequest().body(Map.of("error","Failed to Update Question"));
        else return ResponseEntity.ok(Map.of("message","question uploaded successfully","data",quizQuestion));
    }


    @DeleteMapping("/questiondelete/{id}")
    public ResponseEntity<?> deleteJavaQuestion(@PathVariable("id") Long id,@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token=authorizationHeader.substring(7);
            if(jwtUtil.isTokenValid(token)){
                List<String> roles=new ArrayList<>(jwtUtil.extractRoles(token));
                if(!roles.get(0).equals("ADMIN") || !roles.get(1).equals("USER")){
                    return ResponseEntity.badRequest().body(Map.of("error","Unauthorized user"));
                }
            }
            else{
                return ResponseEntity.badRequest().body(Map.of("error","Invalid token"));
            }
        }
        else{
            return ResponseEntity.badRequest().body(Map.of("error","token is missing or malformed"));
        }
        if (questionService.existById(id)) {
            Questions question=questionService.findById(id);
            questionService.deleteById(id);
            return ResponseEntity.ok(Map.of("message","question deleted successfully","data",question));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","❌ Question not found"));
        }
    }
}
