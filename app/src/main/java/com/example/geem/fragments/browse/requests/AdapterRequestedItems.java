package com.example.geem.fragments.browse.requests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.fragments.browse.messages.activity.AdapterMessages;

import java.util.ArrayList;
import java.util.List;

public class AdapterRequestedItems extends RecyclerView.Adapter<AdapterRequestedItems.RequestedItemDetailHolder>
{
 
 private List<RequestedItemsTemplate> requestedItemsTemplates = new ArrayList<>();
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
  //holder.imageView.setImageResource(R.drawable.elon);
  Glide.with(context).load(requestedItemsTemplates.get(position).getImageUrl()).placeholder(R.drawable.elon).error(R.drawable.fib).centerCrop().into(holder.imageView);
  holder.itemName.setText(requestedItemsTemplates.get(position).getName());
  holder.totalRequests.setText(requestedItemsTemplates.get(position).getTotalRequests() + " Requests");
 }
 
 @Override
 public int getItemCount()
 {
  return requestedItemsTemplates.size();
 }
 
 public void setRequestedItemsTemplates(List<RequestedItemsTemplate> requestedItemsTemplates)
 {
  this.requestedItemsTemplates = requestedItemsTemplates;
  notifyItemInserted(requestedItemsTemplates.size());
 }
 
 public void insertItems(RequestedItemsTemplate requestedItemsTemplate)
 {
  this.requestedItemsTemplates.add(requestedItemsTemplate);
  notifyItemInserted(this.requestedItemsTemplates.size());
 }
 
 class RequestedItemDetailHolder extends RecyclerView.ViewHolder
 {
  
  public ImageView imageView;
  public TextView itemName;
  public TextView totalRequests;
  
  public RequestedItemDetailHolder(@NonNull View view)
  {
   super(view);
   imageView = view.findViewById(R.id.image);
   itemName = view.findViewById(R.id.name);
   totalRequests = view.findViewById(R.id.total_requests);
   
  }
 }
}
