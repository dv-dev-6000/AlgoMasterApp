package com.example.algomasterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    DatabaseHelper dbHelper;

    private Button learnButton;
    private RecyclerView revFeedRecView;
    private RecyclerView.Adapter revFeedAdapter;
    private RecyclerView.LayoutManager revFeedLayoutMan;

    List<RevisionItem> revItemList = new ArrayList<RevisionItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // create Local Database instance
        InitialDBSetup();

        // fill up the revision item list
        fillRevItemList();

        // set up prog bar
        getCompletionLevel();

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        // display username/rank
        TextView tv_Rank = findViewById(R.id.textView_homeRank);
        tv_Rank.setText(MyApplication.userRank);



        learnButton = findViewById(R.id.button_Learn);
        learnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper.AchievementEarned(0, HomeScreen.this );

                // do stuff
                Intent intent = new Intent(HomeScreen.this, Modules.class);
                startActivity(intent);
            }
        });

        // Set up Rev Feed Recycler View
        revFeedRecView = (RecyclerView) findViewById(R.id.RecView_RevisionFeed);
        revFeedRecView.setHasFixedSize(true);

        revFeedLayoutMan = new LinearLayoutManager(this);
        revFeedRecView.setLayoutManager(revFeedLayoutMan);

        revFeedAdapter = new RecViewAdapter_RevFeed(revItemList, HomeScreen.this);
        revFeedRecView.setAdapter(revFeedAdapter);

    }

    private void getCompletionLevel() {

        // get all lesson data
        List<LessonItem> lessons = dbHelper.getLessons(0);

        // set bar views
        ProgressBar pb_prog = findViewById(R.id.progressBar_Home);

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
            // already home
        }
        else if (item.getItemId() == R.id.menuItem_Profile){
            Intent intent = new Intent(HomeScreen.this, UserProfile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillRevItemList() {
        RevisionItem test1 = new RevisionItem("Did You Know?", "the revision feed contains helpful tips and reminders reflecting on modules you have completed", false, "");
        RevisionItem test2 = new RevisionItem("Remember!", "Complete all the lessons in a module to unlock new content", true, "test");
        RevisionItem test3 = new RevisionItem("Remember!", "Check your profile to review your achievements", false, "");
        RevisionItem test4 = new RevisionItem("Remember!", "Check \n your profile to \n review your achievements", false, "");
        RevisionItem test5 = new RevisionItem("Remember!", "Check \nyour profile \nto review \nyour achievements", true, "stack");
        RevisionItem test6 = new RevisionItem("Remember!", "Check your profile to review your achievements", false, "");

        revItemList.add(test1);
        revItemList.add(test2);
        revItemList.add(test3);
        revItemList.add(test4);
        revItemList.add(test5);
        revItemList.add(test6);
    }

    private void InitialDBSetup() {

        dbHelper = new DatabaseHelper(HomeScreen.this);

        if (dbHelper.Get_IsEmpty("ACHIEVEMENT_PROGRESS", -1)){
            Toast toast = Toast.makeText(HomeScreen.this, "Loading Achievements", Toast.LENGTH_SHORT);
            toast.show();

            if (dbHelper.addAchievements()){
                toast = Toast.makeText(HomeScreen.this, "Update Success", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if (dbHelper.Get_IsEmpty("LESSON_PROGRESS", -1)){
            Toast toast = Toast.makeText(HomeScreen.this, "Loading Lessons", Toast.LENGTH_SHORT);
            toast.show();

            if (dbHelper.addLessons()){
                toast = Toast.makeText(HomeScreen.this, "Update Success", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        // get user id from global space
        if (dbHelper.Get_IsEmpty("GENERAL_STATS", MyApplication.userID)){
            Toast toast = Toast.makeText(HomeScreen.this, "Stats Empty", Toast.LENGTH_SHORT);
            toast.show();

            // if there is no user record for the user id then create one
            if (dbHelper.addNewUserRecord(MyApplication.userID)){
                toast = Toast.makeText(HomeScreen.this, "Update Success", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            // if a record exists for the user update db with a new login and log date then check for streak or 24 hr return
            dbHelper.updateLoginTotal(MyApplication.userID, HomeScreen.this);
        }

    }

}