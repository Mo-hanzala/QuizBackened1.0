package com.hanz.quiz.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NotesId implements Serializable {

    private String uName;
    private String category;

    public NotesId() {
    }

    public NotesId(String uName, String category) {
        this.uName = uName;
        this.category = category;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotesId)) return false;
        NotesId notesId = (NotesId) o;
        return Objects.equals(uName, notesId.uName) &&
                Objects.equals(category, notesId.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uName, category);
    }
}
