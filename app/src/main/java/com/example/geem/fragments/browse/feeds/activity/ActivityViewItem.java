package com.example.geem.fragments.browse.feeds.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.geem.R;
import com.example.geem.activities.ActivityShowUserProfile;
import com.example.geem.extra.ShivankUserItems;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.messages.activity.MessageActivity;
import com.example.geem.fragments.browse.notifications.DummyTemplate;
import com.example.geem.fragments.browse.notifications.FirebaseNotificationTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Date;

public class ActivityViewItem extends AppCompatActivity
{
 
 private static final String TAG = "ActivityViewItem";
 private static final String PROFILE_COLLECTION_NAME = "dummy_profiles_do_not_delete";
 
 ShivankUserItems item;
 AppBarLayout appBar;
 TextView title, description, user, proximity, menu_title, category;
 public static final int GPS_REQUEST_CODE = 101;
 public static final int NET_REQUEST_CODE = 102;
 LocationManager locationManager;
 Location currentLocation = new Location("");
 View detailsTile;
 Location itemLocation = new Location("");
 
 private ImageView profilePicture;
 
 private String otherId = "";
 private String myId = "";
 private String itemId = "";
 
 //For request and msg things...
 ImageView message;
 ImageView request;
 
 //Firebase
 private static final String FEEDS_COLLECTION_NAME = "fetch_items_final";
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  
  try
  {
   otherId = getIntent().getStringExtra(Variables.OWNER_ID);
   Log.d(TAG, "onCreate: User ID : " + otherId);
   myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
   itemId = getIntent().getStringExtra(Variables.ITEM_ID);
   Log.d(TAG, "onCreate: Item id ==> " + itemId);
  }
  catch(Exception e)
  {
   e.printStackTrace();
   Log.d(TAG, "onCreate: Unable to get user id" + otherId);
   finish();
  }
  
  setContentView(R.layout.activity_view_item);
  fetchItemContentsFromFirebase();
  
  
 }
 
 
 private void fetchItemContentsFromFirebase()
 {
  FirebaseFirestore.getInstance().collection(FEEDS_COLLECTION_NAME).document(itemId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    if(task.isSuccessful())
    {
     item = task.getResult().toObject(ShivankUserItems.class);
     Log.d(TAG, "onComplete: Items ==> " + item);
     
     init();
     getUserLocation();
     setNames();
     setClickListeners();
     setUserNamesAndProfilePicture();
    }
   }
  });
 }
 
 private void setNames()
 {
  title.setText(item.getTitle());
  category.setText(item.getCategory());
  description.setText(item.getDescription());
  
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
  
 }
 
 
 private void setUserNamesAndProfilePicture()
 {
  FirebaseFirestore.getInstance().collection(PROFILE_COLLECTION_NAME).document(otherId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    //This thing can be replaced with better one, but due to lack of time and it's time for dinner, I am leaving with only this version :|
    DummyTemplate template = task.getResult().toObject(DummyTemplate.class);
    Glide.with(getApplicationContext()).load(template.getProfilePictureUrl()).placeholder(R.drawable.ic_tab_profile).error(R.drawable.profile_pic).into(profilePicture);
    user.setText(template.getName());

    findViewById(R.id.owner_picture).setOnClickListener(new View.OnClickListener()
    {
     @Override
     public void onClick(View view)
     {
      Intent intent = new Intent(getApplicationContext(), ActivityShowUserProfile.class);
      intent.putExtra(Variables.OTHER_ID, otherId);
      startActivity(intent);
     }
    });

   }
  });
 }
 
 
 private void setClickListeners()
 {
  request.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    //Toast.makeText(getApplicationContext(), "Requesting item, with other id : " + otherId + " Change logic at" + Thread.currentThread().getStackTrace()[2].getLineNumber(), Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onClick: Requesting item, change with suitable logic at line number : " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    
    FirebaseNotificationTemplate template = new FirebaseNotificationTemplate(new Date().getTime(), Variables.NOTIFICATION_TYPE_REQUEST, myId, otherId, itemId);
    
    FirebaseFirestore.getInstance().collection(Variables.NOTIFICATIONS_COLLECTION_NAME).document().set(template).addOnCompleteListener(new OnCompleteListener<Void>()
    {
     @Override
     public void onComplete(@NonNull Task<Void> task)
     {
      // Toast.makeText(getApplicationContext(), "Item requested ==> " + template, Toast.LENGTH_SHORT).show();
      Toast.makeText(getApplicationContext(), "Item Requested successfully", Toast.LENGTH_SHORT).show();
      Log.d(TAG, "onComplete: Item requested ==> " + template);
     }
    });
   }
  });
  
  message.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: Message things");
    Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
    intent.putExtra(Variables.OTHER_ID, otherId);
    startActivity(intent);
   }
  });

 }
 
 private void init()
 {
  
  
  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
  setSupportActionBar(toolbar);
  
  CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
  toolBarLayout.setTitle(getTitle());
  
  
  request = findViewById(R.id.request);
  message = findViewById(R.id.message);
  profilePicture = findViewById(R.id.owner_picture);
  
  
  appBar = findViewById(R.id.app_bar);
  detailsTile = findViewById(R.id.details_tile);
  title = detailsTile.findViewById(R.id.item_name);
  category = detailsTile.findViewById(R.id.item_category);
  proximity = detailsTile.findViewById(R.id.item_proximity);
  description = detailsTile.findViewById(R.id.item_description);
  user = detailsTile.findViewById(R.id.item_owner);
  
  
 }
 
 
 @Override
 protected void onResume()
 {
  super.onResume();
  
  
  //  double itemLatitude = item.getLatitude();
  //  double itemLongitude = item.getLongitude();
  //  itemLocation.setLatitude(itemLatitude);
  //  itemLocation.setLongitude(itemLongitude);
  //  float distanceInKM = currentLocation.distanceTo(itemLocation) / 1000;
  //  proximity.setText("Loading...");
 }
 
 
 private void getUserLocation()
 {
  locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
   float distanceInKM = currentLocation.distanceTo(itemLocation) / 1000;
   proximity.setText(new DecimalFormat("##.#").format(distanceInKM) + " kms away");
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