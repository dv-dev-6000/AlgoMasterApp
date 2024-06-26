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
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private MyApplication myApplication = new MyApplication();
    private TimeHelper timeHelper = new TimeHelper();
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
    public boolean Get_IsEmpty(String tableName, int userID){

        SQLiteDatabase db = this.getWritableDatabase();
        long rowCount;
        if (userID > -1){
            rowCount = DatabaseUtils.queryNumEntries(db, tableName, COLUMN_USER_ID + " = " + userID);
        }
        else{
            rowCount = DatabaseUtils.queryNumEntries(db, tableName);
        }

        db.close();
        return rowCount <= 0;
    }

    public String Get_DataString(int userID){

        // return vars
        int uID =0;
        int rank =0;
        int lessonsComplete =0;
        int lessonsPerfect =0;
        int achVieved =0;
        int achGained =0;
        int totalLogins =0;
        String achIDs = "";
        String returnMe;

        String qString = "SELECT * FROM " + GENERAL_STATS + " WHERE " + COLUMN_USER_ID + " = " + userID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()){
            uID = userID;
            rank = cursor.getInt(1);
            lessonsComplete = cursor.getInt(2);
            lessonsPerfect = cursor.getInt(3);
            achVieved = cursor.getInt(4);
            achGained = cursor.getInt(5);
            totalLogins = cursor.getInt(6);
        }
        cursor.close();
        db.close();

        qString = "SELECT * FROM " + ACHIEVEMENT_PROGRESS+ " WHERE " + COLUMN_ACHIEVEMENT_ACTIVE + " = TRUE";
        db = this.getReadableDatabase();
        cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()) {
            do {
                achIDs = achIDs + cursor.getInt(0) + ",";
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        returnMe = uID + "-" + rank + "-" + lessonsComplete + "-" + lessonsPerfect + "-" + achVieved + "-" + achGained + "-" + totalLogins + "-" + achIDs;

        return returnMe;
    }

    //region General Stats Related -----------------------------------------------------------------

    // add new user record
    public boolean addNewUserRecord(int userID){

        long time = timeHelper.GetCurrentUnixTime();

        // Variables
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        long rowCountOld = DatabaseUtils.queryNumEntries(db, GENERAL_STATS);

        cv.put(COLUMN_USER_ID, userID);
        cv.put(COLUMN_CURRENT_LEVEL, 0);
        cv.put(COLUMN_LESSONS_COMPLETE, 0);
        cv.put(COLUMN_LESSONS_PERFECT, 0);
        cv.put(COLUMN_ACHIEVEMENTS_VIEWED, 0);
        cv.put(COLUMN_ACHIEVEMENTS_GAINED, 0);
        cv.put(COLUMN_TOTAL_LOGINS, 1);
        cv.put(COLUMN_LAST_LOGIN, time);
        cv.put(COLUMN_CURRENT_STREAK, 1);

        db.insert(GENERAL_STATS, null, cv);

        long rowCountNew = DatabaseUtils.queryNumEntries(db, GENERAL_STATS);

        db.close();

        return (rowCountNew - rowCountOld) == 1;
    }

    // getRank
    public int getUserRank(int id){

        int returnMe = 0;

        String qString = "SELECT * FROM " + GENERAL_STATS + " WHERE " + COLUMN_USER_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()){
            returnMe = cursor.getInt(1);
        }
        cursor.close();
        db.close();

        return returnMe;
    }

    // update user rank
    public void updateUserRank(int userID, int newRank){

        // called when new module unlocked
        int currRank = getUserRank(userID);

        if (newRank > currRank){
            // update rank in db
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            db.execSQL("UPDATE " + GENERAL_STATS + " SET " + COLUMN_CURRENT_LEVEL + " = " + COLUMN_CURRENT_LEVEL + " + 1 WHERE " + COLUMN_USER_ID + " = " + userID);
            db.close();
        }

        myApplication.setUserRank(getUserRank(userID));
    }

    // update perfect score count
    public void updatePerfectTotal(int userID, int newTotal){

        SQLiteDatabase db;
        db = this.getWritableDatabase();
        db.execSQL("UPDATE " + GENERAL_STATS + " SET " + COLUMN_LESSONS_PERFECT + " = " + newTotal + " WHERE " + COLUMN_USER_ID + " = " + userID);
        db.close();

    }

    // update lessons passed
    public void updateLessonsPassedTotal(int userID, int newTotal){

        SQLiteDatabase db;
        db = this.getWritableDatabase();
        db.execSQL("UPDATE " + GENERAL_STATS + " SET " + COLUMN_LESSONS_COMPLETE + " = " + newTotal + " WHERE " + COLUMN_USER_ID + " = " + userID);
        db.close();

    }

    // update new login, check for streak or return
    public void updateLoginTotal(int id, Context context){

        // vars
        SQLiteDatabase db;
        Date lastLogin = null;
        int currStreak = 0;
        long currUnixTime = timeHelper.GetCurrentUnixTime();
        Date currTime = timeHelper.UnixTimeToDate(currUnixTime);

        // update login total
        db = this.getWritableDatabase();
        db.execSQL("UPDATE " + GENERAL_STATS + " SET " + COLUMN_TOTAL_LOGINS + " = " + COLUMN_TOTAL_LOGINS + " + 1 WHERE " + COLUMN_USER_ID + " = " + id);
        db.close();

        // get current streak and last login date
        String qString = "SELECT * FROM " + GENERAL_STATS + " WHERE " + COLUMN_USER_ID + " = " + id;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()){
            lastLogin = timeHelper.UnixTimeToDate(cursor.getInt(7));
            currStreak = cursor.getInt(8);
        }
        cursor.close();
        db.close();

        // check for streak if not check for return
        if (timeHelper.IsNextDay(currTime ,lastLogin)){
            // update streak
            switch(currStreak+1){
                case 2:
                    AchievementEarned(6, context);
                    break;
                case 3:
                    AchievementEarned(7, context);
                    break;
                case 4:
                    AchievementEarned(8, context);
                    break;
                case 5:
                    AchievementEarned(9, context);
                    break;
            }
            updateStreak(id, true);
        }
        else if (timeHelper.Is24HrReturn(currTime,lastLogin)){
            // update return
            AchievementEarned(12, context);
            updateStreak(id, false);
        }

        // update last login date
        db = this.getWritableDatabase();
        db.execSQL("UPDATE " + GENERAL_STATS + " SET " + COLUMN_LAST_LOGIN + " = " + currUnixTime + " WHERE " + COLUMN_USER_ID + " = " + id);
        db.close();
    }

    private void updateStreak(int id, boolean increment){

        SQLiteDatabase db = this.getWritableDatabase();
        if (increment){
            db.execSQL("UPDATE " + GENERAL_STATS + " SET " + COLUMN_CURRENT_STREAK + " = " + COLUMN_CURRENT_STREAK + " + 1 WHERE " + COLUMN_USER_ID + " = " + id);
        }
        else{
            db.execSQL("UPDATE " + GENERAL_STATS + " SET " + COLUMN_TOTAL_LOGINS + " = 1 WHERE " + COLUMN_USER_ID + " = " + id);
        }

        db.close();
    }

    public void updateAchievementViews(int id, Context context){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + GENERAL_STATS + " SET " + COLUMN_ACHIEVEMENTS_VIEWED + " = " + COLUMN_ACHIEVEMENTS_VIEWED + " + 1 WHERE " + COLUMN_USER_ID + " = " + id);
        db.close();

        String qString = "SELECT * FROM " + GENERAL_STATS + " WHERE " + COLUMN_USER_ID + " = " + id;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()){
            if (cursor.getInt(4) >= 10){
                AchievementEarned(13, context);
            }
        }
        cursor.close();
        db.close();
    }

    //end region------------------------------------------------------------------------------------

    //region Lesson Related ------------------------------------------------------------------------

    // check if lesson complete
    public Boolean isLessonComplete(int id){

        boolean returnMe = false;

        // get Value
        String qString = "SELECT * FROM " + LESSON_PROGRESS + " WHERE " + COLUMN_LESSON_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()){
            if (cursor.getInt(6) > 1){
                returnMe = true;
            }
        }
        cursor.close();
        db.close();

        return returnMe;
    }

    // register a lesson click
    public void UpdateLessonClicked(int id){

        // Update Value
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + LESSON_PROGRESS + " SET " + COLUMN_LESSON_CLICKED + " = " + COLUMN_LESSON_CLICKED + " + 1 WHERE " + COLUMN_LESSON_ID + " = " + id);

        db.close();

    }

    // register a new quiz score
    public void UpdateQuizScore(int id, int newScore, Context context){

        // get previous score
        int prevScore;
        String qString = "SELECT * FROM " + LESSON_PROGRESS + " WHERE " + COLUMN_LESSON_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        if (cursor.moveToFirst()){
            prevScore = cursor.getInt(6);
        }
        else{
            prevScore = 1; // error
        }
        cursor.close();
        db.close();

        // check new value is higher than previous
        if (newScore > prevScore){
            // Update Value
            db = this.getWritableDatabase();
            db.execSQL("UPDATE " + LESSON_PROGRESS + " SET " + COLUMN_QUIZ_SCORE + " = " + newScore + " WHERE " + COLUMN_LESSON_ID + " = " + id);

            // if lesson passed update db
            if (newScore > 1){
                db.execSQL("UPDATE " + LESSON_PROGRESS + " SET " + COLUMN_LESSON_COMPLETE + " = TRUE WHERE " + COLUMN_LESSON_ID + " = " + id);

                //if lesson triggers perseverance achievement
                if (prevScore > -1 && prevScore < 2){
                    db.close();
                    AchievementEarned(11, context);
                }
            }
        }
        db.close();

    }

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

                boolean perfect;
                if (score == max){
                    perfect = true;
                }
                else{perfect = false;}

                LessonItem l = new LessonItem(id, clicked, complete, title, desc, max, score, perfect);
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

        initialLessons.add(new LessonItem(0, 0, false, "DS Basics", "A beginners guide to data structures", 3, -1, false));
        initialLessons.add(new LessonItem(1, 0, false, "Stack", "Introducing the stack data structure", 3, -1, false));
        initialLessons.add(new LessonItem(2, 0, false, "Queue", "Introducing the queue data structure", 3, -1, false));
        initialLessons.add(new LessonItem(3, 0, false, "Circular Queue", "Optimising the queue data structure", 3, -1, false));
        initialLessons.add(new LessonItem(4, 0, false, "Linked List", "Introducing the linked list data structure", 3, -1, false));
        initialLessons.add(new LessonItem(5, 0, false, "Additional Considerations", "Things to consider when selecting the appropriate data structures", 3, -1, false));


        initialLessons.add(new LessonItem(6, 0, false, "Search: Linear Search", "Introducing the linear search algorithm", 3, -1, false));
        initialLessons.add(new LessonItem(7, 0, false, "Search: Binary Search", "Introducing the binary search algorithm", 3, -1, false));
        initialLessons.add(new LessonItem(8, 0, false, "Sorting 1: Bubble Sort", "Introducing the bubble sort algorithm", 3, -1, false));
        initialLessons.add(new LessonItem(9, 0, false, "Sorting 2: Insertion Sort", "Introducing the insertion sort algorithm", 3, -1, false));
        initialLessons.add(new LessonItem(10, 0, false, "Sorting 3: Selection Sort", "Introducing the selection sort algorithm", 3, -1, false));
        initialLessons.add(new LessonItem(11, 0, false, "Sorting 4: Merge Sort", "Introducing the merge sort algorithm", 3, -1, false));

        initialLessons.add(new LessonItem(12, 0, false, "Algorithmic Complexity", "An overview of algorithmic complexity and big O notation", 3, -1, false));
        initialLessons.add(new LessonItem(13, 0, false, "Doubly linked lists", "Advanced Lists", 3, -1, false));
        initialLessons.add(new LessonItem(14, 0, false, "Binary Trees", "Advanced Data Structures", 3, -1, false));

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

            db.execSQL("UPDATE " + GENERAL_STATS + " SET " + COLUMN_ACHIEVEMENTS_GAINED + " = " + COLUMN_ACHIEVEMENTS_GAINED + " + 1 WHERE " + COLUMN_USER_ID + " = " + MyApplication.userID);
            db.close();

            if (MyApplication.isGamified){
                String toastString = "Achievement Unlocked:\n" + name;
                Toast toast = Toast.makeText(context, toastString, Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

    // gets number of achievements earned
    public int getEarnedAchievementTotal(){

        int returnMe;

        // get data from database
        String qString = "SELECT * FROM " + ACHIEVEMENT_PROGRESS + " WHERE " + COLUMN_ACHIEVEMENT_ACTIVE + " = TRUE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        returnMe = cursor.getCount();
        cursor.close();
        db.close();

        return returnMe;
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

        initialAchievements.add(new Achievement("Getting Started!", "Begin your learning adventure", false));
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