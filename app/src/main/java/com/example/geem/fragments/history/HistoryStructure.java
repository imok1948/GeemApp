package com.example.geem.fragments.history;


import android.util.Log;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HistoryStructure
{
 
 public static final String OWNER_ADDRESS = "Address";
 public static final String POST_CATEGORY = "Category";
 public static final String POST_DESCRIPTION = "Description";
 public static final String POST_IMAGES = "Image";
 public static final String POST_UPLOADING_TIME = "Timestamp";
 public static final String POST_TITLE = "Title";
 public static final String OWNER_USER_ID = "UserId";
 public static final String POST_LOCATION_GEO_HASH = "geohash";
 public static final String ITEM_STATUS = "isAvailable";
 public static final String ITEM_GPS_LATITUDE = "latitude";
 public static final String ITEM_GPS_LONGITUDE = "longitude";
 public static final String ITEM_AVAILABLE_PLACE = "Address";
 
 private String historyId, title, description, place, address, type;
 private List<String> photoUrl;
 private Timestamp timestamp;
 private int totalItems = 0;
 private boolean itemTaken = false;
 
 public HistoryStructure()
 {
 }
 
 public HistoryStructure(String historyId, String title, String description, String place, String address, List<String> photoUrl, Timestamp timestamp, int totalItems, String type, boolean itemTaken)
 {
  this.photoUrl = new LinkedList<>();
  this.historyId = historyId;
  this.title = title;
  this.description = description;
  this.place = place;
  this.address = address;
  this.timestamp = timestamp;
  this.totalItems = totalItems;
  this.type = type;
  this.itemTaken = itemTaken;
  
  for(String url : photoUrl)
  {
   this.photoUrl.add(url);
  }
 }
 
 public void verbose()
 {
  final String VERBOSE_NAME = "HISTORY_VERBOSE";
  
  try
  {
   Log.i(VERBOSE_NAME, "Title : " + title);
   Log.i(VERBOSE_NAME, "Description : " + description);
   Log.i(VERBOSE_NAME, "Place : " + place);
   Log.i(VERBOSE_NAME, "Address : " + address);
   Log.i(VERBOSE_NAME, "Timestamp : " + (new SimpleDateFormat("dd-MM:hh-SS")).format(new Date(timestamp.getNanoseconds())));
   Log.i(VERBOSE_NAME, "TotalItems : " + totalItems);
   Log.i(VERBOSE_NAME, "Type : " + type);
   Log.i(VERBOSE_NAME, "Item Taken : " + itemTaken);
   Log.i(VERBOSE_NAME, "ID  : " + historyId);
   
   if(photoUrl != null)
   {
    for(String s : photoUrl)
    {
     Log.i(VERBOSE_NAME, "Photo URLS : " + s);
    }
   }
   Log.i(VERBOSE_NAME, "..................................................................");
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
 }
 
 public boolean isItemTaken()
 {
  return itemTaken;
 }
 
 public void setItemTaken(boolean itemTaken)
 {
  this.itemTaken = itemTaken;
 }
 
 public String getType()
 {
  return type;
 }
 
 public void setType(String type)
 {
  this.type = type;
 }
 
 public String getHistoryId()
 {
  return historyId;
 }
 
 public void setHistoryId(String historyId)
 {
  this.historyId = historyId;
 }
 
 public String getTitle()
 {
  return title;
 }
 
 public void setTitle(String title)
 {
  this.title = title;
 }
 
 public String getDescription()
 {
  return description;
 }
 
 public void setDescription(String description)
 {
  this.description = description;
 }
 
 public String getPlace()
 {
  return place;
 }
 
 public void setPlace(String place)
 {
  this.place = place;
 }
 
 public String getAddress()
 {
  return address;
 }
 
 public void setAddress(String address)
 {
  this.address = address;
 }
 
 public List<String> getPhotoUrl()
 {
  return photoUrl;
 }
 
 public void setPhotoUrl(List<String> photoUrl)
 {
  this.photoUrl = photoUrl;
 }
 
 public Timestamp getTimestamp()
 {
  return timestamp;
 }
 
 public void setTimestamp(Timestamp timestamp)
 {
  this.timestamp = timestamp;
 }
 
 public int getTotalItems()
 {
  return totalItems;
 }
 
 public void setTotalItems(int totalItems)
 {
  this.totalItems = totalItems;
 }
}

