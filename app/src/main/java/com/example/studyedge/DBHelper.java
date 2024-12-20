package com.example.studyedge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.UserData;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Userdata(name TEXT primary key, contact TEXT, dob TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Userdata");
    }

    public Boolean insertuserdata(String name, String contact, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        long result = db.insert("Userdata", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean Updateuserdata (String name, String contact, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("contact",contact);
        contentValues.put("dob",dob);
        Cursor cursor = db.rawQuery("select * from Userdata where name=?",new String[]{name});


        if(cursor.getCount()>0){

            long result = db.update("Userdata",contentValues,"name=?",new String[]{name});
            if(result == -1){
                return false;
            }
            else{
                return true;
            }
        }
        else {
            return false;
        }
    }
    public Boolean deletedata (String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from Userdata where name=?",new String[]{name});
        if(cursor.getCount()>0){

            long result = db.delete("Userdata","name=?",new String[]{name});
            if(result == -1){
                return false;
            }
            else{
                return true;
            }
        }
        else {
            return false;
        }
    }
    public Cursor getdata (){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from Userdata ", null);
        return cursor;
    }}

