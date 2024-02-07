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

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        Button viewAchievementsButton = findViewById(R.id.button_viewAchievements);
        viewAchievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, UserAchievements.class);
                startActivity(intent);
            }
        });
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