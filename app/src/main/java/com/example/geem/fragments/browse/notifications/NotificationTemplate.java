package com.example.geem.fragments.browse.notifications;

public class NotificationTemplate
{
 private long timestamp;
 private String type; // request, response, ...
 private String senderId;
 private String receiverId;
 private String itemId;
 private boolean requestAccepted;
 private boolean itemTaken;
 
 public NotificationTemplate()
 {
  //Required
 }
 
 public NotificationTemplate(long timestamp, String type, String senderId, String receiverId, String itemId, boolean requestAccepted, boolean itemTaken)
 {
  this.timestamp = timestamp;
  this.type = type;
  this.senderId = senderId;
  this.receiverId = receiverId;
  this.itemId = itemId;
  this.requestAccepted = requestAccepted;
  this.itemTaken = itemTaken;
 }
 
 public NotificationTemplate(long timestamp, String type, String senderId, String receiverId, String itemId)
 {
  this.timestamp = timestamp;
  this.type = type;
  this.senderId = senderId;
  this.receiverId = receiverId;
  this.itemId = itemId;
 }
 
 
 @Override
 public String toString()
 {
  return "NotificationTemplate{" + "timestamp=" + timestamp + ", type='" + type + '\'' + ", senderId='" + senderId + '\'' + ", receiverId='" + receiverId + '\'' + ", itemId='" + itemId + '\'' + ", requestAccepted=" + requestAccepted + ", itemTaken=" + itemTaken + '}';
 }
 
 public long getTimestamp()
 {
  return timestamp;
 }
 
 public void setTimestamp(long timestamp)
 {
  this.timestamp = timestamp;
 }
 
 public String getType()
 {
  return type;
 }
 
 public void setType(String type)
 {
  this.type = type;
 }
 
 public String getSenderId()
 {
  return senderId;
 }
 
 public void setSenderId(String senderId)
 {
  this.senderId = senderId;
 }
 
 public String getReceiverId()
 {
  return receiverId;
 }
 
 public void setReceiverId(String receiverId)
 {
  this.receiverId = receiverId;
 }
 
 public String getItemId()
 {
  return itemId;
 }
 
 public void setItemId(String itemId)
 {
  this.itemId = itemId;
 }
 
 public boolean isRequestAccepted()
 {
  return requestAccepted;
 }
 
 public void setRequestAccepted(boolean requestAccepted)
 {
  this.requestAccepted = requestAccepted;
 }
 
 public boolean isItemTaken()
 {
  return itemTaken;
 }
 
 public void setItemTaken(boolean itemTaken)
 {
  this.itemTaken = itemTaken;
 }
}