package com.example.algomasterapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(Quiz.this);
    private int lesson;
    private Button subButton;
    private RecyclerView quizRecView;
    private RecViewAdapter_Quiz quizAdapter;
    private RecyclerView.LayoutManager quizLayoutMan;

    List<QuizItem> questionList = new ArrayList<QuizItem>();
    int[] answerList = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar_quiz);
        setSupportActionBar(toolbar);

        // get current lesson
        Intent intent = getIntent();
        lesson = intent.getIntExtra("lessonID", 0);

        // fill up the revision item list
        fillQuestionList();

        // Set up Rev Feed Recycler View
        quizRecView = (RecyclerView) findViewById(R.id.RecView_QuizFeed);
        quizRecView.setHasFixedSize(true);

        quizLayoutMan = new LinearLayoutManager(this);
        quizRecView.setLayoutManager(quizLayoutMan);

        quizAdapter = new RecViewAdapter_Quiz(questionList, Quiz.this);
        quizRecView.setAdapter(quizAdapter);

        subButton = findViewById(R.id.button_QuizSubmit);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set up vars
                String message;
                int score = 0;

                // Calculate score
                for (int i = 0; i < 3; i++) {
                     if (questionList.get(i).getAnswer() == answerList[i]){
                         score++;
                     }
                }
                int finalScore = score;

                // check total score
                if (score > 1){
                    message = "Congratulations! you passed this lesson quiz. You scored " + score + "/3";
                }
                else{
                    message = "Sorry you didn't pass this time. You scored " + score + "/3";
                }
                // show score pop up
                AlertDialog.Builder quizResultsAlert = new AlertDialog.Builder(Quiz.this);
                quizResultsAlert.setTitle("Quiz Complete!");
                quizResultsAlert.setMessage(message);
                quizResultsAlert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Update DB
                        dbHelper.UpdateQuizScore(lesson, finalScore);

                        // back to modules
                        Intent intent = new Intent(Quiz.this, Modules.class);
                        startActivity(intent);
                    }
                });
                quizResultsAlert.create().show();

            }
        });

        quizAdapter.setRadioGroupClickListener((position, checkID) -> {

            // identify checked button
            int selection = Integer.parseInt(findViewById(checkID).getTag().toString());

            Toast toast = Toast.makeText(Quiz.this, "Q:" + position + " A:"+ selection, Toast.LENGTH_SHORT);
            toast.show();

            answerList[position] = selection;
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
            Intent intent = new Intent(Quiz.this, HomeScreen.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menuItem_Profile){
            Intent intent = new Intent(Quiz.this, UserProfile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillQuestionList() {

        // find which lesson has been selected and load questions.
        switch (lesson){
            case 0:
                questionList.add(new QuizItem(0.1f, "Q1.1", "What am I?", "stack", new String[]{"op1","op2","op3","op4"}, 1));
                questionList.add(new QuizItem(0.2f, "Q1.2", "Who am I?", "null", new String[]{"op1","op2","op3","op4"}, 2));
                questionList.add(new QuizItem(0.3f, "Q1.3", "Where am I?", "test", new String[]{"op1","op2","op3","op4"}, 3));
                break;
            case 1:
                questionList.add(new QuizItem(1.1f, "Q2.1", "What am I?", "stack", new String[]{"op1","op2","op3","op4"}, 1));
                questionList.add(new QuizItem(1.2f, "Q2.2", "Who am I?", "null", new String[]{"op1","op2","op3","op4"}, 2));
                questionList.add(new QuizItem(1.3f, "Q2.3", "Where am I?", "test", new String[]{"op1","op2","op3","op4"}, 3));
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            case 11:

                break;
            case 12:

                break;
            case 13:

                break;
            case 14:

                break;
            default:

                break;
        }
    }
}