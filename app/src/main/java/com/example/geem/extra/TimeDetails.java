package com.example.geem.extra;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class TimeDetails
{
 private long timestamp;
 
 public TimeDetails(long timestamp)
 {
  this.timestamp = timestamp;
 }
 
 public void setTimestamp(long timestamp)
 {
  this.timestamp = timestamp;
 }
 
 public String getTime()
 {
  Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
  calendar.setTimeInMillis(timestamp);
  return "" + DateFormat.format("hh:mm AAA", calendar);
 }
 
 public String getDate()
 {
  Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
  calendar.setTimeInMillis(timestamp);
  return "" + DateFormat.format("MMM-dd", calendar);
 }
 
 @Override
 public String toString()
 {
  return "TimeDetails{" + "timestamp=" + timestamp + '}';
 }
}
