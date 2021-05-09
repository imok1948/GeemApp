package com.example.geem.fragments.browse.requests;

public class RequesterTemplate
{
 private String profilePictureUrl;
 private String name;
 private String address;
 private String userId;
 private String itemId;
 private String notificationId;
 private String notificationType;
 
 public RequesterTemplate(String profilePictureUrl, String name, String address, String userId, String itemId, String notificationId, String notificationType)
 {
  this.profilePictureUrl = profilePictureUrl;
  this.name = name;
  this.address = address;
  this.userId = userId;
  this.itemId = itemId;
  this.notificationId = notificationId;
  this.notificationType = notificationType;
 }
 
 
 @Override
 public String toString()
 {
  return "RequesterTemplate{" + "profilePictureUrl='" + profilePictureUrl + "'\n" + ", name='" + name + "'\n" + ", address='" + address + "'\n" + ", userId='" + userId + "'\n" + ", itemId='" + itemId + "'\n" + ", notificationId='" + notificationId + "'\n" + ", notificationType='" + notificationType + "'\n" + '}';
 }
 
 public String getNotificationType()
 {
  return notificationType;
 }
 
 public void setNotificationType(String notificationType)
 {
  this.notificationType = notificationType;
 }
 
 public String getNotificationId()
 {
  return notificationId;
 }
 
 public void setNotificationId(String notificationId)
 {
  this.notificationId = notificationId;
 }
 
 public String getUserId()
 {
  return userId;
 }
 
 public void setUserId(String userId)
 {
  this.userId = userId;
 }
 
 public String getItemId()
 {
  return itemId;
 }
 
 public void setItemId(String itemId)
 {
  this.itemId = itemId;
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
 
 public String getAddress()
 {
  return address;
 }
 
 public void setAddress(String address)
 {
  this.address = address;
 }
}