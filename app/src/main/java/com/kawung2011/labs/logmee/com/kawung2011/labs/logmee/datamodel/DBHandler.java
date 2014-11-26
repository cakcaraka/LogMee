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
    private static final String TABLE_ACTIVITIES = "activities";
    private static final String A_ID = "_id";
    private static final String A_NAME = "name";
    private static final String A_STATUS = "status";
    private static final String A_IMG = "image";
    private static final String A_DATE_TIME = "dateTime";
    //derived
    private static final String A_COUNT_TEXT = "count_text";
    private static final String A_COUNT_IMAGE = "count_image";
    private static final String A_COUNT_SPEECH = "count_speech";

    // table logs
    private static final String TABLE_LOGS = "logs";
    private static final String L_ID ="_id";
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
                A_DATE_TIME + " text, " +
                A_COUNT_TEXT + " integer, " +
                A_COUNT_IMAGE + " integer, " +
                A_COUNT_SPEECH + " integer)";
        db.execSQL(sql);

        sql = "create table " + TABLE_LOGS + " (" + L_ID + " integer primary key  AUTOINCREMENT NOT NULL, " +
                L_ACTIVITY + " integer, " +
                L_TEXT + " text, " +
                L_IMG + " text, " +
                L_SPEECH + " text, " +
                L_LOCATION + " text, " +
                L_LOCATION_LONGITUDE + " text, " +
                L_LOCATION_LATITUDE + " text, " +
                L_DATE_TIME + " text)";
        db.execSQL(sql);
        insertSomeData(db);
    }

    private void insertSomeData(SQLiteDatabase db) {
        db.execSQL("insert into activities values(0, \"Tekmob\", \"in progress\", \"image Tekmob\", \"24/11/2014\", 0, 0, 0);");
        db.execSQL("insert into activities values(1, \"PMPL\", \"in progres\", \"image pmpl\", \"24/11/2014\", 0, 0, 0);");
        db.execSQL("insert into activities values(2, \"Sister\", \"in progres\", \"image sister\", \"24/11/2014\", 0, 0, 0);");
        db.execSQL("insert into logs values(0, 0, \"Brainstorming ide\", \"image brainstorming\", \"speech brainstorming\", \"kantin\", \"-1.4\" , \"1.5\", \"24/11/2014\");");
        db.execSQL("insert into logs values(1, 0, null, \"image sketch\", null, \"kantin\", \"-1.4\" , \"1.5\", \"24/11/2014\");");
        db.execSQL("insert into logs values(2, 0, null, \"image mock up\", null, \"perpustakaan\", \"-1.4\" , \"1.5\", \"25/11/2014\");");
        db.execSQL("insert into logs values(3, 0, null, \"desain ERD\", null, \"perpustakaan\", \"-1.4\" , \"1.5\", \"25/11/2014\");");
        db.execSQL("insert into logs values(4, 0, \"setting environment\", null, null, \"perpustakaan\", \"-1.4\" , \"1.5\", \"25/11/2014\");");
        db.execSQL("insert into logs values(5, 0, null, null, \"speech instalasi android studio\", \"perpustakaan\", \"-1.4\" , \"1.5\", \"25/11/2014\");");
        db.execSQL("insert into logs values(6, 1, \"usability testing\", null, null, \"lab\", \"-1.4\" , \"1.5\", \"25/11/2014\");");
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

    public List<Activities> getAllActivitiesInMain() {
        List<Activities> listActivities = new ArrayList<Activities>();
        // Select All Query

        String selectQuery =
                "select id, name, status, image, dateTime, ifnull(count_text,0), ifnull(count_image,0), ifnull(count_speech,0)\n" +
                "from activities left join\n" +
                "(select activity, count_text, count_image, count_speech from\n" +
                "(select activity, count(text) as count_text from logs group by activity) natural join\n" +
                "(select activity, count(image) as count_image from logs group by activity) natural join\n" +
                "(select activity, count(speech) as count_speech from logs group by activity))\n" +
                "on id = activity;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Activities activity = new Activities();
                activity.set_id(Integer.parseInt(cursor.getString(0)));
                activity.set_name(cursor.getString(1));
                activity.set_status(cursor.getString(2));
                activity.set_image(cursor.getString(3));
                activity.set_dateTime(cursor.getString(4));
                activity.set_count_logs_text(Integer.parseInt(cursor.getString(5)));
                activity.set_count_logs_image(Integer.parseInt(cursor.getString(6)));
                activity.set_count_logs_speech(Integer.parseInt(cursor.getString(7)));

                // Adding contact to list
                listActivities.add(activity);
            } while (cursor.moveToNext());
        }
        // return contact list
        return listActivities;
    }

    public Cursor fetchAllActivitiesInMain() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "select * from activities";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchAllLogsById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "select * from logs where activity = " +id+";";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }



    public List<Activities> getAllActivities() {
        List<Activities> listActivities = new ArrayList<Activities>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ACTIVITIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Activities activity = new Activities();
                activity.set_id(Integer.parseInt(cursor.getString(0)));
                activity.set_name(cursor.getString(1));
                activity.set_status(cursor.getString(2));
                activity.set_image(cursor.getString(3));
                activity.set_dateTime(cursor.getString(4));

                // Adding contact to list
                listActivities.add(activity);
            } while (cursor.moveToNext());
        }

        // return contact list
        return listActivities;
    }
    
    public Activities findActivity(int activityId) {
        String query = "Select * FROM " + TABLE_ACTIVITIES + " WHERE " + A_ID + " = " + activityId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Activities activity = new Activities();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            activity.set_id(Integer.parseInt(cursor.getString(0)));
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

    public void addLogs(Logs log) {
        ContentValues values = new ContentValues();
        values.put(L_ACTIVITY, log.get_activiy_id());
        values.put(L_TEXT, log.get_text());
        values.put(L_IMG, log.get_image());
        values.put(L_SPEECH, log.get_speech());
        values.put(L_LOCATION, log.get_location());
        values.put(L_LOCATION_LATITUDE, log.get_latitude());
        values.put(L_LOCATION_LONGITUDE, log.get_longitude());
        values.put(L_DATE_TIME, log.get_dateTime());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_LOGS, null, values);

        db.close();
    }

    public List<Logs> getAllLogs() {
        List<Logs> listLogs = new ArrayList<Logs>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Logs log = new Logs();
                log.set_id(Integer.parseInt(cursor.getString((0))));
                log.set_activiy_id(Integer.parseInt(cursor.getString(1)));
                log.set_text(cursor.getString(2));
                log.set_image(cursor.getString(3));
                log.set_speech(cursor.getString(4));
                log.set_location(cursor.getString(5));
                log.set_longitude(cursor.getString(6));
                log.set_latitude(cursor.getString(7));
                log.set_dateTime(cursor.getString(8));
                // Adding contact to list
                listLogs.add(log);
            } while (cursor.moveToNext());
        }
        // return contact list
        return listLogs;
    }

    public static String getTag() {
        return TAG;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static String getTableActivities() {
        return TABLE_ACTIVITIES;
    }

    public static String getaId() {
        return A_ID;
    }

    public static String getaName() {
        return A_NAME;
    }

    public static String getaStatus() {
        return A_STATUS;
    }

    public static String getaImg() {
        return A_IMG;
    }

    public static String getaDateTime() {
        return A_DATE_TIME;
    }

    public static String getaCountText() {
        return A_COUNT_TEXT;
    }

    public static String getaCountImage() {
        return A_COUNT_IMAGE;
    }

    public static String getaCountSpeech() {
        return A_COUNT_SPEECH;
    }

    public static String getTableLogs() {
        return TABLE_LOGS;
    }

    public static String getlId() {
        return L_ID;
    }

    public static String getlActivity() {
        return L_ACTIVITY;
    }

    public static String getlText() {
        return L_TEXT;
    }

    public static String getlImg() {
        return L_IMG;
    }

    public static String getlSpeech() {
        return L_SPEECH;
    }

    public static String getlLocation() {
        return L_LOCATION;
    }

    public static String getlLocationLatitude() {
        return L_LOCATION_LATITUDE;
    }

    public static String getlLocationLongitude() {
        return L_LOCATION_LONGITUDE;
    }

    public static String getlDateTime() {
        return L_DATE_TIME;
    }



}
