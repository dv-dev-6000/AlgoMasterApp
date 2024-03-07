package com.example.algomasterapp;

import android.app.Application;

public class MyApplication extends Application {

    // Global Vars
    public static String username = "DefaultUser";
    public static String userRank = "Test";
    public static int userID = 0;
    public static boolean isGamified;

    public void LogIn(String name){

        username = name;
        String nums = name.split("r")[1];
        userID = Integer.parseInt(nums);

        isGamified = userID % 2 == 0;
    }

    public void setUserRank(int uRank){
        switch(uRank){
            case 0:
                userRank = "Novice";
                break;
            case 1:
                userRank = "Adept";
                break;
            case 2:
                userRank = "Expert";
                break;
            case 3:
                userRank = "Master";
                break;
        }
    }

}
