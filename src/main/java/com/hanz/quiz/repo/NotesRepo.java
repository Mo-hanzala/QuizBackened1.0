package com.hanz.quiz.repo;

import com.hanz.quiz.entity.Notes;
import com.hanz.quiz.entity.NotesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepo extends JpaRepository<Notes, NotesId> {

}
