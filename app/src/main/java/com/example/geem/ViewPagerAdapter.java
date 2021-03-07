package com.example.geem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.geem.extra.Variables;
import com.example.geem.fragments.FragmentAddItem;
import com.example.geem.fragments.FragmentFeeds;
import com.example.geem.fragments.FragmentHistory;
import com.example.geem.fragments.FragmentMessages;
import com.example.geem.fragments.FragmentNotifications;
import com.example.geem.fragments.FragmentProfile;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
 public ViewPagerAdapter(@NonNull FragmentManager fm)
 {
  super(fm);
 }
 
 @NonNull
 @Override
 public Fragment getItem(int position)
 {
  Bundle bundle = new Bundle();
  bundle.putString(Variables.GREETING_KEY, "Hi, if you own this fragment, please complete it.");
  
  switch(position)
  {
   case 0:
   {
    FragmentProfile fragment = new FragmentProfile();
    fragment.setArguments(bundle);
    return fragment;
   }
   
   
   case 1:
   {
    FragmentNotifications fragment = new FragmentNotifications();
    fragment.setArguments(bundle);
    return fragment;
   }
   
   
   case 2:
   {
    FragmentMessages fragment = new FragmentMessages();
    fragment.setArguments(bundle);
    return fragment;
   }
   
   
   case 3:
   {
    FragmentFeeds fragment = new FragmentFeeds();
    fragment.setArguments(bundle);
    return fragment;
   }
   
   
   case 4:
   {
    FragmentAddItem fragment = new FragmentAddItem();
    fragment.setArguments(bundle);
    return fragment;
   }
   
   
   case 5:
   {
    FragmentHistory fragment = new FragmentHistory();
    fragment.setArguments(bundle);
    return fragment;
   }
   
   default:  //Yes, this will never be called but for the shake of completeness
   {
    FragmentFeeds fragment = new FragmentFeeds();
    fragment.setArguments(bundle);
    return fragment;
   }
  }
 }
 
 @Override
 public int getCount()
 {
  return 6;
 }
 
 @Nullable
 @Override
 public CharSequence getPageTitle(int position)
 {
  //Do not set any title, instead main activity will set the icons.
  return null;
 }
}
