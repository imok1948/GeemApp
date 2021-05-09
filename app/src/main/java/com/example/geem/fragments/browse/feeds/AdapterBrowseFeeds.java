package com.example.geem.fragments.browse.feeds;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
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
import com.example.geem.fragments.browse.feeds.activity.ActivityViewItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterBrowseFeeds extends RecyclerView.Adapter<AdapterBrowseFeeds.BrowseFeedsHolder>
{
 
 
 private static final String TAG = "AdapterBrowseFeeds";
 private Context context;
 private List<FeedsTemplate> templateList = new ArrayList<>();
 
 
 public AdapterBrowseFeeds(Context context)
 {
  this.context = context;
 }
 
 @NonNull
 @Override
 public BrowseFeedsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item, parent, false);
  BrowseFeedsHolder holder = new BrowseFeedsHolder(view);
  return holder;
 }
 
 @Override
 public void onBindViewHolder(@NonNull BrowseFeedsHolder holder, int position)
 {
  FeedsTemplate template = templateList.get(position);
  holder.distance.setText(new DecimalFormat("##.#").format(template.getDistance()) + " kms away");
  holder.itemTitle.setText(template.getTitle());
  Glide.with(holder.itemImage.getContext()).load(template.getImage()).into(holder.itemImage);
  
  
  holder.view.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: Position ==> " + position);
    
    Intent intent = new Intent(context, ActivityViewItem.class);
    intent.putExtra(Variables.OWNER_ID, template.getUserid());
    intent.putExtra(Variables.ITEM_ID, template.getItemId());
    context.startActivity(intent);
    
   }
  });
  
 }
 
 @Override
 public int getItemCount()
 {
  return templateList.size();
 }
 
 public void addItem(FeedsTemplate template)
 {
  templateList.add(template);
  notifyItemInserted(templateList.size() - 1);
 }
 
 
 class BrowseFeedsHolder extends RecyclerView.ViewHolder
 {
  TextView itemTitle, distance;
  ImageView itemImage;
  
  View view;
  
  public BrowseFeedsHolder(@NonNull View view)
  {
   super(view);
   this.view = view;
   itemTitle = view.findViewById(R.id.item_title);
   itemImage = view.findViewById(R.id.item_image);
   distance = view.findViewById(R.id.distance);
  }
 }
}