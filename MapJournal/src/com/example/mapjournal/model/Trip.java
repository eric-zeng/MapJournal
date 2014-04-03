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

/**
 * The Trip class contains a collection of Points and other user-specified
 * information describing the trip. 
 * @author ericzeng
 *
 */
public class Trip {
  private long id;
  private String name;
  private String description;
  private List<Point> points;
  
  /**
   * Constructs a new trip with the given parameters. 
   * @param id The unique identifier of this trip
   * @param name The user-specified name for the trip
   * @param description The user-specified desription for the trip
   * @param points The list of Points visited on this trip
   */
  public Trip(long id, String name, String description, List<Point> points) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.points = points;
  }
  
  /**
   * Returns the id of the Trip.
   * @return The id of the Trip. 
   */
  public long getId() {
    return id;
  }
  
  /**
   * Sets the id of the trip to the given value. 
   * @param newId The id of the trip
   */
  public void setId(long newId) {
    id = newId;
  }
  
  /**
   * Returns the name of the trip.
   * @return The name of the trip
   */
  public String getName() {
    return name;
  }
  
  /**
   * Renames the trip.
   * @param newName The new name for the trip
   */
  public void setName(String newName) {
    name = newName;
  }
  
  /**
   * Returns the description of the trip.
   * @return The description of the trip.
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * Returns all Points from the trip. 
   * @return A list of Points visited on the Trip.
   */
  public List<Point> getPoints() {
    return points;
  }
}
