package com.example.geem.activities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class AdapterUserReviews extends RecyclerView.Adapter<AdapterUserReviews.HolderUserReviews>
{
 
 Context context;
 List<TemplateUserReview> reviewList = new ArrayList<>();
 
 public AdapterUserReviews(Context context)
 {
  this.context = context;
 }
 
 @NonNull
 @Override
 public HolderUserReviews onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_user_review, parent, false);
  HolderUserReviews holder = new HolderUserReviews(view);
  return holder;
 }
 
 @Override
 public void onBindViewHolder(@NonNull HolderUserReviews holder, int position)
 {
  TemplateUserReview template = reviewList.get(position);
  Glide.with(context).load(template.getImage()).placeholder(R.drawable.rahul_profile).error(R.drawable.elon).into(holder.image);
  holder.name.setText(template.getName());
  holder.ratingBar.setNumStars(5);
  holder.ratingBar.setRating(template.getRating());
  holder.review.setText(template.getContent());
  
  holder.view.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Intent intent = new Intent(context, ActivityShowUserProfile.class);
    intent.putExtra(Variables.OTHER_ID, template.getProfileId());
    context.startActivity(intent);
   }
  });
 }
 
 @Override
 public int getItemCount()
 {
  return reviewList.size();
 }
 
 public void addItem(TemplateUserReview review)
 {
  reviewList.add(review);
  notifyItemInserted(reviewList.size() - 1);
 }
 
 class HolderUserReviews extends RecyclerView.ViewHolder
 {
  View view;
  CircularImageView image;
  
  TextView name;
  RatingBar ratingBar;
  TextView review;
  
  public HolderUserReviews(@NonNull View view)
  {
   super(view);
   this.view = view;
   
   image = view.findViewById(R.id.image);
   name = view.findViewById(R.id.name);
   ratingBar = view.findViewById(R.id.rating);
   review = view.findViewById(R.id.review);
   
  }
 }
}
