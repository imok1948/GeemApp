package com.example.geem.fragments.browse.feeds;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.geem.R;
import com.example.geem.extra.ShivankUserItems;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentBrowseFeeds extends Fragment
{
 private static final String TAG = "FragmentBrowseFeeds2";
 SwitchCompat timeDescending;
 String filterCategory = "";
 String distanceCategory = "";
 private View view;
 boolean time_descending = true;
 private RecyclerView recyclerView;
 private AdapterBrowseFeeds adapterBrowseFeeds;
 double radius = 50000;
 Query options;
 boolean filterPending = false;
 //Firebase
 private static final String FEEDS_COLLECTION_NAME = "fetch_items_final";
 
 
 //GPS Things
 
 private LocationManager locationManager;
 private Location currentLocation = new Location("");
 public static final int GPS_REQUEST_CODE = 101;
 public static final int NET_REQUEST_CODE = 102;
 DrawerLayout feedsDrawer;
 ImageView searchButton;
 EditText searchQuery;
 boolean loggedIn = true;
 Spinner categorySpinner, distanceSpinner;
 //
 ProgressDialog dialog;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_browse_feeds, container, false);
  feedsDrawer = view.findViewById(R.id.feeds_drawer);
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
  categorySpinner = view.findViewById(R.id.category_filter);
  distanceSpinner = view.findViewById(R.id.distance_radius);
  ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.category_items, android.R.layout.simple_spinner_item);
  categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  categorySpinner.setAdapter(categoryAdapter);
  
  ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.nearby_items, android.R.layout.simple_spinner_item);
  distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  distanceSpinner.setAdapter(distanceAdapter);
  
  timeDescending = view.findViewById(R.id.time_descending);
  dialog = new ProgressDialog(getContext());
  
  dialog.setMessage("Loading...");
  dialog.show();
  searchQuery = view.findViewById(R.id.search_text);
  searchButton = (ImageView) view.findViewById(R.id.filter_feeds);
  searchQuery.addTextChangedListener(new TextWatcher()
  {
   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after)
   {
   
   }
   
   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count)
   {
    searchFeedsFirebase(searchQuery.getText().toString());
   }
   
   @Override
   public void afterTextChanged(Editable s)
   {
   
   }
  });
  searchButton.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View v)
   {
    feedsDrawer.openDrawer(GravityCompat.END);
   }
  });
  
  view.findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View v)
   {
    time_descending = timeDescending.isChecked();
    feedsDrawer.closeDrawer(GravityCompat.END);
    filterCategory = categorySpinner.getSelectedItem().toString();
    distanceCategory = distanceSpinner.getSelectedItem().toString();
    Toast.makeText(getActivity(), "Filter Is Applied", Toast.LENGTH_SHORT).show();
    radius = Double.valueOf(distanceCategory.split(" ")[0]);
    filterPending = true;
    searchFeedsFirebase(searchQuery.getText().toString());
   }
  });
  
  view.findViewById(R.id.btn_close_filter).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View v)
   {
    feedsDrawer.closeDrawer(GravityCompat.END);
   }
  });
  return view;
  
 }
 
 public void searchFeedsFirebase(String query)
 {
  Log.i("Q", query);
  if(query.equals(""))
  {
   filterPending = true;
   if(!time_descending)
   {
    options = FirebaseFirestore.getInstance().collection("fetch_items_final").orderBy("timestamp", Query.Direction.ASCENDING).limit(50);
   }
   else
   {
    options = FirebaseFirestore.getInstance().collection("fetch_items_final").orderBy("timestamp", Query.Direction.DESCENDING).limit(50);
   }
  }
  else
  {
   filterPending = true;
   if(!time_descending)
   {
    options = FirebaseFirestore.getInstance().collection("fetch_items_final").whereEqualTo("title", query).orderBy("timestamp", Query.Direction.ASCENDING).limit(50);
   }
   options = FirebaseFirestore.getInstance().collection("fetch_items_final").whereEqualTo("title", query).orderBy("timestamp", Query.Direction.DESCENDING).limit(50);
  }
  
  options.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    dialog.cancel();
    if(task.isSuccessful() && filterPending)
    {
     String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
     
     adapterBrowseFeeds.clear();
     for(DocumentSnapshot snapshot : task.getResult())
     {
      ShivankUserItems template = snapshot.toObject(ShivankUserItems.class);
      
      if(!template.getUserid().equals(myId) && template.isIsavailable())
      {
       Log.d(TAG, "onComplete: Snapshots==> " + template);
       double itemLatitude = template.getLatitude();
       double itemLongitude = template.getLongitude();
       
       Location itemLocation = new Location("");
       itemLocation.setLatitude(itemLatitude);
       itemLocation.setLongitude(itemLongitude);
       float distance = currentLocation.distanceTo(itemLocation) / 1000;
       if(filterCategory.equals("") || filterCategory.equals("All"))
       {
        if(distance <= radius)
        {
         FeedsTemplate feed = new FeedsTemplate();
         feed.setDistance(distance);
         feed.setTitle(template.getTitle());
         feed.setImage(template.getImage());
         feed.setUserid(template.getUserid());
         feed.setItemId(snapshot.getId());
         feed.setCategory(template.getCategory());
         adapterBrowseFeeds.addItem(feed);
         filterPending = false;
        }
        
       }
       else if(filterCategory.equals(template.getCategory()) && distance <= radius)
       {
        FeedsTemplate feed = new FeedsTemplate();
        feed.setDistance(distance);
        feed.setTitle(template.getTitle());
        feed.setImage(template.getImage());
        feed.setUserid(template.getUserid());
        feed.setItemId(snapshot.getId());
        feed.setCategory(template.getCategory());
        adapterBrowseFeeds.addItem(feed);
        filterPending = false;
       }
      }
     }
    }
    else
    {
     Log.d(TAG, "onComplete: Error receiving from feeds :(");
    }
   }
  });
  //updateUI(options);
 }
 
 private void initializeFirebase()
 {
  FirebaseFirestore.getInstance().collection(FEEDS_COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    dialog.cancel();
    if(task.isSuccessful() && filterPending)
    {
     String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
     adapterBrowseFeeds.clear();
     for(DocumentSnapshot snapshot : task.getResult())
     {
      
      ShivankUserItems template = snapshot.toObject(ShivankUserItems.class);
      
      if(!template.getUserid().equals(myId) && template.isIsavailable())
      {
       Log.d(TAG, "onComplete: Snapshot : " + snapshot.toObject(ShivankUserItems.class));
       
       double itemLatitude = template.getLatitude();
       double itemLongitude = template.getLongitude();
       
       Location itemLocation = new Location("");
       itemLocation.setLatitude(itemLatitude);
       itemLocation.setLongitude(itemLongitude);
       float distance = currentLocation.distanceTo(itemLocation) / 1000;
       if(filterCategory.equals("") || filterCategory.equals("All"))
       {
        if(distance <= radius)
        {
         FeedsTemplate feed = new FeedsTemplate();
         feed.setDistance(distance);
         feed.setTitle(template.getTitle());
         feed.setImage(template.getImage());
         feed.setUserid(template.getUserid());
         feed.setItemId(snapshot.getId());
         feed.setCategory(template.getCategory());
         adapterBrowseFeeds.addItem(feed);
         filterPending = false;
        }
        
       }
       else if(filterCategory.equals(template.getCategory()) && distance <= radius)
       {
        FeedsTemplate feed = new FeedsTemplate();
        feed.setDistance(distance);
        feed.setTitle(template.getTitle());
        feed.setImage(template.getImage());
        feed.setUserid(template.getUserid());
        feed.setItemId(snapshot.getId());
        feed.setCategory(template.getCategory());
        adapterBrowseFeeds.addItem(feed);
        filterPending = false;
       }
      }
      
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
  recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
  recyclerView.setAdapter(adapterBrowseFeeds);
 }
 
 private void init()
 {
  recyclerView = view.findViewById(R.id.feeds_recycler_view);
  adapterBrowseFeeds = new AdapterBrowseFeeds(getContext());
  filterPending = true;
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
   locationManager.removeUpdates(mLocationListener);
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
 
 @Override
 public void onResume()
 {
  super.onResume();
 }
}