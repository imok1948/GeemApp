package com.example.geem.fragments.browse.history.history;

public class TemplateBrowseHistory
{
 String title;
 String description;
 String itemId;
 String imageUrl;
 long timestamp;
 
 public TemplateBrowseHistory(String title, String description, String itemId, String imageUrl, long timestamp)
 {
  this.title = title;
  this.description = description;
  this.itemId = itemId;
  this.imageUrl = imageUrl;
  this.timestamp = timestamp;
 }
 
 
 @Override
 public String toString()
 {
  return "TemplateBrowseHistory{" + "title='" + title + "'\n" + ", description='" + description + "'\n" + ", itemId='" + itemId + "'\n" + ", imageUrl='" + imageUrl + "'\n" + ", timestamp=" + timestamp + '}';
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
 
 public String getItemId()
 {
  return itemId;
 }
 
 public void setItemId(String itemId)
 {
  this.itemId = itemId;
 }
 
 public String getImageUrl()
 {
  return imageUrl;
 }
 
 public void setImageUrl(String imageUrl)
 {
  this.imageUrl = imageUrl;
 }
 
 public long getTimestamp()
 {
  return timestamp;
 }
 
 public void setTimestamp(long timestamp)
 {
  this.timestamp = timestamp;
 }
}
