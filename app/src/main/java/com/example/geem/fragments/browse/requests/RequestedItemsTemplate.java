package com.example.geem.fragments.browse.requests;

import android.widget.ImageView;

public class RequestedItemsTemplate
{
 private String imageUrl;
 private String name;
 private int totalRequests;
 private String itemId;
 
 
 public RequestedItemsTemplate(String imageUrl, String name, int totalRequests, String itemId)
 {
  this.imageUrl = imageUrl;
  this.name = name;
  this.totalRequests = totalRequests;
  this.itemId = itemId;
 }
 
 
 @Override
 public String toString()
 {
  return "RequestedItemsTemplate{" + "imageUrl='" + imageUrl + "'\n" + ", name='" + name + "'\n" + ", totalRequests=" + totalRequests + ", itemId='" + itemId + "'\n" + '}';
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
}