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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserAchievements extends AppCompatActivity {

    private RecyclerView achieveRecView;
    private RecyclerView.Adapter achieveAdapter;
    private RecyclerView.LayoutManager achieveLayoutMan;

    List<Achievement> achievementsList = new ArrayList<Achievement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_achievements);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_Achieve);
        setSupportActionBar(toolbar);

        // fill up the revision item list
        fillAchievementsList();

        // Set up Rev Feed Recycler View
        achieveRecView = (RecyclerView) findViewById(R.id.recView_Achievements);
        achieveRecView.setHasFixedSize(true);

        achieveLayoutMan = new LinearLayoutManager(this);
        achieveRecView.setLayoutManager(achieveLayoutMan);

        achieveAdapter = new RecViewAdapter_Achievements(achievementsList, UserAchievements.this);
        achieveRecView.setAdapter(achieveAdapter);
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
            Intent intent = new Intent(UserAchievements.this, HomeScreen.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menuItem_Profile){
            Intent intent = new Intent(UserAchievements.this, UserProfile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillAchievementsList() {

        // use a global list or info from db
        achievementsList.add(new Achievement("A1", "Text Description", true, "stack"));
        achievementsList.add(new Achievement("A2", "Text Description", false, "stack"));
        achievementsList.add(new Achievement("A3", "Text Description", false, "stack"));
        achievementsList.add(new Achievement("A4", "Text Description", true, "stack"));
        achievementsList.add(new Achievement("A5", "Text Description", false, "stack"));
        achievementsList.add(new Achievement("A6", "Text Description", false, "stack"));
        achievementsList.add(new Achievement("A7", "Text Description", true, "stack"));
        achievementsList.add(new Achievement("A8", "Text Description", false, "stack"));
        achievementsList.add(new Achievement("A9", "Text Description", false, "stack"));
        achievementsList.add(new Achievement("A10", "Text Description", true, "stack"));
        achievementsList.add(new Achievement("A11", "Text Description", false, "stack"));
        achievementsList.add(new Achievement("A12", "Text Description", false, "stack"));
        achievementsList.add(new Achievement("A13", "Text Description", true, "stack"));
        achievementsList.add(new Achievement("A14", "Text Description", false, "stack"));
        achievementsList.add(new Achievement("A15", "Text Description", false, "stack"));

        Toast toast = Toast.makeText(UserAchievements.this, "Hello!", Toast.LENGTH_SHORT);
        toast.show();
    }
}