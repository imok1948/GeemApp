package com.example.geem.fragments.browse.requests;

import android.widget.LinearLayout;

import com.mikhaellopez.circularimageview.CircularImageView;

public class RequesterTemplate
{
 private String profilePictureUrl;
 private String name;
 private String address;
 
 public RequesterTemplate(String profilePictureUrl, String name, String address)
 {
  this.profilePictureUrl = profilePictureUrl;
  this.name = name;
  this.address = address;
 }
 
 @Override
 public String toString()
 {
  return "RequesterTemplate{" + "profilePictureUrl='" + profilePictureUrl + '\'' + ", name='" + name + '\'' + ", address='" + address + '\'' + '}';
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