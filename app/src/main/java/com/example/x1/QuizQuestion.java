package com.example.x1;

import java.util.List;


public class QuizQuestion {


    private String imageUrl;
    private String correctName;
    private List<String> options;

    public QuizQuestion(String imageUrl, String correctName, List<String> options) {
        this.imageUrl = imageUrl;
        this.correctName = correctName;
        this.options = options;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCorrectName() {
        return correctName;
    }

    public void setCorrectName(String correctName) {
        this.correctName = correctName;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
