package com.example.geem.fragments.browse.feeds;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.extra.ShivankUserItems;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentBrowseFeeds2 extends Fragment
{
 private static final String TAG = "FragmentBrowseFeeds2";
 
 
 private View view;
 private RecyclerView recyclerView;
 private AdapterBrowseFeeds adapterBrowseFeeds;
 
 
 //Firebase
 private static final String FEEDS_COLLECTION_NAME = "fetch_items_final";
 
 
 //GPS Things
 
 private LocationManager locationManager;
 private Location currentLocation;
 public static final int GPS_REQUEST_CODE = 101;
 public static final int NET_REQUEST_CODE = 102;
 
 
 //
 ProgressDialog dialog;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_browse_feeds, container, false);
  
  
  dialog = new ProgressDialog(getContext());
  dialog.setMessage("Loading...");
  dialog.show();
  
  boolean loggedIn = true;
  if(loggedIn)
  {
   init();
   initializeComponents();
   getUserLocation();
  }
  else
  {
   //Not logged in
   dialog.cancel();
  }
  return view;
 }
 
 private void initializeFirebase()
 {
  FirebaseFirestore.getInstance().collection(FEEDS_COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    dialog.cancel();
    if(task.isSuccessful())
    {
     
     for(DocumentSnapshot snapshot : task.getResult())
     {
      
      ShivankUserItems template = snapshot.toObject(ShivankUserItems.class);
      
      Log.d(TAG, "onComplete: Snapshot : " + snapshot.toObject(ShivankUserItems.class));
      
      double itemLatitude = template.getLatitude();
      double itemLongitude = template.getLongitude();
      
      Location itemLocation = new Location("");
      itemLocation.setLatitude(itemLatitude);
      itemLocation.setLongitude(itemLongitude);
      float distance = currentLocation.distanceTo(itemLocation) / 1000;
      
      FeedsTemplate feed = new FeedsTemplate();
      feed.setDistance(distance);
      feed.setTitle(template.getTitle());
      feed.setImage(template.getImage());
      
      feed.setUserid(template.getUserid());
      feed.setItemId(snapshot.getId());
      
      adapterBrowseFeeds.addItem(feed);
     }
    }
    else
    {
     Log.d(TAG, "onComplete: Error receiving from feeds :(");
    }
   }
  });
 }
 
 private void initializeComponents()
 {
  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  recyclerView.setAdapter(adapterBrowseFeeds);
 }
 
 private void init()
 {
  recyclerView = view.findViewById(R.id.feeds_recycler_view);
  adapterBrowseFeeds = new AdapterBrowseFeeds(getContext());
 }
 
 
 private void getUserLocation()
 {
  locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
  
  if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {
   ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, NET_REQUEST_CODE);
  }
  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
  
  if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {
   ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
  }
  
  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
 }
 
 private final LocationListener mLocationListener = new LocationListener()
 {
  @Override
  public void onLocationChanged(@NonNull Location location)
  {
   currentLocation = location;
   Log.d(TAG, "onLocationChanged: Lat ==> " + location.getLatitude() + ", Long ==> " + location.getLongitude());
   initializeFirebase();
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