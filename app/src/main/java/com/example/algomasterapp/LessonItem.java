package com.example.algomasterapp;

public class LessonItem {

    private int id;
    private String title;
    private String description;
    private boolean isComplete;
    private int clickedCount;
    private int quizMax;
    private int quizScore;

    public LessonItem(int id, int clickedCount, boolean isComplete, String title, String description, int quizMax, int quizScore) {
        this.id = id;
        this.clickedCount = clickedCount;
        this.isComplete = isComplete;
        this.title = title;
        this.description = description;
        this.quizMax = quizMax;
        this.quizScore = quizScore;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public int getClickedCount() {
        return clickedCount;
    }

    public int getQuizMax() {
        return quizMax;
    }

    public int getQuizScore() {
        return quizScore;
    }
}
