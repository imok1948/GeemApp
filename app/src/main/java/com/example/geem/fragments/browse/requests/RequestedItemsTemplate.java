package com.example.geem.fragments.browse.requests;

import android.widget.ImageView;

public class RequestedItemsTemplate
{
 private String imageUrl;
 private String name;
 private int totalRequests;
 private String itemId;
 private String requestType;
 private String notificationId;
 private String requesterId;
 
 public RequestedItemsTemplate(String imageUrl, String name, int totalRequests, String itemId, String requestType, String notificationId, String requesterId)
 {
  this.imageUrl = imageUrl;
  this.name = name;
  this.totalRequests = totalRequests;
  this.itemId = itemId;
  this.requestType = requestType;
  this.notificationId = notificationId;
  this.requesterId = requesterId;
 }
 
 @Override
 public String toString()
 {
  return "RequestedItemsTemplate{" + "imageUrl='" + imageUrl + "'\n" + ", name='" + name + "'\n" + ", totalRequests=" + totalRequests + ", itemId='" + itemId + "'\n" + ", requestType='" + requestType + "'\n" + ", notificationId='" + notificationId + "'\n" + ", requesterId='" + requesterId + "'\n" + '}';
 }
 
 public String getImageUrl()
 {
  return imageUrl;
 }
 
 public void setImageUrl(String imageUrl)
 {
  this.imageUrl = imageUrl;
 }
 
 public String getName()
 {
  return name;
 }
 
 public void setName(String name)
 {
  this.name = name;
 }
 
 public int getTotalRequests()
 {
  return totalRequests;
 }
 
 public void setTotalRequests(int totalRequests)
 {
  this.totalRequests = totalRequests;
 }
 
 public String getItemId()
 {
  return itemId;
 }
 
 public void setItemId(String itemId)
 {
  this.itemId = itemId;
 }
 
 public String getRequestType()
 {
  return requestType;
 }
 
 public void setRequestType(String requestType)
 {
  this.requestType = requestType;
 }
 
 public String getNotificationId()
 {
  return notificationId;
 }
 
 public void setNotificationId(String notificationId)
 {
  this.notificationId = notificationId;
 }
 
 public String getRequesterId()
 {
  return requesterId;
 }
 
 public void setRequesterId(String requesterId)
 {
  this.requesterId = requesterId;
 }
}