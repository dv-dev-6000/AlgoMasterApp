package com.example.algomasterapp;

public class QuizItem {

    private float id;
    private String title;
    private String question;
    private String imageFile;
    private String[] options;
    private int answer;

    public QuizItem(float id, String title, String question, String imageFile, String[] options, int answer) {
        this.id = id;
        this.title = title;
        this.question = question;
        this.imageFile = imageFile; // "null" if no image
        this.options = options;
        this.answer = answer;
    }

    public float getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }

    public String getImageFile() {
        return imageFile;
    }

    public String[] getOptions() {
        return options;
    }

    public int getAnswer() {
        return answer;
    }
}
