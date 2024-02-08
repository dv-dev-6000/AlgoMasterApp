package com.example.algomasterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_quiz);
        setSupportActionBar(toolbar);
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
            Intent intent = new Intent(Quiz.this, HomeScreen.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menuItem_Profile){
            Intent intent = new Intent(Quiz.this, UserProfile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}