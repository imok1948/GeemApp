package com.example.geem.fragments.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.example.geem.activities.MainActivity;
import com.google.android.material.tabs.TabLayout;


public class FragmentBrowseTabs extends Fragment
{
 ViewPagerAdapter adapterTemp;
 private TabLayout tabLayout;
 private ViewPager viewPager;
 
 public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View root = inflater.inflate(R.layout.fragment_browse_tabs, container, false);
  
  tabLayout = root.findViewById(R.id.tabLayout1);
  viewPager = root.findViewById(R.id.viewpages1);
  
  ((MainActivity) getActivity()).tabLayoutForBrowseFragments = tabLayout;
  adapterTemp = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
  viewPager.setAdapter(adapterTemp);
  tabLayout.setupWithViewPager(viewPager);
  
  tabLayout.selectTab(tabLayout.getTabAt(1));
  
  for(int i = 0; i < tabLayout.getTabCount(); i++)
  {
   tabLayout.getTabAt(i).setIcon(Variables.TAB_ICONS[i]);
  }
  return root;
 }
 
 private void toast(String s)
 {
  Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
 }
}