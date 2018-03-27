package com.example.asus.ceng427hw1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Asus on 16.03.2018.
 */

public class ListDB extends SQLiteOpenHelper {

    private static final String dbName="ListDB";
    private static final int dbVersion=1;
    public static final String dbTable="Tasks";
    public static final String dbColumn="TaskName";


    public ListDB(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);", dbTable, dbColumn);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", dbTable);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertTask(String Task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbColumn, Task);
        db.insertWithOnConflict(dbTable,null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
    public void deleteTask(String Task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dbTable,dbColumn+" = ?",new String[]{Task});
        db.close();
    }
    public void updateTask(String Task, String updatedTask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataToUpdate = new ContentValues();
        dataToUpdate.put(dbColumn,updatedTask);
        db.update(dbTable, dataToUpdate,dbColumn+" = ?", new String[]{Task});
        db.close();
    }
    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(dbTable, new String[]{dbColumn},null,null,null,null,null);
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(dbColumn);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }
}
