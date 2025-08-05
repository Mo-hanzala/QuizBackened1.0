package com.hanz.quiz.entity;

import jakarta.persistence.*;

@Entity
public class Notes {

    @EmbeddedId
    private NotesId id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @MapsId("uName") // maps uName from composite key to actual User reference
    @ManyToOne
    @JoinColumn(name = "u_name", referencedColumnName = "u_name")
    private UserEntity user;



    public NotesId getId() {
        return id;
    }

    public void setId(NotesId id) {
        this.id = id;
    }

    public String getCategory() {
        return id != null ? id.getCategory() : null;
    }

    public void setCategory(String category) {
        if (this.id == null) {
            this.id = new NotesId();
        }
        this.id.setCategory(category);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
        if (this.id == null) {
            this.id = new NotesId();
        }
        this.id.setuName(user.getUserName());
    }
}

