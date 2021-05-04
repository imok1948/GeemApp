package com.example.geem.temp.ui.ui.browse2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.geem.temp.ui.ui.browse2.add2.AddItemsFragment;
import com.example.geem.temp.ui.ui.browse2.feeds2.BrowseFeedsFragment;
import com.example.geem.temp.ui.ui.browse2.history2.BrowseHistoryFragment;
import com.example.geem.temp.ui.ui.browse2.messages2.BrowseMessagesFragment;
import com.example.geem.temp.ui.ui.testing.tablayout.BlankFragment;
import com.example.geem.temp.ui.ui.testing.tablayout.BlankFragment2;
import com.example.geem.temp.ui.ui.testing.tablayout.BlankFragment3;

public class ViewPagerAdapterTemp extends FragmentStatePagerAdapter
{
 private static final String TAG = "Fragment Pager Adapter";
 private String[] names = new String[]{"Mesages", "Feeds", "Add Items", "History"};
 
 public ViewPagerAdapterTemp(@NonNull FragmentManager fm)
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
    fragment = new BrowseMessagesFragment();
    break;
   case 1:
    fragment = new BrowseFeedsFragment();
    break;
   case 2:
    fragment = new AddItemsFragment();
    break;
   case 3:
    fragment = new BrowseHistoryFragment();
    break;
   default:
    fragment = new BrowseFeedsFragment();
    break;
  }
  return fragment;
 }
 
 @Override
 public int getCount()
 {
  return names.length;
 }
 
 @Nullable
 @Override
 public CharSequence getPageTitle(int position)
 {
  return names[position];
 }
 
 
}
