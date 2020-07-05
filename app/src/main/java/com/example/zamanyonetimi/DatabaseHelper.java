package com.example.zamanyonetimi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "zamanyonetimi.db";
    public static final String JOBS_TABLE= "jobs";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table jobs (jobid INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT ,"+
                " description TEXT, baslangic TEXT, bitis TEXT, important BOOLEAN, urgent BOOLEAN, complete BOOLEAN); ");
        db.execSQL("create table reminders (name TEXT NOT NULL, reminddate TEXT PRIMARY KEY, remindtime TEXT,"+
                " FOREIGN KEY(name) REFERENCES jobs(name) ON UPDATE CASCADE ON DELETE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS jobs;");
        db.execSQL("DROP TABLE IF EXISTS reminders;");

        onCreate(db);
    }

    public boolean insertJob (String job_name, String job_description, Editable baslangic, Editable bitis,
                              Boolean important, Boolean urgent) {
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

    public boolean deleteJob (String jobName) {
        SQLiteDatabase db = this.getWritableDatabase();
        long sonuc = db.delete("jobs", "name = \'"+ jobName+"\'", null);
        return sonuc != -1;
    }

    public boolean updateJob (Integer whereId, String job_name, String job_description, Editable baslangic,
                              Editable bitis, Boolean important, Boolean urgent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", job_name);
        contentValues.put("description", job_description);
        contentValues.put("baslangic", baslangic.toString());
        contentValues.put("bitis", bitis.toString());
        contentValues.put("important", important);
        contentValues.put("urgent", urgent);
        long sonuc = db.update("jobs", contentValues, "jobid = \'"+ whereId+"\'", null);
        return sonuc != -1;
    }

    public void tamamlaJob (String jobName, Integer durum) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE jobs SET complete = "+ durum.toString() +" WHERE name = \'"+ jobName+"\'");
    }

    public boolean insertReminder (String jobName, String reminddate, String remindtime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", jobName);
        contentValues.put("reminddate", reminddate);
        contentValues.put("remindtime", remindtime);
        long sonuc = db.insert("reminders", null, contentValues);
        return sonuc != -1;
    }

    public boolean updateReminder (String jobName, String reminddate, String remindtime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", jobName);
        contentValues.put("reminddate", reminddate);
        contentValues.put("remindtime", remindtime);
        long sonuc = db.update("reminders", contentValues, "name = \'"+ jobName+"\'", null);
        return sonuc != -1;
    }

    public Cursor ViewData(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="Select * from "+JOBS_TABLE;
        Cursor cursor=db.rawQuery(query,null);
        return cursor;

    }
}
