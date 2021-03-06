package com.example.laptrinhandroid_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="location_user";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "locations";

    private static final String LOCATION_ID="id";
    private static final String LOCATION_NAME="name";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStringCreate = String.format("create table %s(%s integer primary key AUTOINCREMENT, %s nvarchar(50))",TABLE_NAME,LOCATION_ID,LOCATION_NAME);
        db.execSQL(sqlStringCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlStringDrop = "drop table if exists "+TABLE_NAME;
        db.execSQL(sqlStringDrop);
        onCreate(db);
    }
    public void addNewLocation(Location location){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_NAME,location.getName());
        database.insert(TABLE_NAME,null,contentValues);
        database.close();
    }
    public List<Location> getLocations(){
        List<Location> list = new ArrayList<>();
        String sqlStringGetLocations = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlStringGetLocations,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Location location = new Location(cursor.getInt(0),cursor.getString(1));
            list.add(location);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public void removeLocation(int locationID){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_NAME,LOCATION_ID +"=?",new String[]{String.valueOf(locationID)});
        db.close();
    }
    public void updateLocation(Location location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LOCATION_NAME,location.getName());
        db.update(TABLE_NAME,values,LOCATION_ID + "=?",new String[]{String.valueOf(location.getId())});
        db.close();
    }
}
