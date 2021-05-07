package com.example.geem.fragments.browse.feeds.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.geem.R;
import com.example.geem.extra.ShivankUserItems;
import com.example.geem.fragments.browse.notifications.FragmentNotifications;
import com.example.geem.fragments.browse.notifications.NotificationTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import java.util.Date;

public class ActivityViewItem extends AppCompatActivity
{
 ShivankUserItems item;
 AppBarLayout appBar;
 
 private static final String TAG = "ActivityViewItem";
 private static final String MY_ID = "rahul";
 private static final String OTHER_ID = "shiwank";
 private static final String ITEM_ID = "3h92oriykxqftRDalUPX";
 
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_view_item);
  item = (ShivankUserItems) getIntent().getSerializableExtra("item_details");
  Log.d(TAG, "onCreate: Printing Item : " + item);
  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
  setSupportActionBar(toolbar);
  CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
  toolBarLayout.setTitle(getTitle());
  appBar = findViewById(R.id.app_bar);
  
  init();
  
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
 
 
 private void init()
 {
  findViewById(R.id.button_give).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    CollectionReference feedsCollectionReference = FirebaseFirestore.getInstance().collection(FragmentNotifications.NOTIFICATIONS_COLLECTION_NAME);
    
    boolean loggedIn = FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null;
    if(getIntent().getStringExtra("itemId") != null && loggedIn)
    {
     String myId = "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
     
     NotificationTemplate template = new NotificationTemplate(new Date().getTime(), "request", myId, item.getUserid(), getIntent().getStringExtra("itemId"));
     Log.d(TAG, "onClick: receiver id : " + item.getUserid());
     feedsCollectionReference.document().set(template).addOnCompleteListener(new OnCompleteListener<Void>()
     {
      @Override
      public void onComplete(@NonNull Task<Void> task)
      {
       if(task.isSuccessful())
       {
        Log.d(TAG, "onComplete: Request uploading, Success");
       }
       else
       {
        Log.d(TAG, "onComplete: Request uploading, Failed" + task.getException());
       }
      }
     });
    }
    else
    {
     try
     {
      Log.d(TAG, "onClick: Cannot request item, UserId : " + FirebaseAuth.getInstance().getCurrentUser() + ", ItemId: " + getIntent().getStringExtra("itemId"));
     }
     catch(Exception e)
     {
      e.printStackTrace();
     }
    }
    
   }
  });
 }
}