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
        getCompletionLevels();

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
                dbHelper.AchievementEarned(1, Modules.this);
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
                dbHelper.AchievementEarned(2, Modules.this);
                Intent intent = new Intent(Modules.this, Lessons.class);
                intent.putExtra("module", "m3");
                startActivity(intent);
            }
        });
    }

    private void getCompletionLevels() {

        // get all lesson data
        List<LessonItem> lessons = dbHelper.getLessons(0);

        // set bar views
        ProgressBar pb_mod1 = findViewById(R.id.progressBar_mod1);
        ProgressBar pb_mod2 = findViewById(R.id.progressBar_mod2);
        ProgressBar pb_mod3 = findViewById(R.id.progressBar_mod3);
        pb_mod1.setMax(18);
        pb_mod2.setMax(18);
        pb_mod3.setMax(9);

        // set bar values
        int bar1 = 0, bar2 = 0, bar3 = 0;
        int count = 0;

        for (int i = 0; i < lessons.size(); i++){

            int score;
            if (lessons.get(i).getQuizScore() < 0){
                score = 0;
            }
            else{score = lessons.get(i).getQuizScore(); }

            count = count + score;

            switch (i){
                case 5:
                    bar1 = count;
                    count = 0;
                    break;
                case 11:
                    bar2 = count;
                    count = 0;
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

        detectPerfectModules(pb_mod1, pb_mod2, pb_mod3);
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