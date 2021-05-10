package com.example.geem.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.notifications.DummyTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity
{
 public HashMap<Integer, Fragment> positionFragmentMap = new HashMap<>();
 public TabLayout tabLayoutForBrowseFragments;
 private AppBarConfiguration appBarConfiguration;
 public NavController navController;
 public static final int GPS_REQUEST_CODE = 101;
 public static final int NET_REQUEST_CODE = 102;
 
 
 //For passing data to fragments
 public HashMap<String, String> dataHashMap = new HashMap<>();
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {
   
   ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
  }
  if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {
   
   ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, NET_REQUEST_CODE);
  }
  
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
  
  if(FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().getUid() != null)
  {
  
  }
  else
  {
   navController.navigate(R.id.nav_profile);
  }
  
  try
  {
   setInfoToNavBar();
  }
  catch(Exception e)
  {
   navController.navigate(R.id.nav_profile);
  }
 }
 
 
 private void setInfoToNavBar() throws Exception
 {
  
  View view = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
  
  TextView name = view.findViewById(R.id.nav_header_full_name);
  CircularImageView imageView = view.findViewById(R.id.nav_header_profile_pic);
  
  String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
  
  imageView.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Intent intent = new Intent(getApplicationContext(), ActivityShowUserProfile.class);
    intent.putExtra(Variables.OTHER_ID, myId);
    startActivity(intent);
   }
  });
  
  
  FirebaseFirestore.getInstance().collection(Variables.PROFILE_COLLECTION_NAME).document(myId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    if(task.isSuccessful())
    {
     DummyTemplate profileTemplate = task.getResult().toObject(DummyTemplate.class);
     name.setText(profileTemplate.getName());
     Glide.with(getApplicationContext()).load(profileTemplate.getProfilePictureUrl()).placeholder(R.drawable.rahul_profile).error(R.drawable.elon).into(imageView);
    }
   }
  });
 }
 
 public boolean checkLoggedIn()
 {
  return FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null;
 }
 
 
 public void goToHomeFragment()
 {
  navController.navigate(R.id.nav_browse_items);
 }
 
 public void goToLoginFragment()
 {
  Toast.makeText(getApplicationContext(), "Please log in first", Toast.LENGTH_SHORT).show();
  navController.navigate(R.id.nav_profile);
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