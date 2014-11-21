package com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by numan on 20/11/2014.
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final String TAG ="DbHelper";
    private static final String DB_NAME = "logmee.db";
    private static final int DB_VERSION = 1;
    // table activities
    private static final String TABLE_ACTIVITIES = "activites";
    private static final String A_ID = "id";
    private static final String A_NAME = "name";
    private static final String A_STATUS = "status";
    private static final String A_IMG = "image";
    private static final String A_DATE_TIME = "dateTime";
    // table logs
    private  static final String TABLE_LOGS = "logs";
    private static final String L_ID ="id";
    private static final String L_ACTIVITY = "activity";
    private static final String L_TEXT = "text";
    private static final String L_IMG = "image";
    private static final String L_SPEECH = "speech";
    private static final String L_LOCATION = "location";
    private static final String L_LOCATION_LONGITUDE = "longitude";
    private static final String L_LOCATION_LATITUDE = "latitude";
    private static final String L_DATE_TIME = "dateTime";

    //Constructor
    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory)  {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_ACTIVITIES + " (" + A_ID + " integer primary key  AUTOINCREMENT NOT NULL, " +
                A_NAME + " text, " +
                A_STATUS + " text, " +
                A_IMG + " text, " +
                A_DATE_TIME + " text)";
        db.execSQL(sql);

        sql = "create table " + TABLE_LOGS + " (" + L_ID + " integer primary key  AUTOINCREMENT NOT NULL, " +
                L_ACTIVITY + " text, " +
                L_TEXT + " text, " +
                L_IMG + " text, " +
                L_SPEECH + " text, " +
                L_LOCATION + " text, " +
                L_LOCATION_LONGITUDE + " text, " +
                L_LOCATION_LATITUDE + " text, " +
                L_DATE_TIME + " text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ACTIVITIES);
        db.execSQL("drop table if exists " + TABLE_LOGS);
        onCreate(db);
    }

    public void addActivities(Activities activity) {
        ContentValues values = new ContentValues();
        values.put(A_NAME, activity.get_name());
        values.put(A_STATUS, activity.get_status());
        values.put(A_IMG, activity.get_image());
        values.put(A_DATE_TIME, activity.get_dateTime());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ACTIVITIES, null, values);

        db.close();
    }

    public List<Activities> getAllActivities() {
        List<Activities> contactList = new ArrayList<Activities>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIVITIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Activities contact = new Activities();
                contact.set_id(cursor.getString(0));
                contact.set_name(cursor.getString(1));
                contact.set_status(cursor.getString(2));
                contact.set_image(cursor.getString(3));
                contact.set_dateTime(cursor.getString(4));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public void addMember(int id, String name) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("member", null, values);

    }

    public Activities findActivity(String activityName) {
        String query = "Select * FROM " + TABLE_ACTIVITIES + " WHERE " + A_NAME + " =  \"" + activityName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Activities activity = new Activities();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            activity.set_id(cursor.getString(0));
            activity.set_name(cursor.getString(1));
            activity.set_status(cursor.getString(2));
            activity.set_image(cursor.getString(3));
            activity.set_dateTime(cursor.getString(4));
            cursor.close();
        } else {
            activity = null;
        }
        db.close();
        return activity;
    }

}
