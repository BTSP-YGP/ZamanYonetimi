package com.example.zamanyonetimi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.provider.BaseColumns._ID;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "zamanyonetimi.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists jobs (jobid INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL,"+
                " description TEXT, baslangic TEXT, bitis TEXT, important BOOLEAN, urgent BOOLEAN, complete BOOLEAN); " +
                "create table if not exists reminders (jobid INTEGER PRIMARY KEY, reminddate TEXT, remindtime TEXT,"+
                " FOREIGN KEY(jobid) REFERENCES jobs(jobid));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS jobs, reminders;");
        onCreate(db);
    }

    public boolean insertJob (String job_name, String job_description, Editable baslangic, Editable bitis, Boolean important, Boolean urgent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", job_name);
        contentValues.put("description", job_description);
        contentValues.put("baslangic", baslangic.toString());
        contentValues.put("bitis", bitis.toString());
        contentValues.put("important", important);
        contentValues.put("urgent", urgent);
        long sonuc = db.insert("jobs", null, contentValues);
        return sonuc != -1;

    }
    public boolean insertReminder (Integer job_id, Date reminddate, Time remindtime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("jobid", job_id);
        contentValues.put("reminddate", reminddate.toString());
        contentValues.put("remindtime", remindtime.toString());
        long sonuc = db.insert("reminders", null, contentValues);
        return sonuc != -1;
    }

    public boolean deleteJob (Integer job_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long sonuc = db.delete("jobs", "jobid ="+job_id, null);
        return sonuc != -1;
    }

    public boolean updateJob (Integer job_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long sonuc = db.delete("jobs", "jobid ="+job_id, null);
        return sonuc != -1;
    }

    public void tamamlaJob (Integer job_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE jobs SET complete = 1 WHERE jobid = "+ job_id.toString(),null);
    }
}
