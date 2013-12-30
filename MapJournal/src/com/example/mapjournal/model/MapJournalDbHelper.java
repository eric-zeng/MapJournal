package com.example.mapjournal.model;

import com.example.mapjournal.model.MapJournalDbContract.MediaEntry;
import com.example.mapjournal.model.MapJournalDbContract.PointEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MapJournalDbHelper extends SQLiteOpenHelper {
  
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "MapJournal.db";
  
  private static final String CREATE_POINT_TABLE = 
      "CREATE TABLE " + PointEntry.TABLE_NAME + "(" + 
      PointEntry._ID + " INTEGER PRIMARY KEY," +
      PointEntry.COLUMN_NAME_TITLE + " TEXT," +
      PointEntry.COLUMN_NAME_TRIP + " TEXT," +
      PointEntry.COLUMN_NAME_LATITUDE + " REAL," +
      PointEntry.COLUMN_NAME_LONGITUDE + " REAL," +
      PointEntry.COLUMN_NAME_ALTITUDE + " REAL," +
      PointEntry.COLUMN_NAME_TIME + " INTEGER," +
      PointEntry.COLUMN_NAME_ADDRESS + " TEXT," +
      PointEntry.COLUMN_NAME_TEXT + " TEXT" +
      ")";
  
  private static final String CREATE_MEDIA_TABLE = 
      "CREATE TABLE " + MediaEntry.TABLE_NAME + "(" +
      MediaEntry._ID + " INTEGER PRIMARY KEY," +
      MediaEntry.COLUMN_NAME_POINT_ID + " INTEGER," +
      MediaEntry.COLUMN_NAME_CAPTION + " TEXT," +
      MediaEntry.COLUMN_NAME_PATH + " TEXT" +
      ")";
  
  public MapJournalDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_POINT_TABLE);
    db.execSQL(CREATE_MEDIA_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
    // TODO(ericzeng): implement when there are new database versions
    
  }

}
