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

public class UserProfile extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(UserProfile.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        // set up personal info
        TextView tv_username = findViewById(R.id.textView_ProfileUsername);
        TextView tv_rank = findViewById(R.id.textView_ProfileRank);
        tv_username.setText(MyApplication.username);
        tv_rank.setText(MyApplication.userRank);

        // set up progress bar
        getCompletionLevel();

        // set up the total achievement counter
        String achvText = Integer.toString(dbHelper.getEarnedAchievementTotal());
        TextView tv_achvCount = findViewById(R.id.textView_ProfileAchieveCount);
        tv_achvCount.setText(achvText);

        // set up the view achievements button
        Button viewAchievementsButton = findViewById(R.id.button_viewAchievements);
        viewAchievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, UserAchievements.class);
                startActivity(intent);
            }
        });
    }

    private void getCompletionLevel() {

        // get all lesson data
        List<LessonItem> lessons = dbHelper.getLessons(0);

        // set bar views
        ProgressBar pb_prog = findViewById(R.id.progressBar_Profile);

        pb_prog.setMax(45);

        // set bar values
        int count = 0;

        for (int i = 0; i < lessons.size(); i++) {

            int score;
            if (lessons.get(i).getQuizScore() < 0) {
                score = 0;
            } else {
                score = lessons.get(i).getQuizScore();
            }

            count = count + score;

        }

        pb_prog.setProgress(count);

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
            Intent intent = new Intent(UserProfile.this, HomeScreen.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menuItem_Profile){
            // already in profile
        }

        return super.onOptionsItemSelected(item);
    }
}