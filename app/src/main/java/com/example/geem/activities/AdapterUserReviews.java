package com.example.geem.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
  return null;
 }
 
 @Override
 public void onBindViewHolder(@NonNull HolderUserReviews holder, int position)
 {
 
 }
 
 @Override
 public int getItemCount()
 {
  return 0;
 }
 
 public void addItem(TemplateUserReview review)
 {
  reviewList.add(review);
  notifyItemInserted(reviewList.size() - 1);
 }
 
 class HolderUserReviews extends RecyclerView.ViewHolder
 {
  
  public HolderUserReviews(@NonNull View itemView)
  {
   super(itemView);
  }
 }
}
