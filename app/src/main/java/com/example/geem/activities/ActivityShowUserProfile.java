package com.example.geem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.extra.TimeDetails;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.notifications.DummyTemplate;
import com.example.geem.fragments.browse.requests.TemplateFirebaseReview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ActivityShowUserProfile extends AppCompatActivity
{
 
 // [10, 20, 10, 50, 60]
 
 
 private static final String TAG = "ActivityShowUserProfile";
 
 //Views
 private CircularImageView image;
 private TextView name, joiningDate, city, totalItemTaken, totalItemGiven, averageRating, oneStarCount, twoStarCount, threeStarCount, fourStarCount, fiveStarCount;
 private ProgressBar oneStarRating, twoStarRating, threeStarRating, fourStarRating, fiveStarRating;
 private RecyclerView recyclerView;
 
 private String myId;
 
 //Firebase
 private static final String PROFILE_COLLECTION_NAME = "dummy_profiles_do_not_delete";
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_show_user_profile);
  init();
  
  myId = "Eaw3iPx39HUyNp1DlCsU7NLcUuL2";
  
  if(getIntent().getStringExtra(Variables.OTHER_ID) != null)
  {
   myId = getIntent().getStringExtra(Variables.OTHER_ID);
  }
  
  
  this.myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
  initComponents();
  initializeFirebase(myId);
 }
 
 private void initComponents()
 {
  FirebaseFirestore.getInstance().collection(Variables.REVIEW_COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     
     int[] ratings = new int[5];
     
     for(DocumentSnapshot snapshot : task.getResult())
     {
      TemplateFirebaseReview reviewTemplate = snapshot.toObject(TemplateFirebaseReview.class);
      if(reviewTemplate.getToId().equals(myId))
      {
       try
       {
        ratings[reviewTemplate.getRating() - 1] += 1;
        fetchProfilePictureAndAddToRecycleView(reviewTemplate.getFromId(), reviewTemplate.getRating(), reviewTemplate.getReview());
       }
       catch(Exception e)
       {
        e.printStackTrace();
       }
      }
     }
     
     //[10, 20, 10, 60, 50]
     int totalRatings = 0;
     int totalRaters = 0;
     
     for(int i = 0; i < ratings.length; i++)
     {
      totalRatings += (ratings[i] * (i + 1)); //10*1
      totalRaters += ratings[i];
     }
     
     
    }
   }
  });
 }
 
 private void fetchProfilePictureAndAddToRecycleView(String fromId, int rating, String review)
 {
 
 }
 
 
 private void setMyInfo()
 {
  FirebaseFirestore.getInstance().collection(Variables.PROFILE_COLLECTION_NAME).document(myId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    DummyTemplate template = task.getResult().toObject(DummyTemplate.class);
    Glide.with(getApplicationContext()).load(template.getProfilePictureUrl()).placeholder(R.drawable.rahul_profile).error(R.drawable.ic_tab_profile).into(image);
    name.setText(template.getName());
    joiningDate.setText(new TimeDetails(template.getJoiningTime()).getDate());
    city.setText(template.getCity());
   }
  });
 }
 
 
 private void initializeFirebase(String userId)
 {
  FirebaseFirestore.getInstance().collection(Variables.PROFILE_COLLECTION_NAME).document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    
    List<Float> givenRatings;
    List<Integer> givenRatingTotal;
    
    List<Float> takenRatings;
    List<Integer> takenRatingTotal;
    
    
    DummyTemplate template = task.getResult().toObject(DummyTemplate.class);
    
    Glide.with(getApplicationContext()).load(template.getProfilePictureUrl()).placeholder(R.drawable.rahul_profile).error(R.drawable.ic_tab_profile).into(image);
    name.setText(template.getName());
    joiningDate.setText(new TimeDetails(template.getJoiningTime()).getDate());
    city.setText(template.getCity());
    
    
    totalItemGiven.setText(55 + "");
    totalItemTaken.setText(21 + "");
    
    float averageRatingTemp = 0;
    for(float rating : template.getTakenRatings())
    {
     averageRatingTemp += rating;
    }
    averageRatingTemp /= 5;
    
    
    int totalRater = 0;
    for(int i : template.getTotalGivenRatings())
    {
     totalRater += i;
    }
    
    if(totalRater == 0)
    {
     totalRater = 1000000;
    }
    
    averageRating.setText(String.format("%.2f", averageRatingTemp));
    oneStarRating.setProgress((int) ((template.getTakenRatings().get(0) / 5) * 100));
    twoStarRating.setProgress((int) ((template.getTakenRatings().get(1) / 5) * 100));
    threeStarRating.setProgress((int) ((template.getTakenRatings().get(2) / 5) * 100));
    fourStarRating.setProgress((int) ((template.getTakenRatings().get(3) / 5) * 100));
    fiveStarRating.setProgress((int) ((template.getTakenRatings().get(4) / 5) * 100));
    
    Log.d(TAG, "onComplete: Taken 0 ==> " + template.getTakenRatings().get(0));
    Log.d(TAG, "onComplete: Taken 1 ==> " + template.getTakenRatings().get(1));
    Log.d(TAG, "onComplete: Taken 2 ==> " + template.getTakenRatings().get(2));
    
    Log.d(TAG, "onComplete: Divided 0 ==> " + template.getTakenRatings().get(0) / totalRater);
    Log.d(TAG, "onComplete: *100 0 ==> " + (template.getTakenRatings().get(0) / totalRater) * 100);
    Log.d(TAG, "onComplete: *100 to int 0 ==> " + (int) ((template.getTakenRatings().get(0) / totalRater) * 100));
    
    
    Log.d(TAG, "onComplete: Progree ==> " + oneStarRating.getProgress());
    Log.d(TAG, "onComplete: 5==> " + (int) ((template.getTakenRatings().get(4) / totalRater) * 100));
    
    oneStarCount.setText("" + template.getTotalTakenRatings().get(0));
    twoStarCount.setText("" + template.getTotalTakenRatings().get(1));
    threeStarCount.setText("" + template.getTotalTakenRatings().get(2));
    fourStarCount.setText("" + template.getTotalTakenRatings().get(3));
    fiveStarCount.setText("" + template.getTotalTakenRatings().get(4));
    
   }
  });
  
 }
 
 private void init()
 {
  image = findViewById(R.id.image);
  name = findViewById(R.id.name);
  joiningDate = findViewById(R.id.joining_date);
  city = findViewById(R.id.city);
  totalItemTaken = findViewById(R.id.total_item_taken);
  totalItemGiven = findViewById(R.id.total_item_given);
  
  averageRating = findViewById(R.id.average_rating);
  
  oneStarCount = findViewById(R.id.one_star_count);
  twoStarCount = findViewById(R.id.two_star_count);
  threeStarCount = findViewById(R.id.three_star_count);
  fourStarCount = findViewById(R.id.four_star_count);
  fiveStarCount = findViewById(R.id.five_star_count);
  
  oneStarRating = findViewById(R.id.one_star_rating);
  twoStarRating = findViewById(R.id.two_star_rating);
  threeStarRating = findViewById(R.id.three_star_rating);
  fourStarRating = findViewById(R.id.four_star_rating);
  fiveStarRating = findViewById(R.id.five_star_rating);
  
  recyclerView = findViewById(R.id.recycler_view);
  
 }
}