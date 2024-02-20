package com.example.algomasterapp;

public class Achievement {

    private String title;
    private String desc;
    private Boolean active;
    //private String imageID;

    public Achievement(String title, String desc, Boolean active) {
        this.title = title;
        this.desc = desc;
        this.active = active;
        //this.imageID = imageID;

    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public Boolean getActive() {
        return active;
    }

    //public String getImageID() {
    //    return imageID;
    //}

}
