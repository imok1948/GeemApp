package com.example.geem.fragments.browse;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.geem.fragments.browse.add.FragmentAddItems;
import com.example.geem.fragments.browse.feeds.FragmentBrowseFeeds;
import com.example.geem.fragments.browse.history.FragmentBrowseHistory;
import com.example.geem.fragments.browse.messages.FragmentBrowseMessages;

public class ViewPagerAdapter extends FragmentStatePagerAdapter
{
 private static final String TAG = "Fragment Pager Adapter";
 private String[] names = new String[]{"Mesages", "Feeds", "Add Items", "History"};
 
 public ViewPagerAdapter(@NonNull FragmentManager fm)
 {
  super(fm);
 }
 
 @NonNull
 @Override
 public Fragment getItem(int position)
 {
  Fragment fragment = null;
  switch(position)
  {
   case 0:
    fragment = new FragmentBrowseMessages();
    break;
   case 1:
    fragment = new FragmentBrowseFeeds();
    break;
   case 2:
    fragment = new FragmentAddItems();
    break;
   case 3:
    fragment = new FragmentBrowseHistory();
    break;
   default:
    fragment = new FragmentBrowseFeeds();
    break;
  }
  return fragment;
 }
 
 @Override
 public int getCount()
 {
  return names.length;
 }
 
 /*
 @Nullable
 @Override
 public CharSequence getPageTitle(int position)
 {
  return names[position];
 }*/
 
 
}
