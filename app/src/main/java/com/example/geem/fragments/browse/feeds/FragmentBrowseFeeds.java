package com.example.geem.fragments.browse.feeds;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.extra.ShivankUserItems;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.feeds.activity.ActivityViewItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;

import static android.content.ContentValues.TAG;

public class FragmentBrowseFeeds extends Fragment
{
 //Recycler View Container
 RecyclerView feedsRecyclerView;
 ItemsAdapter itemsAdapter;
 ImageView searchButton;
 EditText searchQuery;
 FirestoreRecyclerOptions<ShivankUserItems> options;
 LocationManager locationManager;
 Location currentLocation;
 public static final int GPS_REQUEST_CODE = 101;
 public static final int NET_REQUEST_CODE = 102;
 FirebaseFirestore db;
 DrawerLayout feedsDrawer;
 
 
 //For ignoring my items
 private String myId = "";
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  
  View view = inflater.inflate(R.layout.fragment_browse_feeds, container, false);
  
  try
  {
   myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
  }
  catch(Exception e)
  {
   Log.d(TAG, "onCreateView: Error geting user name");
   e.printStackTrace();
  }
  db = FirebaseFirestore.getInstance();
  currentLocation = new Location("");
  getUserLocation();
  db.collection("user_items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     for(QueryDocumentSnapshot document : task.getResult())
     {
      Log.i(TAG, document.getId() + " => " + document.getData());
     }
    }
    else
    {
     Log.i(TAG, "Error getting documents.", task.getException());
    }
   }
  });
  feedsDrawer = view.findViewById(R.id.feeds_drawer);
  feedsRecyclerView = view.findViewById(R.id.feeds_recycler_view);
  feedsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  options = new FirestoreRecyclerOptions.Builder<ShivankUserItems>().setQuery(FirebaseFirestore.getInstance().collection("fetch_items_final").orderBy("timestamp", Query.Direction.DESCENDING).limit(50), ShivankUserItems.class).build();
  
  updateUI(options);
  //Toast.makeText(getContext(), getArguments().getString(Variables.GREETING_KEY), Toast.LENGTH_SHORT).show();
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
    feedsDrawer.closeDrawer(GravityCompat.END);
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
   options = new FirestoreRecyclerOptions.Builder<ShivankUserItems>().setQuery(FirebaseFirestore.getInstance().collection("fetch_items_final").whereNotEqualTo("userid", myId).orderBy("timestamp", Query.Direction.DESCENDING).limit(50), ShivankUserItems.class).build();
  }
  else
  {
   options = new FirestoreRecyclerOptions.Builder<ShivankUserItems>().setQuery(FirebaseFirestore.getInstance().collection("fetch_items_final").whereNotEqualTo("userid", myId).whereEqualTo("title", query).orderBy("timestamp", Query.Direction.DESCENDING).limit(50), ShivankUserItems.class).build();
  }
  updateUI(options);
  
 }
 
 public void updateUI(FirestoreRecyclerOptions<ShivankUserItems> options)
 {
  //if(itemsAdapter == null){
  itemsAdapter = new ItemsAdapter(options);
  feedsRecyclerView.setAdapter(itemsAdapter);
  itemsAdapter.startListening();
  Log.i("INFO", "New Adapter Generated");
  //}
  
  //else{
  //itemsAdapter.notifyDataSetChanged();
  //Log.i("INFO","Old Adapter Refreshed");
  //}
 }
 
 @Override
 public void onResume()
 {
  super.onResume();
  updateUI(options);
 }
 
 private class ItemsAdapter extends FirestoreRecyclerAdapter<ShivankUserItems, CardViewHolder>
 {
  
  /**
   * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
   * FirestoreRecyclerOptions} for configuration options.
   *
   * @param options
   */
  public ItemsAdapter(@NonNull FirestoreRecyclerOptions<ShivankUserItems> options)
  {
   super(options);
  }
  
  @Override
  
  public void onError(FirebaseFirestoreException e)
  {
   //Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
  }
  
  @NonNull
  @Override
  public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
  {
   LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
   return new CardViewHolder(layoutInflater, parent);
  }
  
  
  @Override
  protected void onBindViewHolder(@NonNull CardViewHolder holder, int position, @NonNull ShivankUserItems model)
  {
   holder.thisItem = model;
   holder.itemTitle.setText(model.getTitle());
   Glide.with(holder.itemImage.getContext()).load(model.getImage()).into(holder.itemImage);
   double itemLatitude = model.getLatitude();
   double itemLongitude = model.getLongitude();
   Location itemLocation = new Location("");
   itemLocation.setLatitude(itemLatitude);
   itemLocation.setLongitude(itemLongitude);
   float distanceInKM = currentLocation.distanceTo(itemLocation) / 1000;
   holder.distance.setText(new DecimalFormat("##.#").format(distanceInKM) + " kms away");
   
   
   holder.itemView.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     //Toast.makeText(getContext(), "Clicked position ==> " + position, Toast.LENGTH_SHORT).show();
     
     Intent intent = new Intent(getContext(), ActivityViewItem.class);
     
     intent.putExtra("item_details", holder.thisItem);
     intent.putExtra(Variables.USER_ID, getSnapshots().get(position).getUserid());
     startActivity(intent);
    }
   });
  }
  
 }
 
 //View Holder Class
 private class CardViewHolder extends RecyclerView.ViewHolder
 {
  TextView itemTitle, distance;
  ImageView itemImage;
  ShivankUserItems thisItem;
  
  public CardViewHolder(LayoutInflater inflater, ViewGroup parent)
  {
   super(inflater.inflate(R.layout.card_view_item, parent, false));
   itemTitle = itemView.findViewById(R.id.item_title);
   itemImage = itemView.findViewById(R.id.item_image);
   distance = itemView.findViewById(R.id.distance);
  }
  
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