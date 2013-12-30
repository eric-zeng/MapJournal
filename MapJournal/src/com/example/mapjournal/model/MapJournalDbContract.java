package com.example.mapjournal.model;

import android.provider.BaseColumns;

public class MapJournalDbContract {
  public MapJournalDbContract() {}
  
  public static abstract class PointEntry implements BaseColumns {
    public static final String TABLE_NAME = "MapJournalPoints";
    public static final String COLUMN_NAME_TITLE = "Title";
    public static final String COLUMN_NAME_TRIP = "Trip";
    public static final String COLUMN_NAME_LATITUDE = "Latitude";
    public static final String COLUMN_NAME_LONGITUDE = "Longitude";
    public static final String COLUMN_NAME_ALTITUDE = "Altitude";
    public static final String COLUMN_NAME_TIME = "Time";
    public static final String COLUMN_NAME_ADDRESS = "Address";
    public static final String COLUMN_NAME_TEXT = "Text";
  }
  
  public static abstract class MediaEntry implements BaseColumns {
    public static final String TABLE_NAME = "MapJournalMedia";
    public static final String COLUMN_NAME_POINT_ID = "PointId";
    public static final String COLUMN_NAME_CAPTION = "Caption";
    public static final String COLUMN_NAME_PATH = "Path";
  }
}
