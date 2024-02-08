package com.example.algomasterapp;

public class RevisionItem {
    private String title;
    private String main;
    private Boolean hasImage;
    private String imageID;

    public RevisionItem(String title, String main, Boolean hasImage, String imageID) {
        this.title = title;
        this.main = main;
        this.hasImage = hasImage;

        if (this.hasImage){
            this.imageID = imageID;
        }
        else{ this.imageID = "";}

    }

    public String getTitle() {
        return title;
    }

    public String getMain() {
        return main;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public String getImageID() {
        return imageID;
    }
}
