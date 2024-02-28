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

public class Lessons extends AppCompatActivity {

    private String module = "";

    private RecyclerView lessonRecView;
    private RecyclerView.Adapter lessonAdapter;
    private RecyclerView.LayoutManager lessonLayoutMan;

    List<LessonItem> lessonList = new ArrayList<LessonItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_Lessons);
        setSupportActionBar(toolbar);

        // get current module
        Intent intent = getIntent();
        module = intent.getStringExtra("module");

        // fill up the lesson item list
        fillLessonList();

        // Set up Rev Feed Recycler View
        lessonRecView = (RecyclerView) findViewById(R.id.recView_lessons);
        lessonRecView.setHasFixedSize(true);

        lessonLayoutMan = new LinearLayoutManager(this);
        lessonRecView.setLayoutManager(lessonLayoutMan);

        lessonAdapter = new RecViewAdapter_Lessons(lessonList, Lessons.this);
        lessonRecView.setAdapter(lessonAdapter);

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
            Intent intent = new Intent(Lessons.this, HomeScreen.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menuItem_Profile){
            Intent intent = new Intent(Lessons.this, UserProfile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillLessonList() {

        DatabaseHelper dbHelper = new DatabaseHelper(Lessons.this);

        // find which module has been selected and load lessons.
        switch (module){
            case "m1":
                lessonList = dbHelper.getLessons(1);
                break;
            case "m2":
                lessonList = dbHelper.getLessons(2);
                break;
            case "m3":
                lessonList = dbHelper.getLessons(3);
                break;
            default:
                lessonList = dbHelper.getLessons(0);
                break;
        }

    }
}