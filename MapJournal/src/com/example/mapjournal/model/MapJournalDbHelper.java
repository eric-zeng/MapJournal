/*  Copyright 2014 Eric Zeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.example.mapjournal.model;

import com.example.mapjournal.model.MapJournalDbContract.MediaEntry;
import com.example.mapjournal.model.MapJournalDbContract.PointEntry;
import com.example.mapjournal.model.MapJournalDbContract.TripEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MapJournalDbHelper extends SQLiteOpenHelper {
  
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "MapJournal.db";
  
  private static final String CREATE_TABLE_TRIP =
      "CREATE TABLE " + TripEntry.TABLE_NAME + "(" +
      TripEntry._ID + " INTEGER PRIMARY KEY" + "," +  
      TripEntry.COLUMN_NAME_NAME + " TEXT" + "," +
      TripEntry.COLUMN_NAME_DESC + " TEXT" + 
      ")";
  
  private static final String CREATE_TABLE_POINT = 
      "CREATE TABLE " + PointEntry.TABLE_NAME + "(" + 
      PointEntry._ID + " INTEGER PRIMARY KEY" + "," +
      PointEntry.COLUMN_NAME_TRIP + " INTEGER" + "," +
      PointEntry.COLUMN_NAME_TITLE + " TEXT" + "," +
      PointEntry.COLUMN_NAME_LATITUDE + " REAL" + "," +
      PointEntry.COLUMN_NAME_LONGITUDE + " REAL" + "," +
      PointEntry.COLUMN_NAME_ALTITUDE + " REAL" + "," +
      PointEntry.COLUMN_NAME_TIME + " INTEGER" + "," +
      PointEntry.COLUMN_NAME_ADDRESS + " TEXT" + "," +
      PointEntry.COLUMN_NAME_JOURNAL + " TEXT" + "," +
      "FOREIGN KEY (" + PointEntry.COLUMN_NAME_TRIP + ") REFERENCES " + 
        TripEntry.TABLE_NAME + "(" + TripEntry._ID + ")" +
      ")";
  
  private static final String CREATE_TABLE_MEDIA = 
      "CREATE TABLE " + MediaEntry.TABLE_NAME + "(" +
      MediaEntry._ID + " INTEGER PRIMARY KEY" + "," +
      MediaEntry.COLUMN_NAME_POINT_ID + " INTEGER" + "," +
      MediaEntry.COLUMN_NAME_CAPTION + " TEXT" + "," +
      MediaEntry.COLUMN_NAME_PATH + " TEXT" + "," +
      "FOREIGN KEY (" + MediaEntry.COLUMN_NAME_POINT_ID + ") REFERENCES " +
        PointEntry.TABLE_NAME + "(" + PointEntry._ID + ")" +
      ")";
  
  public MapJournalDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_TRIP);
    db.execSQL(CREATE_TABLE_POINT);
    db.execSQL(CREATE_TABLE_MEDIA);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
    // TODO(ericzeng): implement when there are new database versions
    
  }

}
