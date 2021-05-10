package com.example.geem.fragments.browse.feeds;

import java.util.Date;

public class FeedsTemplate
{
 private String address;
 private String category;
 private String description;
 private String image;
 private Date timestamp;
 private String title;
 private String userid;
 private String geohash;
 private boolean isAvailable;
 private double latitude;
 private double longitude;
 private String itemId;
 private float distance;
 
 
 public FeedsTemplate()
 {
 }
 
 public FeedsTemplate(String address, String category, String description, String image, Date timestamp, String title, String userid, String geohash, boolean isAvailable, double latitude, double longitude, String itemId, float distance)
 {
  this.address = address;
  this.category = category;
  this.description = description;
  this.image = image;
  this.timestamp = timestamp;
  this.title = title;
  this.userid = userid;
  this.geohash = geohash;
  this.isAvailable = isAvailable;
  this.latitude = latitude;
  this.longitude = longitude;
  this.itemId = itemId;
  this.distance = distance;
 }
 
 public float getDistance()
 {
  return distance;
 }
 
 public void setDistance(float distance)
 {
  this.distance = distance;
 }
 
 public String getAddress()
 {
  return address;
 }
 
 public void setAddress(String address)
 {
  this.address = address;
 }
 
 public String getCategory()
 {
  return category;
 }
 
 public void setCategory(String category)
 {
  this.category = category;
 }
 
 public String getDescription()
 {
  return description;
 }
 
 public void setDescription(String description)
 {
  this.description = description;
 }
 
 public String getImage()
 {
  return image;
 }
 
 public void setImage(String image)
 {
  this.image = image;
 }
 
 public Date getTimestamp()
 {
  return timestamp;
 }
 
 public void setTimestamp(Date timestamp)
 {
  this.timestamp = timestamp;
 }
 
 public String getTitle()
 {
  return title;
 }
 
 public void setTitle(String title)
 {
  this.title = title;
 }
 
 public String getUserid()
 {
  return userid;
 }
 
 public void setUserid(String userid)
 {
  this.userid = userid;
 }
 
 public String getGeohash()
 {
  return geohash;
 }
 
 public void setGeohash(String geohash)
 {
  this.geohash = geohash;
 }
 
 public boolean isAvailable()
 {
  return isAvailable;
 }
 
 public void setAvailable(boolean available)
 {
  isAvailable = available;
 }
 
 public double getLatitude()
 {
  return latitude;
 }
 
 public void setLatitude(double latitude)
 {
  this.latitude = latitude;
 }
 
 public double getLongitude()
 {
  return longitude;
 }
 
 public void setLongitude(double longitude)
 {
  this.longitude = longitude;
 }
 
 public String getItemId()
 {
  return itemId;
 }
 
 public void setItemId(String itemId)
 {
  this.itemId = itemId;
 }
}
