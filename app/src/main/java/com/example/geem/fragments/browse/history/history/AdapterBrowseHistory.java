package com.example.geem.fragments.browse.history.history;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.history.history.activity.ActivityViewDetailedHistory;

import java.util.ArrayList;
import java.util.List;

public class AdapterBrowseHistory extends RecyclerView.Adapter<AdapterBrowseHistory.BrowseHistoryHolder>
{
 private static final String TAG = "AdapterBrowseHistory";
 private Context context;
 private List<TemplateBrowseHistory> historyList = new ArrayList<>();
 
 public AdapterBrowseHistory(Context context)
 {
  this.context = context;
 }
 
 @NonNull
 @Override
 public BrowseHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_browse_history, parent, false);
  BrowseHistoryHolder holder = new BrowseHistoryHolder(view);
  return holder;
 }
 
 @Override
 public void onBindViewHolder(@NonNull BrowseHistoryHolder holder, int position)
 {
  TemplateBrowseHistory template = historyList.get(position);
  
  Log.d(TAG, "onBindViewHolder: Received template ==> " + template);
  
  holder.title.setText(template.getTitle());
  holder.description.setText(template.getDescription());
  Glide.with(context).load(template.getImageUrl()).placeholder(R.drawable.rahul_profile).error(R.drawable.elon).into(holder.image);
  
  holder.view.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Intent intent = new Intent(context, ActivityViewDetailedHistory.class);
    intent.putExtra(Variables.ITEM_ID, template.getItemId());
    context.startActivity(intent);
   }
  });
 }
 
 @Override
 public int getItemCount()
 {
  return historyList.size();
 }
 
 
 public void addItem(TemplateBrowseHistory template)
 {
  historyList.add(template);
  notifyItemInserted(historyList.size());
 }
 
 class BrowseHistoryHolder extends RecyclerView.ViewHolder
 {
  ImageView image;
  TextView title, description;
  View view;
  
  public BrowseHistoryHolder(@NonNull View view)
  {
   super(view);
   this.view = view;
   image = view.findViewById(R.id.image);
   title = view.findViewById(R.id.title);
   description = view.findViewById(R.id.description);
  }
 }
}
