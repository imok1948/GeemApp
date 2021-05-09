package com.example.geem.fragments.browse.requests.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.notifications.DummyTemplate;
import com.example.geem.fragments.browse.notifications.FragmentNotifications;
import com.example.geem.fragments.browse.notifications.FirebaseNotificationTemplate;
import com.example.geem.fragments.browse.requests.AdapterRequester;
import com.example.geem.fragments.browse.requests.RequesterTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ActivityRequesters extends AppCompatActivity
{
 private static final String TAG = "ActivityRequesters";
 private String itemId;
 private String notificationType;
 private String notificationId;
 //
 private RecyclerView recyclerView;
 private AdapterRequester adapterRequester;
 
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_requesters);
  
  boolean loggedIn = true;
  if(loggedIn)
  {
   try
   {
    itemId = getIntent().getStringExtra(Variables.ITEM_ID);
    notificationType = getIntent().getStringExtra(Variables.NOTIFICATION_TYPE_FOR_PASSING_ACTIVITY);
    notificationId = getIntent().getStringExtra(Variables.NOTIFICATION_ID_FOR_PASSING_ACTIVITY);
    //Not print all the profiles with itemid
    Log.d(TAG, "onCreate: Received Item Id ==> " + itemId);
    Log.d(TAG, "onCreate: Notification Type ==> " + notificationType);
    Log.d(TAG, "onCreate: Received Notification id ==> " + notificationId);
    init();
    if(notificationType.equals(Variables.NOTIFICATION_TYPE_RESPONSE))
    {
     fetchGiver();
    }
    else
    {
     fetchAllRequesters();
    }
    
   }
   catch(Exception e)
   {
    e.printStackTrace();
   }
  }
  else
  {
   finish();
  }
 }
 
 private void fetchGiver()
 {
  FirebaseFirestore.getInstance().collection(Variables.NOTIFICATIONS_COLLECTION_NAME).document(notificationId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    if(task.isSuccessful())
    {
     FirebaseNotificationTemplate notificationTemplate = task.getResult().toObject(FirebaseNotificationTemplate.class);
     Log.d(TAG, "onComplete: Received NotificationTemplate ==> " + notificationTemplate);
     
     FirebaseFirestore.getInstance().collection(Variables.PROFILE_COLLECTION_NAME).document(notificationTemplate.getSenderId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
     {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> profileTask)
      {
       if(profileTask.isSuccessful())
       {
        Log.d(TAG, "onComplete: Requested by ==> " + profileTask.getResult().toObject(DummyTemplate.class));
        DummyTemplate template = profileTask.getResult().toObject(DummyTemplate.class);
        
        RequesterTemplate requesterTemplate = new RequesterTemplate(
         template.getProfilePictureUrl(),
         template.getName(),
         template.getCity(),
         profileTask.getResult().getId(),
         notificationTemplate.getItemId(),
         task.getResult().getId(),
         notificationTemplate.getType());
 
        Log.d(TAG, "onComplete: Added notification : "+task.getResult().getId());
        
        
        adapterRequester.addItem(requesterTemplate);
       }
      }
     });
     
     
    }
   }
  });
 }
 
 private void fetchAllRequesters()
 {
  FirebaseFirestore.getInstance().collection(FragmentNotifications.NOTIFICATIONS_COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     for(DocumentSnapshot snapshot : task.getResult())
     {
      FirebaseNotificationTemplate template = snapshot.toObject(FirebaseNotificationTemplate.class);
      if(template.getItemId().equals(itemId))
      {
       Log.d(TAG, "onComplete: Template2 ==> " + template);
       template.setNotificationId(snapshot.getId());
       fetchAndSetProfileDetails(template, snapshot.getId());
      }
     }
    }
   }
  });
 }
 
 private void fetchAndSetProfileDetails(FirebaseNotificationTemplate firebaseNotificationTemplate, String notificationId)
 {
  Log.d(TAG, "fetchAndSetProfileDetails: Firebase template ==> " + firebaseNotificationTemplate.getSenderId());
  
  
  FirebaseFirestore.getInstance().collection(Variables.PROFILE_COLLECTION_NAME).document(firebaseNotificationTemplate.getSenderId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    if(task.isSuccessful())
    {
     Log.d(TAG, "onComplete: Requested by ==> " + task.getResult().toObject(DummyTemplate.class));
     DummyTemplate template = task.getResult().toObject(DummyTemplate.class);
     
     RequesterTemplate requesterTemplate = new RequesterTemplate(
      template.getProfilePictureUrl(),
      template.getName(),
      template.getCity(),
      firebaseNotificationTemplate.getSenderId(),
      firebaseNotificationTemplate.getItemId(),
      notificationId,
      firebaseNotificationTemplate.getType());
     adapterRequester.addItem(requesterTemplate);
    }
   }
  });
 }
 
 private void init()
 {
  recyclerView = findViewById(R.id.recycler_view);
  adapterRequester = new AdapterRequester(this);
  recyclerView.setAdapter(adapterRequester);
  recyclerView.setLayoutManager(new LinearLayoutManager(this));
 }
 
 
}
























































