package com.example.geem.temp.ui.ui.testing.tablayout;

import android.os.Bundle;

import com.example.geem.temp.ui.ui.browse2.ViewPagerAdapterTemp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.example.geem.R;
import com.google.android.material.tabs.TabLayout;

public class TempActivityForTestingTablayout extends FragmentActivity
{
 
 private TabLayout tabLayout;
 private ViewPager viewPager;
 
 ViewPagerAdapterTemp adapterTemp;
 
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_temp_for_testing_tablayout);
  Toolbar toolbar = findViewById(R.id.toolbar);
  
  
  FloatingActionButton fab = findViewById(R.id.fab);
  fab.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
   }
  });
  getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new FragmentForTablayout()).commit();
  
  
 }
}