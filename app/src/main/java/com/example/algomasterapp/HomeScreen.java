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
        ProgressBar pb_prog = findViewById(R.id.progressBar_Home);
        getCompletionLevel(pb_prog);

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
                dbHelper.updateUserRank(MyApplication.userID, 1);
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

        // hide info for non-gamified users
        if (!MyApplication.isGamified){
            tv_Rank.setVisibility(View.INVISIBLE);
            pb_prog.setVisibility(View.INVISIBLE);
            TextView tv_1 = findViewById(R.id.textView_Home1);
            tv_1.setText("Click Learn \nto Begin >>");
            TextView tv_2 = findViewById(R.id.textView_Home2);
            tv_2.setVisibility(View.INVISIBLE);
        }
    }

    private void getCompletionLevel(ProgressBar pb_prog) {

        // get all lesson data
        List<LessonItem> lessons = dbHelper.getLessons(0);

        // set bar views
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
        RevisionItem test1 = new RevisionItem("Hello!", "Welcome to the AlgoMaster App. To help you get started, we've added some tips on how to use the app to the welcome feed. \n\nPlease have a scroll to see what features are available in your version.", false, "");
        RevisionItem test2 = new RevisionItem("Learn!", "Click the learn button above to jump straight to the lessons and get learning.", false, "");
        RevisionItem test3 = new RevisionItem("Modules!", "The prototype contains three modules for you to work through, each module contains a number of short lessons on topics related to algorithms and data structures", false, "");
        RevisionItem test4 = new RevisionItem("Quiz Time!", "Each lesson has a short quiz which consists of 3 questions, answer 2 correctly to pass or all 3 for a perfect score.", false, "");
        RevisionItem test5 = new RevisionItem("Gamification!", "If you can see your rank and progress bar on this screen then your using the gamified version. \n\nBe sure to check in on your achievements from time to time and monitor your progress to unlock more content.", false, "");
        RevisionItem test6 = new RevisionItem("No Visible Rank?", "if you cant see your rank on this page, then your using the regular version. \n\nEnjoy unrestricted access to all module content from the start", false, "");
        RevisionItem test7 = new RevisionItem("Achievements!", "Use the dropdown menu (top right) to visit your user profile. \n\nFrom here, gamified users can can track their overall progress and view their Achievements. \n\nYou can also get info on how to unlock any achievements you still need to earn.", false, "");
        RevisionItem test8 = new RevisionItem("Progress!", "Gamified users can also track their progress. \n\nComplete lesson quiz's to level up your rank, as your rank increases, you can unlock access to more advanced modules.", true, "");
        RevisionItem test9 = new RevisionItem("Finishing Up!", "Once your finished trialing the app please remember to fill out the user survey linked in the sign up sheet. \n\nYou'll be asked for your user data sting so please take a note of this before you uninstall the app, you can get this using the button in your user profile. \n\nThe survey and user data are critical to supporting the research. Please do complete the survey, the data generated will be useful even if you have not used the application often. \n\nThanks", false, "");

        revItemList.add(test1);
        revItemList.add(test2);
        revItemList.add(test3);
        revItemList.add(test4);
        revItemList.add(test5);
        revItemList.add(test6);
        revItemList.add(test7);
        revItemList.add(test8);
        revItemList.add(test9);
    }

    private void InitialDBSetup() {

        dbHelper = new DatabaseHelper(HomeScreen.this);

        if (dbHelper.Get_IsEmpty("ACHIEVEMENT_PROGRESS", -1)){
            //Toast toast = Toast.makeText(HomeScreen.this, "Loading Achievements", Toast.LENGTH_SHORT);
            //toast.show();

            if (dbHelper.addAchievements()){
                //toast = Toast.makeText(HomeScreen.this, "Update Success", Toast.LENGTH_SHORT);
                //toast.show();
            }
        }

        if (dbHelper.Get_IsEmpty("LESSON_PROGRESS", -1)){
            //Toast toast = Toast.makeText(HomeScreen.this, "Loading Lessons", Toast.LENGTH_SHORT);
            //toast.show();

            if (dbHelper.addLessons()){
                //toast = Toast.makeText(HomeScreen.this, "Update Success", Toast.LENGTH_SHORT);
                //toast.show();
            }
        }

        // get user id from global space
        if (dbHelper.Get_IsEmpty("GENERAL_STATS", MyApplication.userID)){
            //Toast toast = Toast.makeText(HomeScreen.this, "Stats Empty", Toast.LENGTH_SHORT);
            //toast.show();

            // if there is no user record for the user id then create one
            if (dbHelper.addNewUserRecord(MyApplication.userID)){
                //toast = Toast.makeText(HomeScreen.this, "Update Success", Toast.LENGTH_SHORT);
                //toast.show();
            }
        }
        else{
            // if a record exists for the user update db with a new login and log date then check for streak or 24 hr return
            dbHelper.updateLoginTotal(MyApplication.userID, HomeScreen.this);
        }

    }

}