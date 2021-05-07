package com.example.geem.fragments.browse.notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyTemplate
{
 long joiningTime;
 String profilePictureUrl;
 String name;
 String email;
 String city;
 
 List<Float> takenRatings;
 List<Integer> totalTakenRatings;
 List<Float> givenRatings;
 List<Integer> totalGivenRatings;
 
 public DummyTemplate()
 {
  //Required do not delete
 }
 
 
 public DummyTemplate(String profilePictureUrl, String name, String email, String city)
 {
  this.profilePictureUrl = profilePictureUrl;
  this.name = name;
  this.email = email;
  this.city = city;
  
  
  this.takenRatings = new ArrayList<>();
  this.totalTakenRatings = new ArrayList<>();
  this.givenRatings = new ArrayList<>();
  this.totalGivenRatings = new ArrayList<>();
  
  float min = 0;
  float max = 5;
  
  Random random = new Random();
  
  this.joiningTime = 162_033_848_603_5L + random.nextInt(3_848_603_5);
  for(int i = 0; i < 5; i++)
  {
   takenRatings.add(random.nextFloat() * (max - min) + min);
   totalTakenRatings.add(random.nextInt(100));
   givenRatings.add(random.nextFloat() * (max - min) + min);
   totalGivenRatings.add(random.nextInt(100));
  }
 }
 
 @Override
 public String toString()
 {
  return "DummyTemplate{" + "joiningTime=" + joiningTime + ", profilePictureUrl='" + profilePictureUrl + '\'' + ", name='" + name + '\'' + ", email='" + email + '\'' + ", city='" + city + '\'' + ", takenRatings=" + takenRatings + ", totalTakenRatings=" + totalTakenRatings + ", givenRatings=" + givenRatings + ", totalGivenRatings=" + totalGivenRatings + '}';
 }
 
 public long getJoiningTime()
 {
  return joiningTime;
 }
 
 public void setJoiningTime(long joiningTime)
 {
  this.joiningTime = joiningTime;
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
 
 public String getEmail()
 {
  return email;
 }
 
 public void setEmail(String email)
 {
  this.email = email;
 }
 
 public String getCity()
 {
  return city;
 }
 
 public void setCity(String city)
 {
  this.city = city;
 }
 
 public List<Float> getTakenRatings()
 {
  return takenRatings;
 }
 
 public void setTakenRatings(List<Float> takenRatings)
 {
  this.takenRatings = takenRatings;
 }
 
 public List<Integer> getTotalTakenRatings()
 {
  return totalTakenRatings;
 }
 
 public void setTotalTakenRatings(List<Integer> totalTakenRatings)
 {
  this.totalTakenRatings = totalTakenRatings;
 }
 
 public List<Float> getGivenRatings()
 {
  return givenRatings;
 }
 
 public void setGivenRatings(List<Float> givenRatings)
 {
  this.givenRatings = givenRatings;
 }
 
 public List<Integer> getTotalGivenRatings()
 {
  return totalGivenRatings;
 }
 
 public void setTotalGivenRatings(List<Integer> totalGivenRatings)
 {
  this.totalGivenRatings = totalGivenRatings;
 }
}
