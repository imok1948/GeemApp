package com.example.geem.fragments.browse.requests;

import android.widget.ImageView;

public class RequestedItemsTemplate
{
 private String imageUrl;
 private String name;
 private int totalRequests;
 
 public RequestedItemsTemplate(String imageUrl, String name, int totalRequests)
 {
  this.imageUrl = imageUrl;
  this.name = name;
  this.totalRequests = totalRequests;
 }
 
 
 @Override
 public String toString()
 {
  return "RequestedItemsTemplate{" + "imageUrl='" + imageUrl + '\'' + ", name='" + name + '\'' + ", totalRequests=" + totalRequests + '}';
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