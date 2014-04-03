package com.example.mapjournal.model;

/**
 * The MediaItem class is used by Point to store pictures, videos, and 
 * associated captions. The class contains the id of the media item,
 * the file path of the image or video, and an optional caption.  
 * @author Eric Zeng
 */
public class MediaItem {
  private long id;            // Unique identifier for the MediaItem
  private long pointId;       // Point that the MediaItem belongs to
  private String filepath;    // Location of media in internal storage
  private String caption;     // User caption for media. null if unspecified
  
  /**
   * Constructs a new media item.
   * @param id The id of the media item. If not retrieving from the database,
   *           leave id as -1. 
   * @param filepath The location of the media in internal storage 
   * @param caption The user's caption for this media. Leave as null if user
   *                did not write a caption. 
   */
  public MediaItem(long id, long pointId, String filepath, String caption) {
    this.id = id;
    this.pointId = pointId;
    this.filepath = filepath;
    this.caption = caption;
  }
  
  /**
   * Get the id of the MediaItem.
   * @return the id of the MediaItem.
   */
  public long getId() {
    return id;
  }
  
  /**
   * Set the id of the MediaItem
   * @param newId
   */
  public void setId(long newId) {
    this.id = newId;
  }
  
  /**
   * Get the id of the Point associated with this MediaItem
   * @return the associated Point id
   */
  public long getPointId() {
    return pointId;
  }
  
  /**
   * Set the associated Point id
   * @param newPointId
   */
  public void setPointId(long newPointId) {
    this.pointId = newPointId;
  }
  
  /**
   * Get the path of the media. 
   * @return the path of the media.
   */
  public String getFilePath() {
    return filepath;
  }
  
  /**
   * Get the caption for the media.
   * @return the caption for the media. null if user did not write a caption
   */
  public String getCaption() {
    return caption;
  }
  
  /**
   * Set the caption.
   * @param newCaption The caption to be used. null if the user did not write
   *                   a caption.
   */
  public void setCaption(String newCaption) {
    caption = newCaption;
  }
}
