package com.example.geem.fragments.browse.requests.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.requests.AdapterRequester;
import com.example.geem.fragments.browse.requests.FragmentRequester;

public class ActivityRequesters extends AppCompatActivity
{
 private static final String TAG = "ActivityRequesters";
 
 private String itemId;
 
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
    Log.d(TAG, "onCreate: Received Item Id ==> " + itemId);
    init();
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
 
 private void init()
 {
  adapterRequester = new AdapterRequester(getApplicationContext());
  recyclerView = findViewById(R.id.recycler_view);
  recyclerView.setAdapter(adapterRequester);
  recyclerView.setLayoutManager(new LinearLayoutManager(this));
  adapterRequester.addItem(FragmentRequester.getTemplateList(10));
 }
 
 
 
 
}






















