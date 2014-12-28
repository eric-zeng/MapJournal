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

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The Point class represents a single location that a user has added to a trip.
 * Each Point contains information about the precise geographic location, 
 * user specified text fields such as the title and journal entry, and a list
 * of associated pictures and videos. 
 * @author Eric Zeng
 */
public class Point {
  private long id;                   // Unique identifier for the point
  private String title;             // User-defined name for point (optional)
  private long tripId;              // Unique identifier of the point's trip
  private double latitude;          // Geographic coordinates
  private double longitude;         //
  private double altitude;          //
  private int time;                 // Time point was visited
  private String address;           // User-defined street address (optional)
  private String journal;           // User's text entry for the point.
  private List<MediaItem> media;    // List of associated media items
  
  /**
   * Constructs a new Point from the parameters.
   * @param id The primary key of the Point
   * @param title The user-defined descriptor for the Point. Can be null if
   *              user leaves field empty.
   * @param trip The trip the point is associated with
   * @param latitude Latitude of the point's location
   * @param longitude Longitude of the point's location
   * @param time The time when the point was visited (unix time)
   * @param address A user-defined street address for the point. Can be null
   *                if user leaves field empty.
   * @param journal The text entry for the point written by the user. Can be
   *                null if user leaves field empty. 
   * @param media The list of media objects associated with this point. Can be
   *              null if there are no media objects. 
   */
  public Point(long id,
               String title,
               long tripId,
               double latitude,
               double longitude,
               double altitude,
               int time,
               String address,
               String journal,
               List<MediaItem> media) {
    this.id = id;
    this.title = title;
    this.tripId = tripId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
    this.time = time;
    this.address = address;
    this.journal = journal;
    this.media = media;    
  }
  
  /**
   * Get the unique ID (primary key) of the Point.
   * @return the unique ID of this point
   */
  public long getId() {
    return id;
  }
  
  /**
   * Change the ID of the point
   * @param newId the new ID of the point
   */
  public void setId(long newId) {
    id = newId;
  }
  
  /**
   * Get the point's title.
   * @return The title of the point. Will be null if no title is specified. 
   */
  public String getTitle() {
    return title;
  }
  
  /**
   * Sets the point's title to the given string.
   * @param newTitle The new title for the Point. Leave as null if the user
   *                 does not specify a title. 
   */
  public void setTitle(String newTitle) {
    title = newTitle;
  }
  
  /**
   * Get the unique identifier of the Trip the Point is part of.
   * @return the Trip ID of the Point. 
   */
  public long getTripId() {
    return tripId;
  }
  
  /**
   * Set the trip ID to the given ID. Must refer to an existing trip. 
   * @param newTripId the trip ID for the trip the point will be in. 
   */
  public void setTripId(long newTripId) {
    tripId = newTripId;
  }
  
  /**
   * Get the latitude of the Point.
   * @return The latitude of the Point.
   */
  public double getLatitude() {
    return latitude;
  }
  
  /**
   * Set the latitude of point. 
   * @param newLat The new latitude of the point
   * @throws IllegalArgumentException if the latitude is not between -180 and
   *         180 degrees. 
   */
  public void setLatitude(double newLat) {
    if (Math.abs(newLat) > 180.0) {
      throw new IllegalArgumentException("Invalid latitude value: " + newLat);
    }
    latitude = newLat;
  }
  
  /**
   * Get the longitude of the Point.
   * @return the longitude of the Point. 
   */
  public double getLongitude() {
    return longitude;
  }
  
  /**
   * Set the longitude of the Point.
   * @param newLong The new longitude of the Point.
   * @throws IllegalArgumentException if the longitude is not between -180 and
   *         180 degrees.
   */
  public void setLongitude(double newLong) {
    if (Math.abs(newLong) > 180.0) {
      throw new IllegalArgumentException("Invalid longitude value: " + newLong);
    }
    latitude = newLong;
  }
  
  /**
   * Get the altitude of the Point. 
   * @return the altitude of the Point.
   */
  public double getAltitude() {
    return altitude;
  }
  
  /**
   * Set the altitude of the Point
   * @param newAlt the new altitude of the point.
   */
  public void setAltitude(double newAlt) {
    altitude = newAlt;
  }
  
  /**
   * Get the time when the Point was visited. 
   * @return the time in POSIX time. 
   */
  public int getTime() {
    return time;
  }
  
  /**
   * Set the time when the Point was visited.
   * @param newTime the new time in POSIX time. 
   */
  public void setTime(int newTime) {
    if(newTime < 0) {
      throw new IllegalArgumentException("Time must be after 00:00 1/1/1970 : "
                                         + newTime + " (time in POSIX time)");
    }
    time = newTime;
  }
  
  /**
   * Get the address of the point.
   * @return The address of the point. Will be null if no address was specified.
   */
  public String getAddress() {
    return address;
  }
  
  /**
   * Set the address of the point. 
   * @param newAddr The address of the point. Leave as null if user does not
   *                specify an address. 
   */
  public void setAddress(String newAddr) {
    address = newAddr;
  }
  
  /**
   * Get the journal entry for this Point.
   * @return The journal entry for this Point. Returns null if the user did not
   *         write an entry. 
   */
  public String getJournal() {
    return journal;
  }
  
  /**
   * Set the journal entry for this Point.
   * @param newJournal The journal entry for this Point. Leave as null if user
   *                   did not write an entry. 
   */
  public void setJournal(String newJournal) {
    journal = newJournal;
  }
  
  /**
   * Get the list of MediaItems associated with the point. 
   * @return the list of MediaItems associated with the point. 
   */
  public List<MediaItem> getAllMedia() {
    return media;
  }
  
  /**
   * Adds a new MediaItem to the point. 
   * @param newItem The new MediaItem.
   * @throws NullPointerException if newItem is null 
   */
  public void AddMediaItem(MediaItem newItem) {
    if(newItem == null) {
      throw new NullPointerException("Cannot add a null MediaItem to point");
    }
    media.add(newItem);
  }
  
  /**
   * Removes the media item with the given filepath from this point. The item
   * is not removed from the device. 
   * @param filepath The path of the MediaItem to be removed
   * @throws java.util.NoSuchElementException if the Point does not have an item with
   *         the given path.
   * @throws NullPointerException if filepath is null. 
   */
  public void RemoveMediaItem(String filepath) {
    if (filepath == null)
      throw new NullPointerException("filepath of MediaItem cannot be null");
    for (int i = 0; i < media.size(); i++) {
      if(media.get(i).equals(filepath)) {
        media.remove(i);
        return;
      }
    }
    throw new NoSuchElementException("No MediaItem with the given file path");
  }
}
