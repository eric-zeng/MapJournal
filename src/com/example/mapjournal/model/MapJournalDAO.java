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

import java.util.ArrayList;
import java.util.List;

import com.example.mapjournal.model.MapJournalDbContract.MediaEntry;
import com.example.mapjournal.model.MapJournalDbContract.PointEntry;
import com.example.mapjournal.model.MapJournalDbContract.TripEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Database Access Object for the MapJournal databases. 
 * @author ericzeng
 *
 */
public class MapJournalDAO {
  private SQLiteDatabase db;
  private MapJournalDbHelper openHelper;
  
  // All columns in the Point table
  private static final String[] POINT_COLUMNS = { 
      PointEntry._ID,
      PointEntry.COLUMN_NAME_TITLE,
      PointEntry.COLUMN_NAME_TRIP,
      PointEntry.COLUMN_NAME_LATITUDE,
      PointEntry.COLUMN_NAME_LONGITUDE,
      PointEntry.COLUMN_NAME_ALTITUDE,
      PointEntry.COLUMN_NAME_TIME,
      PointEntry.COLUMN_NAME_ADDRESS,
      PointEntry.COLUMN_NAME_JOURNAL};
  
  // All columns in the Media table
  private static final String[] MEDIA_COLUMNS = {
      MediaEntry._ID,
      MediaEntry.COLUMN_NAME_POINT_ID,
      MediaEntry.COLUMN_NAME_CAPTION,
      MediaEntry.COLUMN_NAME_PATH
  };
  
  // All columns in the Trip table
  private static final String[] TRIP_COLUMNS = {
      TripEntry._ID,
      TripEntry.COLUMN_NAME_NAME,
      TripEntry.COLUMN_NAME_DESC
  };
  
  /**
   * Create a new Data Access Object
   * @param context
   */
  public MapJournalDAO(Context context) {
    openHelper = MapJournalDbHelper.getInstance(context);
  }
  
  /**
   * Opens a new connection to the database
   * @throws SQLiteException
   */
  public void open() throws SQLiteException {
    db = openHelper.getWritableDatabase();
  }
  
  /**
   * Closes the connection to the database
   */
  public void close() {
    openHelper.close();
  }
  
  /**
   * Creates a new Point entry in the Point database. The id of the Point object
   * will be set to the id of the corresponding entry in the database. 
   * MediaItems associated with point should be added to the database manually
   * with createMedia(). 
   * @param point The Point object to be added to the database. 
   */
  public void createPoint(Point point) {    
    // Insert the Point into the database
    ContentValues pointValues = new ContentValues();
    putPointValues(pointValues, point);
    
    long newId = db.insert(PointEntry.TABLE_NAME, null, pointValues);
    point.setId(newId);
  }
  
  /**
   * Retrieves the Point entry with the given id and puts the data into a
   * Point object. The list of MediaItems should be populated manually using
   * getMediaByPoint(id). 
   * @param id The id of the Point to be retrieved
   * @return A Point object with the given id
   */
  public Point getPoint(long id) {
    String pointSelection = PointEntry._ID + " LIKE ?";
    String[] pointSelectionArgs = {String.valueOf(id)};
    Cursor c = db.query(PointEntry.TABLE_NAME,
                        POINT_COLUMNS,
                        pointSelection,
                        pointSelectionArgs,
                        null,
                        null,
                        null);
    
    Point result = new Point(
      c.getLong(c.getColumnIndexOrThrow(PointEntry._ID)),
      c.getString(c.getColumnIndexOrThrow(PointEntry.COLUMN_NAME_TITLE)),
      c.getLong(c.getColumnIndexOrThrow(PointEntry.COLUMN_NAME_TRIP)),
      c.getDouble(c.getColumnIndexOrThrow(PointEntry.COLUMN_NAME_LATITUDE)),
      c.getDouble(c.getColumnIndexOrThrow(PointEntry.COLUMN_NAME_LONGITUDE)),
      c.getDouble(c.getColumnIndexOrThrow(PointEntry.COLUMN_NAME_ALTITUDE)),
      c.getInt(c.getColumnIndexOrThrow(PointEntry.COLUMN_NAME_TIME)),
      c.getString(c.getColumnIndexOrThrow(PointEntry.COLUMN_NAME_ADDRESS)),
      c.getString(c.getColumnIndexOrThrow(PointEntry.COLUMN_NAME_JOURNAL)),
      null);  // MediaList is not initialized until retrieved
      
    return result;          
  }
  
  /**
   * Update the Point entry in the database with the current Point's value. 
   * MediaItems associated with the point need to be updated manually.
   * @param point The Point to be updated in the database.
   */
  public void updatePoint(Point point) {
    ContentValues pointValues = new ContentValues();
    putPointValues(pointValues, point);
    
    String pointSelection = PointEntry._ID + " LIKE ?";
    String[] pointSelectionArgs = {String.valueOf(point.getId())};
    
    db.update(PointEntry.TABLE_NAME,
              pointValues,
              pointSelection,
              pointSelectionArgs);
  }
  
  /**
   * Delete the given Point from the database. MediaItems associated with the
   * point wil also be deleted to maintain referential integrity. 
   * @param point The Point be deleted from the database. 
   */
  public void deletePoint(Point point) {
    String pointSelection = PointEntry._ID + " LIKE ?";
    String[] pointSelectionArgs = {String.valueOf(point.getId())};
    db.delete(PointEntry.TABLE_NAME, pointSelection, pointSelectionArgs);
    
    List<MediaItem> items = point.getAllMedia();
    for (MediaItem item : items) {
      deleteMedia(item);
    }
  }
  
  /**
   * Creates a new entry in the Media table using the given MediaItem. 
   * @param item The MediaItem to add to the database
   */
  public void createMedia(MediaItem item) {
    ContentValues mediaValues = new ContentValues();
    putMediaValues(mediaValues, item);
    
    long newId = db.insert(MediaEntry.TABLE_NAME, null, mediaValues);
    item.setId(newId);
  }
  
  /**
   * Retrieves the MediaItem entry with the given id.
   * @param id The id of the MediaItem to retrieve
   * @return A MediaItem object with the given id
   */
  public MediaItem getMedia(long id) {
    String mediaSelection = MediaEntry._ID + " LIKE ?";
    String[] mediaSelectionArgs = {String.valueOf(id)};
    
    Cursor c = db.query(MediaEntry.TABLE_NAME,
                        MEDIA_COLUMNS,
                        mediaSelection,
                        mediaSelectionArgs,
                        null,
                        null,
                        null);
    
    MediaItem item = new MediaItem(
      c.getLong(c.getColumnIndexOrThrow(MediaEntry._ID)),
      c.getLong(c.getColumnIndexOrThrow(MediaEntry.COLUMN_NAME_POINT_ID)),
      c.getString(c.getColumnIndexOrThrow(MediaEntry.COLUMN_NAME_PATH)),
      c.getString(c.getColumnIndexOrThrow(MediaEntry.COLUMN_NAME_CAPTION)));
    
    return item;
  }
  
  /**
   * Retrieves all MediaItems associated with the Point with the given id
   * @param pointId The id of the Point containing the MediaITems
   * @return A list of MediaItems associated with the point. 
   */
  public List<MediaItem> getMediaByPoint(long pointId) {
    String mediaSelection = MediaEntry.COLUMN_NAME_POINT_ID + " LIKE ?";
    String[] mediaSelectionArgs = { String.valueOf(pointId) };
    Cursor d = db.query(MediaEntry.TABLE_NAME,
                        MEDIA_COLUMNS,
                        mediaSelection,
                        mediaSelectionArgs,
                        null,
                        null,
                        null);

    List<MediaItem> mediaList = new ArrayList<MediaItem>();
    
    // Loop through every id, get the MediaItem in each iteration
    while (!d.isAfterLast()) {
      MediaItem item = 
          getMedia(d.getLong(d.getColumnIndexOrThrow(MediaEntry._ID)));
      mediaList.add(item);
      d.moveToNext();
    }
    return mediaList;
  }
  
  /**
   * Updates the given MediaItem's entry in the Database/
   * @param item The MediaItem to be updated. 
   */
  public void updateMedia(MediaItem item) {
    ContentValues mediaValues = new ContentValues();
    putMediaValues(mediaValues, item);
    
    String mediaSelection = MediaEntry._ID + " LIKE ?";
    String[] mediaSelectionArgs = {String.valueOf(item.getId())};
    
    db.update(MediaEntry.TABLE_NAME,
              mediaValues,
              mediaSelection,
              mediaSelectionArgs);
  }
  
  /**
   * Deletes the given MediaItem from the database.
   * @param item The MediaItem to be deleted
   */
  public void deleteMedia(MediaItem item) {
    String mediaSelection = MediaEntry._ID + " LIKE ?";
    String[] mediaSelectionArgs = { String.valueOf(item.getId()) };
    
    db.delete(MediaEntry.TABLE_NAME, mediaSelection, mediaSelectionArgs);
  }
  
  /**
   * Creates a new Trip entry in the database. Points in the Trip object are
   * not added to the database. 
   * @param trip The Trip to add to the database. 
   */
  public void createTrip(Trip trip) {
    ContentValues tripValues = new ContentValues();
    putTripValues(tripValues, trip);
    
    long newId = db.insert(TripEntry.TABLE_NAME, null, tripValues);
    trip.setId(newId);
  }
  
  /**
   * Retrieves a trip from the database and puts the data into a Trip object.
   * @param id The id of the Trip to receive
   * @return A Trip object containing the data from the selected entry
   */
  public Trip getTrip(long id) {
    String tripSelection = TripEntry._ID + " LIKE ?";
    String[] tripSelectionArgs = { String.valueOf(id) };
    
    Cursor c = db.query(TripEntry.TABLE_NAME,
                        TRIP_COLUMNS,
                        tripSelection,
                        tripSelectionArgs,
                        null,
                        null,
                        null);
    
    Trip result = new Trip(
        c.getLong(c.getColumnIndexOrThrow(TripEntry._ID)),
        c.getString(c.getColumnIndexOrThrow(TripEntry.COLUMN_NAME_NAME)),
        c.getString(c.getColumnIndexOrThrow(TripEntry.COLUMN_NAME_DESC)),
        null);
    return result;
  }
  
  /**
   * Retrieves all Trips stored in the database.
   * @return A list containing every Trip in the database. 
   */
  public List<Trip> getAllTrips() {
    Cursor c = db.query(TripEntry.TABLE_NAME,
                        TRIP_COLUMNS,
                        null,
                        null,
                        null,
                        null,
                        null);
    List<Trip> trips = new ArrayList<Trip>();
    while (!c.isAfterLast()) {
      Trip t = new Trip(
        c.getLong(c.getColumnIndexOrThrow(TripEntry._ID)),
        c.getString(c.getColumnIndexOrThrow(TripEntry.COLUMN_NAME_NAME)),
        c.getString(c.getColumnIndexOrThrow(TripEntry.COLUMN_NAME_DESC)),
        null);
      trips.add(t);
      c.moveToNext();
    }
    return trips;
  }
  
  /**
   * Updates the given Trip in the database. 
   * @param trip The Trip to be updated. 
   */
  public void updateTrip(Trip trip) {
    ContentValues tripValues = new ContentValues();
    putTripValues(tripValues, trip);
    
    String tripSelection = TripEntry._ID + " LIKE ?";
    String[] tripSelectionArgs = { String.valueOf(trip.getId()) };
    
    db.update(TripEntry.TABLE_NAME,
              tripValues,
              tripSelection,
              tripSelectionArgs);
  }
  
  /**
   * Deletes the given Trip from the database.
   * @param trip The Trip to be deleted.
   */
  public void deleteTrip(Trip trip) {
    String tripSelection = TripEntry._ID + " LIKE ?";
    String[] tripSelectionArgs = { String.valueOf(trip.getId()) };
    db.delete(TripEntry.TABLE_NAME, tripSelection, tripSelectionArgs);
  }
  
  /**
   * Helper method to put every field of a Point into a ContentValues object.
   * @param values The ContentValues to be filled
   * @param point The Point to retrieve data from
   */
  private void putPointValues(ContentValues values, Point point) {
    values.put(PointEntry.COLUMN_NAME_TITLE, point.getTitle());
    values.put(PointEntry.COLUMN_NAME_TRIP, point.getTripId());
    values.put(PointEntry.COLUMN_NAME_LATITUDE, point.getLatitude());
    values.put(PointEntry.COLUMN_NAME_LONGITUDE, point.getLongitude());
    values.put(PointEntry.COLUMN_NAME_ALTITUDE, point.getAltitude());
    values.put(PointEntry.COLUMN_NAME_TIME, point.getTime());
    values.put(PointEntry.COLUMN_NAME_ADDRESS, point.getAddress());
    values.put(PointEntry.COLUMN_NAME_JOURNAL, point.getJournal());
  }
  
  /**
   * Helper method to put every field of a MediaItem into a ContentValues object
   * @param values The ContentValues to be filled
   * @param item The MediaItem to retrieve data from
   */
  private void putMediaValues(ContentValues values, MediaItem item) {
    values.put(MediaEntry.COLUMN_NAME_POINT_ID, item.getPointId());
    values.put(MediaEntry.COLUMN_NAME_CAPTION, item.getCaption());
    values.put(MediaEntry.COLUMN_NAME_PATH, item.getFilePath());
  }
  
  /**
   * Helper method to put every field of a Trip into a ContentValues object.
   * @param values The ContentValues to be filled
   * @param trip The Trip to retrieve data from
   */
  private void putTripValues(ContentValues values, Trip trip) {
    values.put(TripEntry.COLUMN_NAME_NAME, trip.getName());
    values.put(TripEntry.COLUMN_NAME_DESC, trip.getDescription());
  }
  
}
