package com.example.yj.bluetoothapplication.UserType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class UserDBManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OODP";
    public static final String TABLE_NAME = "User_Table";
    private static final String KEY_TEAM_NUM = "team_num";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PW = "password";
    public static final String KEY_MANAGER = "manager_bit";

    public UserDBManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_TEAM_NUM + " INTEGER, "
                + KEY_ID +" TEXT," + KEY_NAME + " TEXT,"
                + KEY_PW + " TEXT," + KEY_MANAGER + " INTEGER" + ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);

        onCreate(db);
    }

    public void addUserData(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEAM_NUM, user.getUserTeamNum());
        values.put(KEY_ID, user.getUserId());
        values.put(KEY_NAME, user.getUserName());
        values.put(KEY_PW, user.getUserPw());
        values.put(KEY_MANAGER, user.getManager());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<User> getAllUserData(){
        ArrayList<User> userDataList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_NAME + " ASC" + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                User userData = new User();
                userData.setUserTeamNum(cursor.getInt(0));
                userData.setUserId(cursor.getString(1));
                userData.setUserName(cursor.getString(2));
                userData.setUserPw(cursor.getString(3));
                userData.setManager(cursor.getInt(4));
                userDataList.add(userData);
            }while (cursor.moveToNext());
        }
        db.close();
        return userDataList;
    }

    public void updateUserData(User user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEAM_NUM, user.getUserTeamNum());
        values.put(KEY_NAME, user.getUserName());
        values.put(KEY_PW, user.getUserPw());
        values.put(KEY_MANAGER, user.getManager());

        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{user.getUserId()});
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void createTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_TEAM_NUM + " INTEGER, "
                + KEY_ID +" TEXT," + KEY_NAME + " TEXT,"
                + KEY_PW + " TEXT," + KEY_MANAGER + " INTEGER" + ");";
        db.execSQL(CREATE_TABLE);
    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE " + TABLE_NAME + ";";
        db.execSQL(query);
        db.close();
    }
}
