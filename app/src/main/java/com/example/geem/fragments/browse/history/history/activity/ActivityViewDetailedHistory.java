package com.example.geem.fragments.browse.history.history.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.extra.ShivankUserItems;
import com.example.geem.extra.TimeDetails;
import com.example.geem.extra.Variables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityViewDetailedHistory extends AppCompatActivity
{
 private static final String TAG = "ActivityViewDetailedHistory";
 
 private String itemId;
 
 
 private ImageView image;
 private TextView title, postedTime, category, availability, taker, address, description;
 private LinearLayout layout;
 
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_view_detailed_history);
  
  itemId = getIntent().getStringExtra(Variables.ITEM_ID);
  init();
  fetchItemDetails();
 }
 
 private void fetchItemDetails()
 {
  FirebaseFirestore.getInstance().collection(Variables.FEEDS_COLLECTION_NAME).document(itemId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    if(task.isSuccessful())
    {
     ShivankUserItems item = task.getResult().toObject(ShivankUserItems.class);
     Glide.with(getApplicationContext()).load(item.getImage()).placeholder(R.drawable.rahul_profile).error(R.drawable.elon).into(image);
     title.setText(item.getTitle());
     postedTime.setText(new TimeDetails(item.getTimestamp().getTime()).getDate());
     category.setText(item.getCategory());
     
     if(item.isIsavailable())
     {
      availability.setText("Yes");
      taker.setText("Ajay");
     }
     else
     {
      availability.setText("NO");
      layout.setVisibility(View.GONE);
     }
     
     address.setText(item.getAddress());
     description.setText(item.getDescription());
    }
   }
  });
 }
 
 private void init()
 {
  image = findViewById(R.id.image);
  title = findViewById(R.id.title);
  postedTime = findViewById(R.id.date);
  category = findViewById(R.id.category);
  availability = findViewById(R.id.available);
  taker = findViewById(R.id.taker);
  address = findViewById(R.id.address);
  description = findViewById(R.id.description);
  
  layout = findViewById(R.id.layout_taker);
 }
}