package com.hanz.quiz.repo;

import com.hanz.quiz.entity.Questions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionsRepo extends JpaRepository<Questions,Long> {
    List<Questions> findByQueType(String queType);
}
