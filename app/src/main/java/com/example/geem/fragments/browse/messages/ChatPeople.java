package com.example.geem.fragments.browse.messages;

import com.example.geem.extra.TimeDetails;

public class ChatPeople
{
 private String profilePictureUrl;
 private String name;
 private String message;
 private TimeDetails timeDetails;
 private String userId;
 
 public ChatPeople(String profilePictureUrl, String name, String message, TimeDetails timeDetails, String userId)
 {
  this.profilePictureUrl = profilePictureUrl;
  this.name = name;
  this.message = message;
  this.timeDetails = timeDetails;
  this.userId = userId;
 }
 
 @Override
 public String toString()
 {
  return "ChatPeople{" + "profilePictureUrl='" + profilePictureUrl + "'\n" + ", name='" + name + "'\n" + ", message='" + message + "'\n" + ", timeDetails=" + timeDetails + ", userId='" + userId + "'\n" + '}';
 }
 
 public String getProfilePictureUrl()
 {
  return profilePictureUrl;
 }
 
 public void setProfilePictureUrl(String profilePictureUrl)
 {
  this.profilePictureUrl = profilePictureUrl;
 }
 
 public String getName()
 {
  return name;
 }
 
 public void setName(String name)
 {
  this.name = name;
 }
 
 public String getMessage()
 {
  return message;
 }
 
 public void setMessage(String message)
 {
  this.message = message;
 }
 
 public TimeDetails getTimeDetails()
 {
  return timeDetails;
 }
 
 public void setTimeDetails(TimeDetails timeDetails)
 {
  this.timeDetails = timeDetails;
 }
 
 public String getUserId()
 {
  return userId;
 }
 
 public void setUserId(String userId)
 {
  this.userId = userId;
 }
}