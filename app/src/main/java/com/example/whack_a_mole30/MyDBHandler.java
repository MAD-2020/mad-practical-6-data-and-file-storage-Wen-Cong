package com.example.whack_a_mole30;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user1DB.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_SCORE = "score";
    private final String TAG = "DBHandler";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_LEVEL + " INTEGER," + COLUMN_SCORE + " INTEGER," +
                "PRIMARY KEY (" + COLUMN_USERNAME + "," + COLUMN_LEVEL + "));";

        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(User user){
        ContentValues values = new ContentValues();
        for(int i = 0; i < user.getScoreList().length; i++){
            values.put(COLUMN_USERNAME, user.getUsername());
            values.put(COLUMN_PASSWORD, user.getPassword());

            values.put(COLUMN_LEVEL, i + 1);
            values.put(COLUMN_SCORE, user.getScoreList()[i]);

            SQLiteDatabase database = this.getWritableDatabase();
            database.insert(TABLE_USERS, null, values);
            database.close();
            Log.v("Added Data", "Username=" + user.getUsername() + " Password=" + user.getPassword() + " Level="
            + (i+1) + " Score=" + user.getScoreList()[i]);
        }
    }

    public User loadUser(String username){
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = \"" + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        User user = null;

        if(cursor.moveToFirst()){
            user = new User();
            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            Log.v(TAG, "User name: " + user.getUsername() + " Found!");

            int[] score = user.getScoreList();
            score[count] = Integer.parseInt(cursor.getString(3));
            user.setScoreList(score);
            count++;
        }

        while(cursor.moveToNext()){
            int[] score = user.getScoreList();
            score[count] = Integer.parseInt(cursor.getString(3));
            user.setScoreList(score);
            count++;
        }

        cursor.close();
        db.close();

        return user;
    }

    public void updateScore(String username ,int level, int scorePoint){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, scorePoint);
        String whereClause = COLUMN_USERNAME + "=" + "\"" + username + "\"" + " AND " + COLUMN_LEVEL + "=" + level;
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USERS,values,whereClause,null);
        db.close();
        Log.v(TAG, "Data Updated!");
    }


}
