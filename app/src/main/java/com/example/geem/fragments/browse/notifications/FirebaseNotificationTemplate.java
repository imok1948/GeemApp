package com.example.geem.fragments.browse.notifications;

public class FirebaseNotificationTemplate
{
 private long timestamp;
 private String type; // request, response, ...
 private String senderId;
 private String receiverId;
 private String itemId;
 private boolean requestAccepted;
 private boolean itemTaken;
 private String notificationId;
 
 public FirebaseNotificationTemplate()
 {
  //Required
 }
 
 public FirebaseNotificationTemplate(long timestamp, String type, String senderId, String receiverId, String itemId, boolean requestAccepted, boolean itemTaken, String notificationId)
 {
  this.timestamp = timestamp;
  this.type = type;
  this.senderId = senderId;
  this.receiverId = receiverId;
  this.itemId = itemId;
  this.requestAccepted = requestAccepted;
  this.itemTaken = itemTaken;
  this.notificationId = notificationId;
 }
 
 
 
 public FirebaseNotificationTemplate(long timestamp, String type, String senderId, String receiverId, String itemId)
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
  return "FirebaseNotificationTemplate{" + "timestamp=" + timestamp + ", type='" + type + "'\n" + ", senderId='" + senderId + "'\n" + ", receiverId='" + receiverId + "'\n" + ", itemId='" + itemId + "'\n" + ", requestAccepted=" + requestAccepted + ", itemTaken=" + itemTaken + ", notificationId='" + notificationId + "'\n" + '}';
 }
 
 
 public String getNotificationId()
 {
  return notificationId;
 }
 
 public void setNotificationId(String notificationId)
 {
  this.notificationId = notificationId;
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