package com.example.geem.fragments.browse.add;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.fragments.browse.feeds.FragmentBrowseFeeds;

public class FragmentAddItem2 extends Fragment
{
 
 private View view;
 private Location currentLocation = new Location("");
 private LocationManager locationManager;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_add_item2, container, false);
  
  
  return view;
 }
 
 private void getUserLocation()
 {
  locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
  if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {
   ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, FragmentBrowseFeeds.NET_REQUEST_CODE);
  }
  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
  
  if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {
   ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FragmentBrowseFeeds.GPS_REQUEST_CODE);
  }
  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 50, locationListener);
 }
 
 private final LocationListener locationListener = new LocationListener()
 {
  @Override
  public void onLocationChanged(@NonNull Location location)
  {
   Toast.makeText(getContext(), "Location received ==> " + location, Toast.LENGTH_SHORT).show();
   locationManager.removeUpdates(locationListener);
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