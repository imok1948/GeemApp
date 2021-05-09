package com.example.geem.fragments.browse.requests;

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
import com.example.geem.activities.MainActivity;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.requests.activity.ActivityRequesters;

import java.util.ArrayList;
import java.util.List;

public class AdapterRequestedItems extends RecyclerView.Adapter<AdapterRequestedItems.RequestedItemDetailHolder>
{
 private static final String TAG = "AdapterRequestedItems";
 private List<RequestedItemsTemplate> templatesList = new ArrayList<>();
 private Context context;
 
 public AdapterRequestedItems(Context context)
 {
  this.context = context;
 }
 
 @NonNull
 @Override
 public RequestedItemDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_requested_items, parent, false);
  RequestedItemDetailHolder holder = new RequestedItemDetailHolder(view);
  return holder;
 }
 
 @Override
 public void onBindViewHolder(@NonNull RequestedItemDetailHolder holder, int position)
 {
  RequestedItemsTemplate template = templatesList.get(position);
  
  Glide.with(context).load(template.getImageUrl()).placeholder(R.drawable.rahul_profile).error(R.drawable.elon).centerCrop().into(holder.imageView);
  holder.itemName.setText(template.getName());
  holder.totalRequests.setText(template.getTotalRequests() + " Requests");
  
  holder.view.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Intent intent = new Intent(context, ActivityRequesters.class);
    intent.putExtra(Variables.ITEM_ID, template.getItemId());
    context.startActivity(intent);
   }
  });
  
 }
 
 @Override
 public int getItemCount()
 {
  return templatesList.size();
 }
 
 public void setTemplatesList(List<RequestedItemsTemplate> templatesList)
 {
  this.templatesList = templatesList;
  notifyItemInserted(templatesList.size());
 }
 
 public void insertItems(RequestedItemsTemplate requestedItemsTemplate)
 {
  this.templatesList.add(requestedItemsTemplate);
  notifyItemInserted(this.templatesList.size());
 }
 
 class RequestedItemDetailHolder extends RecyclerView.ViewHolder
 {
  
  public ImageView imageView;
  public TextView itemName;
  public TextView totalRequests;
  public View view;
  
  public RequestedItemDetailHolder(@NonNull View view)
  {
   super(view);
   this.view = view;
   imageView = view.findViewById(R.id.image);
   itemName = view.findViewById(R.id.name);
   totalRequests = view.findViewById(R.id.total_requests);
   
  }
 }
}
