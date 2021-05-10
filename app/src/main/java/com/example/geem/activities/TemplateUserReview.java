package com.example.geem.activities;

public class TemplateUserReview
{
 private float rating;
 private String name;
 private String content;
 private long timestamp;
 private String image;
 private String profileId;
 
 public TemplateUserReview(float rating, String name, String content, long timestamp, String image, String profileId)
 {
  this.rating = rating;
  this.name = name;
  this.content = content;
  this.timestamp = timestamp;
  this.image = image;
  this.profileId = profileId;
 }
 
 @Override
 public String toString()
 {
  return "TemplateUserReview{" + "rating=" + rating + ", name='" + name + "'\n" + ", content='" + content + "'\n" + ", timestamp=" + timestamp + ", image='" + image + "'\n" + ", profileId='" + profileId + "'\n" + '}';
 }
 
 public float getRating()
 {
  return rating;
 }
 
 public void setRating(float rating)
 {
  this.rating = rating;
 }
 
 public String getName()
 {
  return name;
 }
 
 public void setName(String name)
 {
  this.name = name;
 }
 
 public String getContent()
 {
  return content;
 }
 
 public void setContent(String content)
 {
  this.content = content;
 }
 
 public long getTimestamp()
 {
  return timestamp;
 }
 
 public void setTimestamp(long timestamp)
 {
  this.timestamp = timestamp;
 }
 
 public String getImage()
 {
  return image;
 }
 
 public void setImage(String image)
 {
  this.image = image;
 }
 
 public String getProfileId()
 {
  return profileId;
 }
 
 public void setProfileId(String profileId)
 {
  this.profileId = profileId;
 }
}
