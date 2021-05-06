package com.example.geem.fragments.browse.messages.activity;

import com.example.geem.extra.TimeDetails;

public class MessageTemplateForAdapter
{
 private int id;
 private boolean sentByMe = true;
 private String data;
 private TimeDetails timeDetails;
 
 public MessageTemplateForAdapter(int id, boolean sentByMe, String data, TimeDetails timeDetails)
 {
  this.id = id;
  this.sentByMe = sentByMe;
  this.data = data;
  this.timeDetails = timeDetails;
 }
 
 
 @Override
 public String toString()
 {
  return "Message{" + "id=" + id + ", sentByMe=" + sentByMe + ", data='" + data + '\'' + ", timeDetails=" + timeDetails + '}';
 }
 
 public int getId()
 {
  return id;
 }
 
 public void setId(int id)
 {
  this.id = id;
 }
 
 public boolean isSentByMe()
 {
  return sentByMe;
 }
 
 public void setSentByMe(boolean sentByMe)
 {
  this.sentByMe = sentByMe;
 }
 
 public String getData()
 {
  return data;
 }
 
 public void setData(String data)
 {
  this.data = data;
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
