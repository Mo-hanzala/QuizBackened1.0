package com.hanz.quiz.service;

import com.hanz.quiz.entity.Notes;
import com.hanz.quiz.entity.NotesId;
import com.hanz.quiz.entity.UserEntity;
import com.hanz.quiz.repo.NotesRepo;
import com.hanz.quiz.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    NotesRepo notesRepo;

    @Autowired
    UserRepo userRepo;


    public boolean is_notes_present(String u_name,String category){
        NotesId notesId=new NotesId(u_name,category);
        Optional<Notes> temp = notesRepo.findById(notesId);
        if(temp.isPresent()) return true;
        else return false;
    }

    public String find_notes_by_id(String u_name,String category){
        NotesId notesId=new NotesId(u_name,category);
        Optional<Notes> temp=notesRepo.findById(notesId);
        if(temp.isPresent()) return temp.get().getContent();
        else return null;
    }

    public Notes saveUserNotes(String u_name,String category,String content){
        UserEntity user= userRepo.findById(u_name)
                .orElseThrow(()->new RuntimeException("user not found"));
        NotesId notesId=new NotesId(u_name,category);





        Notes notes=new Notes();

        notes.setId(notesId);
        notes.setUser(user);
        notes.setContent(content);


        return notesRepo.save(notes);


    }

    public Notes updateUserNotes(String u_name,String category,String content){
        NotesId notesId = new NotesId(u_name,category);
        Optional<Notes> temp=notesRepo.findById(notesId);
        if(temp.isPresent()){
            Notes notes=temp.get();
            notes.setContent(content);
            return notesRepo.save(notes);
        }
        else{
            return null;
        }

    }


}
