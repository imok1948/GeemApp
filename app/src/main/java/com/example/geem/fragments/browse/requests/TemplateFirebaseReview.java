package com.example.geem.fragments.browse.requests;

public class TemplateFirebaseReview
{
 private String fromId;
 private String toId;
 private String itemId;
 private int rating;
 private String review;
 
 public TemplateFirebaseReview()
 {
  //Required
 }
 
 public TemplateFirebaseReview(String fromId, String toId, String itemId, int rating, String review)
 {
  this.fromId = fromId;
  this.toId = toId;
  this.itemId = itemId;
  this.rating = rating;
  this.review = review;
 }
 
 @Override
 public String toString()
 {
  return "TemplateFirebaseReview{" + "fromId='" + fromId + "'\n" + ", toId='" + toId + "'\n" + ", itemId='" + itemId + "'\n" + ", rating=" + rating + ", review='" + review + "'\n" + '}';
 }
 
 public String getToId()
 {
  return toId;
 }
 
 public void setToId(String toId)
 {
  this.toId = toId;
 }
 
 public String getItemId()
 {
  return itemId;
 }
 
 public void setItemId(String itemId)
 {
  this.itemId = itemId;
 }
 
 public int getRating()
 {
  return rating;
 }
 
 public void setRating(int rating)
 {
  this.rating = rating;
 }
 
 public String getReview()
 {
  return review;
 }
 
 public void setReview(String review)
 {
  this.review = review;
 }
 
 public String getFromId()
 {
  return fromId;
 }
 
 public void setFromId(String fromId)
 {
  this.fromId = fromId;
 }
}