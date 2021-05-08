package com.example.geem.extra;

import java.io.Serializable;
import java.util.Date;

public class ShivankUserItems implements Serializable
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
 
 public ShivankUserItems()
 {
  //Do not delete
 }
 
 public ShivankUserItems(String address, String category, String description, String image, String title, String userid, String geoHash, boolean isAvailable, double latitude, double longitude)
 {
  this.address = address;
  this.category = category;
  this.description = description;
  this.image = image;
  this.title = title;
  this.userid = userid;
  this.geohash = geoHash;
  this.isAvailable = isAvailable;
  this.latitude = latitude;
  this.longitude = longitude;
 }
 
 @Override
 public String toString()
 {
  return "ShivankUserItems{" + "address='" + address + "'\n" + ", category='" + category + "'\n" + ", description='" + description + "'\n" + ", image='" + image + "'\n" + ", timestamp=" + timestamp + ", title='" + title + "'\n" + ", userid='" + userid + "'\n" + ", geohash='" + geohash + "'\n" + ", isAvailable=" + isAvailable + ", latitude=" + latitude + ", longitude=" + longitude + '}';
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
 
 public void setGeohash(String geoHash)
 {
  this.geohash = geoHash;
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
}