package com.example.geem.activities;

import android.os.Bundle;
import android.view.Menu;

import com.example.geem.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity
{
 
 public TabLayout tabLayoutForBrowseFragments;
 private AppBarConfiguration appBarConfiguration;
 public NavController navController;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
  Toolbar toolbar = findViewById(R.id.toolbar);
  setSupportActionBar(toolbar);
  
  DrawerLayout drawer = findViewById(R.id.drawer_layout);
  NavigationView navigationView = findViewById(R.id.nav_view);
  
  // Passing each menu ID as a set of Ids because each
  // menu should be considered as top level destinations.
  
  appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_profile, R.id.nav_address, R.id.nav_browse_items, R.id.nav_settings, R.id.nav_help, R.id.nav_about).setDrawerLayout(drawer).build();
  navController = Navigation.findNavController(this, R.id.nav_host_fragment);
  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
  NavigationUI.setupWithNavController(navigationView, navController);
  
  
  if(FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null)
  {
   navController.navigate(R.id.nav_browse_items);
  }
  else
  {
   navController.navigate(R.id.nav_profile);
  }
 }
 
 @Override
 public boolean onCreateOptionsMenu(Menu menu)
 {
  // Inflate the menu; this adds items to the action bar if it is present.
  getMenuInflater().inflate(R.menu.navigation_drawer_temp, menu);
  return true;
 }
 
 @Override
 public boolean onSupportNavigateUp()
 {
  NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
  return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
 }
 
 
}