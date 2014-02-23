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

import android.provider.BaseColumns;

public class MapJournalDbContract {
  public MapJournalDbContract() {}
  
  public static abstract class TripEntry implements BaseColumns {
    public static final String TABLE_NAME = "MapJournalTrips";
    public static final String COLUMN_NAME_NAME = "Name";
    public static final String COLUMN_NAME_DESC = "Description";
  }
  
  public static abstract class PointEntry implements BaseColumns {
    public static final String TABLE_NAME = "MapJournalPoint";
    public static final String COLUMN_NAME_TRIP = "TripId";
    public static final String COLUMN_NAME_TITLE = "Title";
    public static final String COLUMN_NAME_LATITUDE = "Latitude";
    public static final String COLUMN_NAME_LONGITUDE = "Longitude";
    public static final String COLUMN_NAME_ALTITUDE = "Altitude";
    public static final String COLUMN_NAME_TIME = "Time";
    public static final String COLUMN_NAME_ADDRESS = "Address";
    public static final String COLUMN_NAME_JOURNAL = "Journal";
  }
  
  public static abstract class MediaEntry implements BaseColumns {
    public static final String TABLE_NAME = "MapJournalMedia";
    public static final String COLUMN_NAME_POINT_ID = "PointId";
    public static final String COLUMN_NAME_CAPTION = "Caption";
    public static final String COLUMN_NAME_PATH = "Path";
  }
 
}
