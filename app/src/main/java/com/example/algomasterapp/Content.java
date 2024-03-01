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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class Content extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(Content.this);
    int lessonID;
    private Button quizBut;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_Content);
        setSupportActionBar(toolbar);

        tv1 = findViewById(R.id.textView_Lesson1);
        tv2 = findViewById(R.id.textView_Lesson2);
        tv3 = findViewById(R.id.textView_Lesson3);
        tv4 = findViewById(R.id.textView_Lesson4);

        iv1 = findViewById(R.id.imageView_Lesson1);
        iv2 = findViewById(R.id.imageView_Lesson2);
        iv3 = findViewById(R.id.imageView_Lesson3);

        quizBut = findViewById(R.id.button_LessonComplete);

        // get current module
        Intent intent = getIntent();
        lessonID = intent.getIntExtra("id", 0);

        // check for studious achievement
        if (dbHelper.isLessonComplete(lessonID)){
            dbHelper.AchievementEarned(10, Content.this);
        }

        // get the lesson content
        getLessonContent(lessonID);
        dbHelper.UpdateLessonClicked(lessonID);

        // set up quiz button
        quizBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content.this, Quiz.class);
                intent.putExtra("lessonID", lessonID);
                startActivity(intent);
            }
        });
    }

    private void getLessonContent(int lessonID) {

        CSV_Helper csvHelper = new CSV_Helper(getResources().openRawResource(R.raw.lessondata));
        String[] currentLesson = csvHelper.GetLine(lessonID);

        tv1.setText(currentLesson[0]);
        setImage(iv1, currentLesson[1]);
        tv2.setText(currentLesson[2]);
        setImage(iv2, currentLesson[3]);
        tv3.setText(currentLesson[4]);
        setImage(iv3, currentLesson[5]);
        tv4.setText(currentLesson[6]);
    }

    private void setImage(ImageView iv, String filename){

        if (!Objects.equals(filename, "null")){
            int resID = getResources().getIdentifier(filename, "drawable", getPackageName());;

            iv.setImageResource(resID);
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
            Intent intent = new Intent(Content.this, HomeScreen.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menuItem_Profile){
            Intent intent = new Intent(Content.this, UserProfile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}