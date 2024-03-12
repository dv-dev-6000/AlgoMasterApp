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
                        dbHelper.UpdateQuizScore(lesson, finalScore, Quiz.this);

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

            //Toast toast = Toast.makeText(Quiz.this, "Q:" + position + " A:"+ selection, Toast.LENGTH_SHORT);
            //toast.show();

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
                questionList.add(new QuizItem(0.1f, "Q1.1", "A homogeneous data structure means all data must:", "null", new String[]{"Be the same type.","Be stored consecutively in memory.","Have the same value","Be of various any types"}, 1));
                questionList.add(new QuizItem(0.2f, "Q1.2", "Which of the following options is not a real Data Structure?", "null", new String[]{"Stack","Tree","Trunk","Queue"}, 3));
                questionList.add(new QuizItem(0.3f, "Q1.3", "Push is a common operation supported by various data structures, what does it do?", "null", new String[]{"Removes an item","Adds a new item","Shifts an items position up","Shifts an items position down"}, 2));
                break;
            case 1:
                questionList.add(new QuizItem(1.1f, "Q2.1", "The following integers are added to a stack in order [1,3,6,4]. Which value is returned when the pop operation is called?", "null", new String[]{"1","2","3","4"}, 4));
                questionList.add(new QuizItem(1.2f, "Q2.2", "The peek operation is called on a stack containing 6 integers. How many integers does the stack contain after the operation?", "null", new String[]{"5","6","7","0"}, 2));
                questionList.add(new QuizItem(1.3f, "Q2.3", "The underflow condition is triggered when? ", "null", new String[]{"Pop called on full stack","Push called on full stack","Pop called on empty stack","Push called on empty stack"}, 3));
                break;
            case 2:
                questionList.add(new QuizItem(2.1f, "Q3.1", "Which of the following acronyms represents the principal by which data is accessed in a queue data structure?", "null", new String[]{"FILO","FIFO","LIFO","YOLO"}, 2));
                questionList.add(new QuizItem(2.2f, "Q3.2", "The tail pointer in a queue, points to what?", "null", new String[]{"The first element","The last element","The next empty element to fill","None of the above "}, 3));
                questionList.add(new QuizItem(2.3f, "Q3.3", "The integers [3,1,6,8,5] are added in order to a queue data structure, which integer is returned when the dequeue operation is called?", "null", new String[]{"3","5","6","None of the above"}, 1));
                break;
            case 3:
                questionList.add(new QuizItem(3.1f, "Q4.1", "What is the main advantage the circular queue structure has over a regular queue?", "null", new String[]{"Larger capacity","Easier to implement","Faster","More memory efficient"}, 4));
                questionList.add(new QuizItem(3.2f, "Q4.2", "The enqueue operation is called on a circular queue with a declared size of 5, The tail node currently points to index 4. At which index will the tail node point after the operation has been performed?", "null", new String[]{"5","0","1","4"}, 2));
                questionList.add(new QuizItem(3.3f, "Q4.3", "Which of the following is not a common use case for the queue structure?", "null", new String[]{"Backtracking","Scheduling","Traffic Control Systems","Network Protocols"}, 1));
                break;
            case 4:
                questionList.add(new QuizItem(4.1f, "Q5.1", "Each node in a standard linked list contains two data fields. One contains the data to be stored, what is contained in the other?", "null", new String[]{"The nodes memory address","Int representing index position","Next nodes memory address","Data type"}, 3));
                questionList.add(new QuizItem(4.2f, "Q5.2", "Dynamic memory allocation is one of the main advantages of linked lists over arrays. What benefit does this allow?", "null", new String[]{"List can store data of different types","The size does not need to be defined","-","-"}, 2));
                questionList.add(new QuizItem(4.3f, "Q5.3", "A linked list of strings contains the following 3 nodes: \n\nNODE 1: [string=“hello”, next=123] \nNODE 2: [string=“delete”, next=124] \nNODE 3: [string=“world”, next =125]\n\nIf NODE 2 is removed, what will the be the value of next for NODE 1?", "null", new String[]{"122","123","124","125"}, 3));
                break;
            case 5:
                questionList.add(new QuizItem(5.1f, "Q6.1", "You want a data structure to store a fixed amount of data and memory efficiency is a high priority. \n\nChoose the most suitable data structure?", "null", new String[]{"Linked List", "Array","-","-"}, 2));
                questionList.add(new QuizItem(5.2f, "Q6.2", "You want a controlled access data structure to handle incoming data to your system, the volume of data incoming is not known, and data must be processed by the system in the order it arrived. \n\nChoose the most suitable data structure?", "null", new String[]{"List based stack","Array based stack","List based queue","Array based queue"}, 3));
                questionList.add(new QuizItem(5.3f, "Q6.3", "True or false? Insertions and deletions are more computationally expensive in arrays when compared to linked lists. ", "null", new String[]{"True","False","-","-"}, 1));
                break;
                //----------------------------------------------------------------------------------
            case 6:
                questionList.add(new QuizItem(6.1f, "Q1.1", "True or false? Linear search works only on sorted lists.", "null", new String[]{"True","False","-","-"}, 2));
                questionList.add(new QuizItem(6.2f, "Q1.2", "True or false? Linear search is an in-place search algorithm meaning it doesn’t require extra memory to operate.", "null", new String[]{"True","False","-","-"}, 1));
                questionList.add(new QuizItem(6.3f, "Q1.3", "In which of the following scenarios would linear search be a good choice?", "null", new String[]{"When the list is large","When the list is sorted","When the list cannot be sorted","None of the above"}, 3));
                break;
            case 7:
                questionList.add(new QuizItem(7.1f, "Q2.1", "True or false? Binary search works only on sorted lists.", "null", new String[]{"True","False","-","-"}, 1));
                questionList.add(new QuizItem(7.2f, "Q2.2", "How many iterations would it take for binary search to find the integer value 3 in the following ordered list \n\n[1,3,4,6,8,9,11]", "null", new String[]{"7","4","2","1"}, 3));
                questionList.add(new QuizItem(7.3f, "Q2.3", "The time complexity for binary search is?", "null", new String[]{"O(1)","O(log n)","O(n)","O(n^2)"}, 2));
                break;
            case 8:
                questionList.add(new QuizItem(8.1f, "Q3.1", "Which of the following is NOT a suitable use case for the bubble sort algorithm? ", "null", new String[]{"Small lists","Large lists","Nearly sorted lists","When memory is limited"}, 2));
                questionList.add(new QuizItem(8.2f, "Q3.2", "True or false? Bubble sort is an in-place sorting algorithm meaning it doesn’t require extra memory to operate.", "null", new String[]{"True","False","-","-"}, 1));
                questionList.add(new QuizItem(8.3f, "Q3.3", "The time complexity for bubble sort is?", "null", new String[]{"O(1)","O(log n)","O(n)","O(n^2)"}, 4));
                break;
            case 9:
                questionList.add(new QuizItem(9.1f, "Q4.1", "Which of the following statements about Insertion Sort is true?", "null", new String[]{"It works well for large datasets.","It uses divide-and-conquer strategy.","It is an in-place sorting algorithm.","None of the above"}, 3));
                questionList.add(new QuizItem(9.2f, "Q4.2", "In Insertion Sort, how many elements are compared in the worst-case scenario when inserting an element into its correct position?", "null", new String[]{"One element","All elements in the sorted portion","All elements in the unsorted portion","Half of the elements"}, 2));
                questionList.add(new QuizItem(9.3f, "Q4.3", "In Insertion Sort, how many swaps are typically required to sort an array with n elements?", "null", new String[]{"It depends on the input data","Exactly n swaps","At least n swaps","At most n swaps"}, 3));
                break;
            case 10:
                questionList.add(new QuizItem(10.1f, "Q5.1", "The time complexity in all cases for selection sort is?", "null", new String[]{"O(1)","O(log n)","O(n)","O(n^2)"}, 4));
                questionList.add(new QuizItem(10.2f, "Q5.2", "In which of the following scenarios is Selection Sort most suitable?", "null", new String[]{"Sorting a small array with mostly sorted elements","Sorting a large array with random elements","Sorting a linked list","Sorting an array with duplicate values"}, 1));
                questionList.add(new QuizItem(10.3f, "Q5.3", "True or false? Selection sort is a comparison-based sorting algorithm", "null", new String[]{"True","False","-","-"}, 3));
                break;
            case 11:
                questionList.add(new QuizItem(11.1f, "Q6.1", "The time complexity in all cases for selection sort is?", "null", new String[]{"O(log n)", "O(n log n)","O(n)","O(n^2)"}, 2));
                questionList.add(new QuizItem(11.2f, "Q6.2", "During the division step, what is the total number of sub-arrays the data set be split into before the merge step begins?", "null", new String[]{"One sub-array","Two sub-arrays","n/2 sub-arrays","n sub-arrays"}, 4));
                questionList.add(new QuizItem(11.3f, "Q6.3", "During the merge step, what is the approximate number of merges required to achieve a sorted list?", "null", new String[]{"1 merge","log₂(n) merges","n merges","n² merges"}, 2));
                break;
                //----------------------------------------------------------------------------------
            case 12:
                questionList.add(new QuizItem(12.1f, "Q1.1", "Which of the following statements about algorithmic complexity is correct?\n\nA. Time complexity is a measure of the amount of memory an algorithm needs to run to completion.\n\nB. Space complexity includes both the space used by the input and the auxiliary space, which is the extra space or the temporary space used by the algorithm during its execution.\n\nC. In Big O notation, O(n) represents the best-case scenario of time complexity.\n\n D. Auxiliary space is the space required to store the input to the operation.", "null", new String[]{"A","B","C","D"}, 2));
                questionList.add(new QuizItem(12.2f, "Q1.2", "Which of the following Big O notations represents a time complexity where the running time increases linearly with the size of the input?", "null", new String[]{"O(1)","O(log n)","O(n)","O(n log n)"}, 3));
                questionList.add(new QuizItem(12.3f, "Q1.3", "What does best-case performance in algorithm analysis indicate?", "null", new String[]{"Maximum resource usage.","Average resource usage.","Minimum resource usage.","Resource trade-off."}, 3));
                break;
            case 13:
                questionList.add(new QuizItem(13.1f, "Q2.1", "What is a key feature of a doubly linked list?", "null", new String[]{"Single traversal","No traversal","Bidirectional traversal","Infinite traversal"}, 3));
                questionList.add(new QuizItem(13.2f, "Q2.2", "In a doubly linked list, what does the ‘prev’ pointer in a node point to?", "null", new String[]{"The next node","The previous node","The current node","None of the above"}, 2));
                questionList.add(new QuizItem(13.3f, "Q2.3", "What is a common use of doubly linked lists?", "null", new String[]{"Browser history","Storing data in a queue","Performing arithmetic operations","All of the above"}, 1));
                break;
            case 14:
                questionList.add(new QuizItem(14.1f, "Q3.1", "Question Error - Answer = op1", "null", new String[]{"op1","op2","op3","op4"}, 1));
                questionList.add(new QuizItem(14.2f, "Q3.2", "Question Error - Answer = op1", "null", new String[]{"op1","op2","op3","op4"}, 1));
                questionList.add(new QuizItem(14.3f, "Q3.3", "Question Error - Answer = op1", "null", new String[]{"op1","op2","op3","op4"}, 1));
                break;
            default:

                break;
        }
    }
}