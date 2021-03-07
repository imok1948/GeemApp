package com.example.geem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.Image;
import android.os.Bundle;
import android.widget.Toast;

import com.example.geem.extra.Variables;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
{
 
 private TabLayout tabLayout;
 private ViewPager viewPager;
 private ViewPagerAdapter viewPagerAdapter;
 
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
  tabLayout = findViewById(R.id.tabLayout);
  viewPager = findViewById(R.id.viewpages);
  viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
  
  viewPager.setAdapter(viewPagerAdapter);
  viewPager.setCurrentItem(3);  //Change default tab to feeds
  
  tabLayout.setupWithViewPager(viewPager);
  
  for(int i = 0; i < tabLayout.getTabCount(); i++)
  {
   tabLayout.getTabAt(i).setIcon(Variables.TAB_ICONS[i]);
  }
 }
}