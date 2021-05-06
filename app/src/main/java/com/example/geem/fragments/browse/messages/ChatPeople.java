package com.example.geem.fragments.browse.messages;

import com.example.geem.extra.TimeDetails;

public class ChatPeople
{
 private int profilePicture;
 private String name;
 private String message;
 private TimeDetails timeDetails;
 
 public ChatPeople(int profilePicture, String name, String message, TimeDetails timeDetails)
 {
  this.profilePicture = profilePicture;
  this.name = name;
  this.message = message;
  this.timeDetails = timeDetails;
 }
 
 public ChatPeople()
 {
 
 }
 
 
 @Override
 public String toString()
 {
  return "ChatPeople{" + "profilePicture=" + profilePicture + ", name='" + name + '\'' + ", message='" + message + '\'' + ", timeDetails=" + timeDetails + '}';
 }
 
 //Getter setters ....
 public int getProfilePicture()
 {
  return profilePicture;
 }
 
 public void setProfilePicture(int profilePicture)
 {
  this.profilePicture = profilePicture;
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
}
