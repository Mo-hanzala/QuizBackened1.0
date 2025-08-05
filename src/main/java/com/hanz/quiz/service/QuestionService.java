package com.hanz.quiz.service;

import com.hanz.quiz.entity.Questions;
import com.hanz.quiz.repo.QuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionsRepo questionsRepo;

    public List<Questions> getAllQuestions()
    {
        List<Questions> questionRepoAll = questionsRepo.findAll();
        return questionRepoAll;
    }

    public List<Questions> getAllQuestionsByType(String queType)
    {
        List<Questions> questionRepoAll = questionsRepo.findByQueType(queType);
        return questionRepoAll;
    }

    public Questions saveQuestion(Questions question){
        Questions save = questionsRepo.save(question);
        return save;
    }

    public Questions findById(Long id){
        Optional<Questions> que=questionsRepo.findById(id);
        if(que.isPresent()) return que.get();
        else return null;
    }

    public boolean existById(Long id){
        if(questionsRepo.existsById(id)) return true;
        else return false;
    }

    public void deleteById(Long id){
        questionsRepo.deleteById(id);
    }

}
