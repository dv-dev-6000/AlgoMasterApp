package com.example.algomasterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private List<Achievement> initialAchievements = new ArrayList<Achievement>();
    private List<LessonItem> initialLessons = new ArrayList<LessonItem>();

    //region Constant Vars
    // LESSON TABLE CONSTANTS
    public static final String LESSON_PROGRESS = "LESSON_PROGRESS";
    public static final String COLUMN_LESSON_ID = "LESSON_ID";
    public static final String COLUMN_LESSON_CLICKED = "LESSON_CLICKED";
    public static final String COLUMN_LESSON_COMPLETE = "LESSON_COMPLETE";
    public static final String COLUMN_LESSON_TITLE = "LESSON_TITLE";
    public static final String COLUMN_LESSON_DESC = "LESSON_DESC";
    public static final String COLUMN_QUIZ_MAX = "QUIZ_MAX";
    public static final String COLUMN_QUIZ_SCORE = "QUIZ_SCORE";
    // ACHIEVEMENT TABLE CONSTANTS
    public static final String ACHIEVEMENT_PROGRESS = "ACHIEVEMENT_PROGRESS";
    public static final String COLUMN_ACHIEVEMENT_ID = "ACHIEVEMENT_ID";
    public static final String COLUMN_ACHIEVEMENT_ACTIVE = "ACHIEVEMENT_ACTIVE";
    public static final String COLUMN_ACHIEVEMENT_TITLE = "ACHIEVEMENT_TITLE";
    public static final String COLUMN_ACHIEVEMENT_DESC = "ACHIEVEMENT_DESC";
    // GENERAL TABLE CONSTANTS
    public static final String GENERAL_STATS = "GENERAL_STATS";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_CURRENT_LEVEL = "CURRENT_LEVEL";
    public static final String COLUMN_LESSONS_COMPLETE = "LESSONS_COMPLETE";
    public static final String COLUMN_LESSONS_PERFECT = "LESSONS_PERFECT";
    public static final String COLUMN_ACHIEVEMENTS_VIEWED = "ACHIEVEMENTS_VIEWED";
    public static final String COLUMN_ACHIEVEMENTS_GAINED = "ACHIEVEMENTS_GAINED";
    public static final String COLUMN_TOTAL_LOGINS = "TOTAL_LOGINS";
    public static final String COLUMN_LAST_LOGIN = "LAST_LOGIN";
    public static final String COLUMN_CURRENT_STREAK = "CURRENT_STREAK";
    //endregion

    public DatabaseHelper(@Nullable Context context) {
        super(context, "AlgoMasterDB", null, 1);
    }

    // called when DB is first created
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableLesson = "CREATE TABLE " + LESSON_PROGRESS + " (" + COLUMN_LESSON_ID + " INTEGER PRIMARY KEY, " + COLUMN_LESSON_CLICKED + " INTEGER, " + COLUMN_LESSON_COMPLETE + " BOOL, " + COLUMN_LESSON_TITLE + " TEXT, " + COLUMN_LESSON_DESC + " TEXT, " + COLUMN_QUIZ_MAX + " INTEGER, " + COLUMN_QUIZ_SCORE + " INTEGER)";
        String createTableAchievements = "CREATE TABLE " + ACHIEVEMENT_PROGRESS + " (" + COLUMN_ACHIEVEMENT_ID + " INTEGER PRIMARY KEY, " + COLUMN_ACHIEVEMENT_ACTIVE + " BOOL, " + COLUMN_ACHIEVEMENT_TITLE + " TEXT, " + COLUMN_ACHIEVEMENT_DESC + " TEXT)";
        String createTableGeneral = "CREATE TABLE " + GENERAL_STATS + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY, " + COLUMN_CURRENT_LEVEL + " INTEGER, " + COLUMN_LESSONS_COMPLETE + " INTEGER, " + COLUMN_LESSONS_PERFECT + " INTEGER, " + COLUMN_ACHIEVEMENTS_VIEWED + " INTEGER, " + COLUMN_ACHIEVEMENTS_GAINED + " INTEGER, " + COLUMN_TOTAL_LOGINS + " INTEGER, " + COLUMN_LAST_LOGIN + " INTEGER, " + COLUMN_CURRENT_STREAK + " INTEGER)";

        db.execSQL(createTableLesson);
        db.execSQL(createTableAchievements);
        db.execSQL(createTableGeneral);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // checks to see id a named table is empty
    public boolean Get_IsEmpty(String tableName){

        SQLiteDatabase db = this.getWritableDatabase();
        long rowCount = DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return rowCount <= 0;
    }

    //region Lesson Related ------------------------------------------------------------------------
    //
    public List<LessonItem> getLessons(int module){

        List<LessonItem> dbList = new ArrayList<LessonItem>();

        // get data from database based on module
        String qString;
        switch (module){
            case 0:
                qString = "SELECT * FROM " + LESSON_PROGRESS;
                break;
            case 1:
                qString = "SELECT * FROM " + LESSON_PROGRESS + " WHERE " + COLUMN_LESSON_ID + " < 6";
                break;
            case 2:
                qString = "SELECT * FROM " + LESSON_PROGRESS + " WHERE " + COLUMN_LESSON_ID + " > 5 AND " + COLUMN_LESSON_ID + " < 12";
                break;
            case 3:
                qString = "SELECT * FROM " + LESSON_PROGRESS + " WHERE " + COLUMN_LESSON_ID + " > 11";
                break;
            default:
                qString = "SELECT * FROM " + LESSON_PROGRESS;
                break;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()){
            // loop through results, create new combatant, add to list
            do{
                int id = cursor.getInt(0);
                int clicked = cursor.getInt(1);
                boolean complete = cursor.getInt(2) > 0;
                String title = cursor.getString(3);
                String desc = cursor.getString(4);
                int max = cursor.getInt(5);
                int score = cursor.getInt(6);

                LessonItem l = new LessonItem(id, clicked, complete, title, desc, max, score);
                dbList.add(l);
            } while (cursor.moveToNext());
        }
        else{
            // no results
        }
        cursor.close();
        db.close();

        return dbList;
    }

    public boolean addLessons(){

        // Variables
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        fillInitialLessons();
        for (LessonItem item : initialLessons) {

            cv.put(COLUMN_LESSON_ID, item.getId());
            cv.put(COLUMN_LESSON_CLICKED, item.getClickedCount());
            cv.put(COLUMN_LESSON_COMPLETE, item.getIsComplete());
            cv.put(COLUMN_LESSON_TITLE, item.getTitle());
            cv.put(COLUMN_LESSON_DESC, item.getDescription());
            cv.put(COLUMN_QUIZ_MAX, item.getQuizMax());
            cv.put(COLUMN_QUIZ_SCORE, item.getQuizScore());

            db.insert(LESSON_PROGRESS, null, cv);
        }

        long rowCount = DatabaseUtils.queryNumEntries(db, LESSON_PROGRESS);

        db.close();


        return rowCount == initialAchievements.size();
    }

    private void fillInitialLessons() {

        initialLessons.add(new LessonItem(0, 0, false, "DS Basics", "A beginners guide to data structures", 3, 0));
        initialLessons.add(new LessonItem(1, 0, false, "Stack", "Introducing the stack data structure", 3, 0));
        initialLessons.add(new LessonItem(2, 0, false, "Queue", "Introducing the queue data structure", 3, 0));
        initialLessons.add(new LessonItem(3, 0, false, "Circular Queue", "Optimising the queue data structure", 3, 0));
        initialLessons.add(new LessonItem(4, 0, false, "Linked List", "Introducing the linked list data structure", 3, 0));
        initialLessons.add(new LessonItem(5, 0, false, "Use Cases", "Looking at the different use-cases for the various data structures", 3, 0));

        initialLessons.add(new LessonItem(6, 0, false, "Algorithm Basics", "A beginners guide to Algorithms", 3, 0));
        initialLessons.add(new LessonItem(7, 0, false, "Search: Linear Search", "Introducing the linear search algorithm", 3, 0));
        initialLessons.add(new LessonItem(8, 0, false, "Search: Binary Search", "Introducing the binary search algorithm", 3, 0));
        initialLessons.add(new LessonItem(9, 0, false, "Sorting 1: Bubble Sort", "Introducing the bubble sort algorithm", 3, 0));
        initialLessons.add(new LessonItem(10, 0, false, "Sorting 2: Insertion Sort", "Introducing the insertion sort algorithm", 3, 0));
        initialLessons.add(new LessonItem(11, 0, false, "Sorting 3: Selection Sort", "Introducing the selection sort algorithm", 3, 0));

        initialLessons.add(new LessonItem(12, 0, false, "Algorithmic Complexity", "Advanced content", 3, 0));
        initialLessons.add(new LessonItem(13, 0, false, "Graphs & Trees 1", "Advanced content", 3, 0));
        initialLessons.add(new LessonItem(14, 0, false, "Graphs & Trees 2", "Advanced content", 3, 0));

    }
    //endregion ------------------------------------------------------------------------------------

    //region Achievements Related ------------------------------------------------------------------
    // activates an earned achievement in db
    public void AchievementEarned(int id, Context context){

        boolean isActive;
        String name;
        // Check value
        String qString = "SELECT * FROM " + ACHIEVEMENT_PROGRESS + " WHERE " + COLUMN_ACHIEVEMENT_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()){
            isActive = cursor.getInt(1) > 0;
            name = cursor.getString(2);
        }
        else{
            isActive = false;
            name = "Error";
        }
        cursor.close();
        db.close();

        if (!isActive){
            // Update Value
            db = this.getWritableDatabase();
            db.execSQL("UPDATE " + ACHIEVEMENT_PROGRESS + " SET " + COLUMN_ACHIEVEMENT_ACTIVE + " = 1 WHERE " + COLUMN_ACHIEVEMENT_ID + " = " + id);

            db.close();

            String toastString = "Achievement Unlocked:\n" + name;
            Toast toast = Toast.makeText(context, toastString, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    // gets list of achievements from db
    public List<Achievement> getAchievements(){

        List<Achievement> dbList = new ArrayList<Achievement>();

        // get data from database
        String qString = "SELECT * FROM " + ACHIEVEMENT_PROGRESS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()){
            // loop through results, create new combatant, add to list
            do{
                boolean active = cursor.getInt(1) > 0;
                String title = cursor.getString(2);
                String desc = cursor.getString(3);
                Achievement a = new Achievement(title, desc, active);
                dbList.add(a);
            } while (cursor.moveToNext());
        }
        else{
            // no results
        }
        cursor.close();
        db.close();

        return dbList;
    }

    // adds the achievement data to local db if none exists
    public boolean addAchievements(){

        // Variables
        int it = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        fillInitialAchievements();
        for (Achievement item : initialAchievements) {

            cv.put(COLUMN_ACHIEVEMENT_ID, it);
            cv.put(COLUMN_ACHIEVEMENT_ACTIVE, item.getActive());
            cv.put(COLUMN_ACHIEVEMENT_TITLE, item.getTitle());
            cv.put(COLUMN_ACHIEVEMENT_DESC, item.getDesc());

            db.insert(ACHIEVEMENT_PROGRESS, null, cv);

            it++;
        }

        long rowCount = DatabaseUtils.queryNumEntries(db, ACHIEVEMENT_PROGRESS);

        db.close();


        return rowCount == initialAchievements.size();
    }

    // data used by addAchievement function
    private void fillInitialAchievements() {

        initialAchievements.add(new Achievement("Getting Started!", "Begin your first lesson.", false));
        initialAchievements.add(new Achievement("Level 2!", "Unlocked level 2: Algorithms", false));
        initialAchievements.add(new Achievement("Level 3!", "Unlocked level 3: Advanced", false));
        initialAchievements.add(new Achievement("DS Perfectionist!", "Get a perfect score on all data structures quiz's", false));
        initialAchievements.add(new Achievement("Algo Perfectionist!", "Get a perfect score on all algorithms quiz's", false));
        initialAchievements.add(new Achievement("Ultimate Perfection!", "Get a perfect score on all quiz's", false));
        initialAchievements.add(new Achievement("2 Day Streak!", "Log in two days consecutively", false));
        initialAchievements.add(new Achievement("3 Day Streak!", "Log in three days consecutively", false));
        initialAchievements.add(new Achievement("4 Day Streak!", "Log in four days consecutively", false));
        initialAchievements.add(new Achievement("5 Day Streak!", "Log in five days consecutively", false));
        initialAchievements.add(new Achievement("Studious!", "Revisit completed lessons", false));
        initialAchievements.add(new Achievement("Perseverance!", "Succeed in previously failed quiz", false));
        initialAchievements.add(new Achievement("Back in the Game!", "Log back in after over 24 hours away", false));
        initialAchievements.add(new Achievement("Proud Collector!", "You like view your achievements", false));
        initialAchievements.add(new Achievement("Secret!", "What?", false));

    }
    //endregion ------------------------------------------------------------------------------------



}