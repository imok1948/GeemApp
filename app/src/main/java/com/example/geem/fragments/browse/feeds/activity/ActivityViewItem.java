package com.example.geem.fragments.browse.feeds.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.geem.R;
import com.example.geem.extra.ShivankUserItems;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import static com.example.geem.fragments.browse.feeds.FragmentBrowseFeeds.GPS_REQUEST_CODE;
import static com.example.geem.fragments.browse.feeds.FragmentBrowseFeeds.NET_REQUEST_CODE;

public class ActivityViewItem extends AppCompatActivity
{
 ShivankUserItems item;
 AppBarLayout appBar;
 TextView title,description,user,proximity,menu_title,category;
 public static final int GPS_REQUEST_CODE = 101;
 public static final int NET_REQUEST_CODE = 102;

 LocationManager locationManager;
 Location currentLocation = new Location("");
 View detailsTile;
 Location itemLocation = new Location("");
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_view_item);
  item = (ShivankUserItems) getIntent().getSerializableExtra("item_details");
  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
  setSupportActionBar(toolbar);
  CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
  toolBarLayout.setTitle(getTitle());
  getUserLocation();
  appBar = findViewById(R.id.app_bar);
  detailsTile = findViewById(R.id.details_tile);
  title = detailsTile.findViewById(R.id.item_name);
  category = detailsTile.findViewById(R.id.item_category);
  proximity = detailsTile.findViewById(R.id.item_proximity);
  description = detailsTile.findViewById(R.id.item_description);
  user = detailsTile.findViewById(R.id.item_owner);

  title.setText(item.getTitle());
  category.setText(item.getCategory());
  description.setText(item.getDescription());
  user.setText(item.getUserId());
  Glide.with(this).asBitmap().load(item.getImage()).into(new CustomTarget<Bitmap>()
  {
   @Override
   public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
   {
    appBar.setBackground(new BitmapDrawable(resource));
   }
   
   @Override
   public void onLoadCleared(@Nullable Drawable placeholder)
   {
   }
  });
  
  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
  fab.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
   }
  });
 }

 @Override
 protected void onResume() {
  super.onResume();
  double itemLatitude = item.getLatitude();
  double itemLongitude = item.getLongitude();
  itemLocation.setLatitude(itemLatitude);
  itemLocation.setLongitude(itemLongitude);
  float distanceInKM = currentLocation.distanceTo(itemLocation) / 1000;
  proximity.setText(new DecimalFormat("##.#").format(distanceInKM) + " kms away");

 }

 private void getUserLocation()
 {
  locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


  if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {

   ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, NET_REQUEST_CODE);
  }
  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);

  if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {

   ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
  }
  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

 }

 private final LocationListener mLocationListener = new LocationListener()
 {

  @Override
  public void onLocationChanged(@NonNull Location location)
  {
   currentLocation = location;
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras)
  {
  }

  @Override
  public void onProviderEnabled(@NonNull String provider)
  {

  }

  @Override
  public void onProviderDisabled(@NonNull String provider)
  {

  }
 };
}