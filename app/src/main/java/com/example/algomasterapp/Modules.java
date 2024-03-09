package com.example.algomasterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class Modules extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(Modules.this);
    private Button mod1;
    private Button mod2;
    private Button mod3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_Modules);
        setSupportActionBar(toolbar);

        // determine module completion level
        ProgressBar pb_mod1 = findViewById(R.id.progressBar_mod1);
        ProgressBar pb_mod2 = findViewById(R.id.progressBar_mod2);
        ProgressBar pb_mod3 = findViewById(R.id.progressBar_mod3);
        getCompletionLevels(pb_mod1, pb_mod2, pb_mod3);

        // Set up module buttons
        mod1 = findViewById(R.id.button_m1);
        mod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff
                Intent intent = new Intent(Modules.this, Lessons.class);
                intent.putExtra("module", "m1");
                startActivity(intent);
            }
        });

        mod2 = findViewById(R.id.button_m2);
        mod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff
                Intent intent = new Intent(Modules.this, Lessons.class);
                intent.putExtra("module", "m2");
                startActivity(intent);
            }
        });

        mod3 = findViewById(R.id.button_m3);
        mod3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff
                Intent intent = new Intent(Modules.this, Lessons.class);
                intent.putExtra("module", "m3");
                startActivity(intent);
            }
        });

        // hide info for non-gamified users
        if (!MyApplication.isGamified){

            pb_mod1.setVisibility(View.INVISIBLE);
            pb_mod2.setVisibility(View.INVISIBLE);
            pb_mod3.setVisibility(View.INVISIBLE);
            TextView tv_1 = findViewById(R.id.textView_Modules1);
            tv_1.setVisibility(View.INVISIBLE);
            TextView tv_2 = findViewById(R.id.textView_Modules2);
            tv_2.setVisibility(View.INVISIBLE);
            TextView tv_3 = findViewById(R.id.textView_Modules3);
            tv_3.setVisibility(View.INVISIBLE);
        }
        else{
            // disable buttons based on user level
            mod2.setEnabled(false);
            mod3.setEnabled(false);

            if (MyApplication.userRank.equals("Expert")){
                mod2.setEnabled(true);
            }
            if(MyApplication.userRank.equals("Master")){
                mod3.setEnabled(true);
            }
        }
    }

    private void getCompletionLevels(ProgressBar pb_mod1, ProgressBar pb_mod2, ProgressBar pb_mod3) {

        // get all lesson data
        List<LessonItem> lessons = dbHelper.getLessons(0);
        int lessonsPassed = 0;
        int perfectLessons = 0;

        // set bar views
        pb_mod1.setMax(18);
        pb_mod2.setMax(18);
        pb_mod3.setMax(9);

        // set bar values
        int bar1 = 0, bar2 = 0, bar3 = 0;
        int count = 0;

        for (int i = 0; i < lessons.size(); i++){

            // get score for current lesson
            int score;
            if (lessons.get(i).getQuizScore() < 0){
                score = 0;
            }
            else{
                score = lessons.get(i).getQuizScore();
            }

            // note if current lesson is passed
            if (lessons.get(i).getIsComplete()){
                lessonsPassed++;
                // if lesson is perfect add one to perfect lessons
                if (lessons.get(i).getQuizScore() == 3){
                    perfectLessons++;
                }
            }

            // add current score to score count
            count = count + score;

            // apply score to prog bar
            switch (i){
                case 5:
                    bar1 = count;
                    count = 0;
                    // check for level up
                    if (lessonsPassed == 6){
                        // level up
                        dbHelper.updateUserRank(MyApplication.userID, 2);
                        // gain achievement
                        dbHelper.AchievementEarned(1, Modules.this);
                    }
                    break;
                case 11:
                    bar2 = count;
                    count = 0;
                    if (lessonsPassed == 12){
                        // level up
                        dbHelper.updateUserRank(MyApplication.userID, 3);
                        // gain achievement
                        dbHelper.AchievementEarned(2, Modules.this);
                    }
                    break;
                case 14:
                    bar3 = count;
                    count = 0;
                    break;
            }
        }

        // apply bar values to bar views
        pb_mod1.setProgress(bar1, true);
        pb_mod2.setProgress(bar2, true);
        pb_mod3.setProgress(bar3, true);

        // check for perfect module achievements
        detectPerfectModules(pb_mod1, pb_mod2, pb_mod3);

        // update perfect quiz scores in db
        dbHelper.updatePerfectTotal(MyApplication.userID, perfectLessons);

        // update total lessons passed in db
        dbHelper.updateLessonsPassedTotal(MyApplication.userID, lessonsPassed);
    }

    private void detectPerfectModules(ProgressBar mod1, ProgressBar mod2, ProgressBar mod3){

        if (mod1.getProgress() == mod1.getMax()){
            dbHelper.AchievementEarned(3, Modules.this);
        }

        if (mod2.getProgress() == mod2.getMax()){
            dbHelper.AchievementEarned(4, Modules.this);
        }

        if (mod3.getProgress() == mod3.getMax()){
            dbHelper.AchievementEarned(5, Modules.this);
        }
    }

    // bind the menu to the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_header, menu);
        return true;
    }

    // set button click operations
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuItem_Home){
            Intent intent = new Intent(Modules.this, HomeScreen.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menuItem_Profile){
            Intent intent = new Intent(Modules.this, UserProfile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}