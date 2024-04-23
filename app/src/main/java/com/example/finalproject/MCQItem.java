package com.example.finalproject;

import java.util.Arrays;

public class MCQItem {
    private String question;
    private String[] options;

    public MCQItem(String question, String[] options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }
}

