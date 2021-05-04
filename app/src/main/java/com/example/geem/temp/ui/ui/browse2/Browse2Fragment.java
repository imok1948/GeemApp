package com.example.geem.temp.ui.ui.browse2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.geem.R;
import com.google.android.material.tabs.TabLayout;


public class Browse2Fragment extends Fragment
{
 ViewPagerAdapterTemp adapterTemp;
 private TabLayout tabLayout;
 private ViewPager viewPager;
 
 
 public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View root = inflater.inflate(R.layout.fragment_browse2, container, false);
  
  tabLayout = root.findViewById(R.id.tabLayout1);
  viewPager = root.findViewById(R.id.viewpages1);
  
  adapterTemp = new ViewPagerAdapterTemp(getActivity().getSupportFragmentManager());
  viewPager.setAdapter(adapterTemp);
  tabLayout.setupWithViewPager(viewPager);
  
  tabLayout.selectTab(tabLayout.getTabAt(2));
  return root;
 }
 
 private void toast(String s)
 {
  Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
 }
}