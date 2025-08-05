package com.hanz.quiz.utility;


import org.springframework.stereotype.Component;

@Component
public class Utility {
    public String normalize(String text) {
        return text.trim().replaceAll("\\s+", " ")         // multiple spaces to single
                .replaceAll("[^a-zA-Z0-9 ]", "")        // remove punctuation
                .toLowerCase();                         // case insensitive
    }
}
