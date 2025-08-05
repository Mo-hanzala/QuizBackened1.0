package com.hanz.quiz.controller;

import com.hanz.quiz.dto.NoteDTO;
import com.hanz.quiz.entity.Notes;
import com.hanz.quiz.service.NotesService;
import com.hanz.quiz.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/notes")



public class NotesController {

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private NotesService notesService;



    @PostMapping("/save")
    public ResponseEntity<?> saveNote(@RequestBody NoteDTO noteDTO,@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token=authorizationHeader.substring(7);
            if(jwtUtil.isTokenValid(token)){
                List<String> roles=new ArrayList<>(jwtUtil.extractRoles(token));
                if(!roles.get(0).equals("USER") || !roles.get(1).equals("USER")){
                    return ResponseEntity.badRequest().body(Map.of("error","Unauthorized user"));
                }
            }
            else{
                return ResponseEntity.badRequest().body(Map.of("error","Invalid Token"));
            }
        }
        else{
            return ResponseEntity.badRequest().body(Map.of("error","Token is missing or malformed"));
        }
        if(notesService.is_notes_present(noteDTO.getUserName(),noteDTO.getCategory())){

            Notes savedNote = notesService.updateUserNotes(noteDTO.getUserName(), noteDTO.getCategory(),noteDTO.getContent());
            if(savedNote!=null){return ResponseEntity.ok(Map.of("message","notes update successfully","data",savedNote.getContent()));}
            else return ResponseEntity.badRequest().body(Map.of("error","Error in updating process"));
        }
        else{
            Notes savedNote = notesService.saveUserNotes(noteDTO.getUserName(), noteDTO.getCategory(), noteDTO.getContent());
            return ResponseEntity.ok(Map.of("message","notes saved successfully","data",savedNote.getContent()));
        }

    }



    @PostMapping("/getnotes")
    public ResponseEntity<?> getNote(@RequestBody NoteDTO noteDTO,@RequestHeader(value = "Authorization", required = false) String authorizationHeader){
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token=authorizationHeader.substring(7);
            if(jwtUtil.isTokenValid(token)){
                List<String> roles=new ArrayList<>(jwtUtil.extractRoles(token));
                if(!roles.get(0).equals("USER") || !roles.get(1).equals("USER")){
                    return ResponseEntity.badRequest().body(Map.of("error","Unauthorized user"));
                }
            }
            else{
                return ResponseEntity.badRequest().body(Map.of("error","Invalid token"));
            }
        }
        else{
            return ResponseEntity.badRequest().body(Map.of("error","Token is missing or malformed"));
        }
        String content=notesService.find_notes_by_id(noteDTO.getUserName(),noteDTO.getCategory());
        if(content==null) return ResponseEntity.ok(Map.of("message","notes send succesfully","data",""));
        return ResponseEntity.ok(Map.of("message","notes send succesfully","data",content));

    }




}
