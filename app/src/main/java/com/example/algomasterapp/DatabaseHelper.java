package com.example.algomasterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private List<Achievement> initialAchievements = new ArrayList<Achievement>();

    //region Constant Vars
    // LESSON TABLE CONSTANTS
    public static final String LESSON_PROGRESS = "LESSON_PROGRESS";
    public static final String COLUMN_LESSON_ID = "LESSON_ID";
    public static final String COLUMN_LESSON_CLICKED = "LESSON_CLICKED";
    public static final String COLUMN_LESSON_COMPLETE = "LESSON_COMPLETE";
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

        String createTableLesson = "CREATE TABLE " + LESSON_PROGRESS + " (" + COLUMN_LESSON_ID + " INTEGER PRIMARY KEY, " + COLUMN_LESSON_CLICKED + " INTEGER, " + COLUMN_LESSON_COMPLETE + " BOOL, " + COLUMN_QUIZ_SCORE + " INTEGER)";
        String createTableAchievements = "CREATE TABLE " + ACHIEVEMENT_PROGRESS + " (" + COLUMN_ACHIEVEMENT_ID + " INTEGER PRIMARY KEY, " + COLUMN_ACHIEVEMENT_ACTIVE + " BOOL, " + COLUMN_ACHIEVEMENT_TITLE + " TEXT, " + COLUMN_ACHIEVEMENT_DESC + " TEXT)";
        String createTableGeneral = "CREATE TABLE " + GENERAL_STATS + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY, " + COLUMN_CURRENT_LEVEL + " INTEGER, " + COLUMN_LESSONS_COMPLETE + " INTEGER, " + COLUMN_LESSONS_PERFECT + " INTEGER, " + COLUMN_ACHIEVEMENTS_VIEWED + " INTEGER, " + COLUMN_ACHIEVEMENTS_GAINED + " INTEGER, " + COLUMN_TOTAL_LOGINS + " INTEGER, " + COLUMN_LAST_LOGIN + " INTEGER, " + COLUMN_CURRENT_STREAK + " INTEGER)";

        db.execSQL(createTableLesson);
        db.execSQL(createTableAchievements);
        db.execSQL(createTableGeneral);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

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

    public boolean Get_IsEmpty(String tableName){

        SQLiteDatabase db = this.getWritableDatabase();
        long rowCount = DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return rowCount <= 0;
    }

    private void fillInitialAchievements() {

        initialAchievements.add(new Achievement("Getting Started!", "Begin your first lesson.", false, "stack"));
        initialAchievements.add(new Achievement("Level 2!", "Unlocked level 2: Algorithms", false, "stack"));
        initialAchievements.add(new Achievement("Level 3!", "Unlocked level 3: Advanced", false, "stack"));
        initialAchievements.add(new Achievement("DS Perfectionist!", "Get a perfect score on all data structures quiz's", false, "stack"));
        initialAchievements.add(new Achievement("Algo Perfectionist!", "Get a perfect score on all algorithms quiz's", false, "stack"));
        initialAchievements.add(new Achievement("Ultimate Perfection!", "Get a perfect score on all quiz's", false, "stack"));
        initialAchievements.add(new Achievement("2 Day Streak!", "Log in two days consecutively", false, "stack"));
        initialAchievements.add(new Achievement("3 Day Streak!", "Log in three days consecutively", false, "stack"));
        initialAchievements.add(new Achievement("4 Day Streak!", "Log in four days consecutively", false, "stack"));
        initialAchievements.add(new Achievement("5 Day Streak!", "Log in five days consecutively", false, "stack"));
        initialAchievements.add(new Achievement("Studious!", "Revisit completed lessons", false, "stack"));
        initialAchievements.add(new Achievement("Perseverance!", "Succeed in previously failed quiz", false, "stack"));
        initialAchievements.add(new Achievement("Back in the Game!", "Log back in after over 24 hours away", false, "stack"));
        initialAchievements.add(new Achievement("Proud Collector!", "You like view your achievements", false, "stack"));
        initialAchievements.add(new Achievement("Secret!", "What?", false, "stack"));

    }

    ///**
    // * Save the given list of combatants into the database
    // * @param cList - the list of combatants to save
    // */
    //public boolean addCombatantList(List<Combatant> cList){
//
    //    // Variables
    //    long insert;
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    ContentValues cv = new ContentValues();
//
    //    // cycles though the combatants in the list
    //    // for each combatant, its data is entered ito the db
    //    for (Combatant c: cList){
//
    //        cv.put(COL_ID, c.getId());
    //        cv.put(COL_INITIATIVE, c.getM_ini());
    //        cv.put(COL_HP, c.getM_hp());
    //        cv.put(COL_HP_MAX, c.getM_hpMax());
    //        cv.put(COL_NAME, c.getM_name());
    //        cv.put(COL_CONDITION, c.getM_cond());
    //        cv.put(COL_URL, c.getM_url());
    //        cv.put(COL_AC, c.getM_ac());
//
//
    //        insert = db.insert(COMBATANT_TABLE, null, cv);
    //        if (insert == -1){
    //            return false;
    //        }
    //    }
//
    //    db.close();
    //    return true;
    //}
//
    ///**
    // * loads all combatants from the database and returns them as a list of combatants
    // */
    //public List<Combatant> loadCombatants(){
//
    //    // list to return
    //    List<Combatant> returnList = new ArrayList<>();
//
    //    // get data from database
    //    String qString = "SELECT * FROM " + COMBATANT_TABLE;
    //    SQLiteDatabase db = this.getReadableDatabase();
    //    Cursor cursor = db.rawQuery(qString, null);
//
    //    if (cursor.moveToFirst()){
    //        // loop through results, create new combatant, add to list
    //        do{
    //            int id = cursor.getInt(0);
    //            int ini = cursor.getInt(1);
    //            int hp = cursor.getInt(2);
    //            int hpMax = cursor.getInt(3);
    //            String name = cursor.getString(4);
    //            String con = cursor.getString(5);
    //            String url = cursor.getString(6);
    //            int ac = cursor.getInt(7);
//
    //            Combatant newCom = new Combatant(id, ini, hp, hpMax, name, con, url, ac);
    //            returnList.add(newCom);
//
    //        } while (cursor.moveToNext());
    //    }
    //    else{
    //        // no results
    //    }
//
    //    cursor.close();
    //    db.close();
    //    return returnList;
    //}

}
