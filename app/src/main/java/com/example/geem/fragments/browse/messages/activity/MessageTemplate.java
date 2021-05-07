package com.example.geem.fragments.browse.messages.activity;

public class MessageTemplate
{
 private long timestamp;
 private String myId;
 private String otherId;
 private boolean sentByMe;
 private String content;
 
 public MessageTemplate()
 {
  //Required do not delete
 }
 
 public MessageTemplate(long timestamp, String myId, String otherId, boolean sentByMe, String content)
 {
  this.timestamp = timestamp;
  this.myId = myId;
  this.otherId = otherId;
  this.sentByMe = sentByMe;
  this.content = content;
  
 }
 
 
 @Override
 public String toString()
 {
  return "MessageTemplate{" + "timestamp=" + timestamp + ", myId='" + myId + '\'' + ", otherId='" + otherId + '\'' + ", sentByMe=" + sentByMe + ", content='" + content + '\'' + '}';
 }
 
 public long getTimestamp()
 {
  return timestamp;
 }
 
 public void setTimestamp(long timestamp)
 {
  this.timestamp = timestamp;
 }
 
 public String getMyId()
 {
  return myId;
 }
 
 public void setMyId(String myId)
 {
  this.myId = myId;
 }
 
 public String getOtherId()
 {
  return otherId;
 }
 
 public void setOtherId(String otherId)
 {
  this.otherId = otherId;
 }
 
 public boolean isSentByMe()
 {
  return sentByMe;
 }
 
 public void setSentByMe(boolean sentByMe)
 {
  this.sentByMe = sentByMe;
 }
 
 public String getContent()
 {
  return content;
 }
 
 public void setContent(String content)
 {
  this.content = content;
 }
}
