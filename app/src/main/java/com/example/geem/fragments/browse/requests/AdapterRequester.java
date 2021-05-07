package com.example.geem.fragments.browse.requests;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class AdapterRequester extends RecyclerView.Adapter<AdapterRequester.RequesterViewHolder>
{
 
 
 private static final String TAG = "AdapterRequester";
 List<RequesterTemplate> requesterTemplateList = new ArrayList<>();
 Context context;
 
 
 public AdapterRequester(Context context)
 {
  this.context = context;
 }
 
 @NonNull
 @Override
 public AdapterRequester.RequesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_requester, parent, false);
  RequesterViewHolder holder = new RequesterViewHolder(view);
  return holder;
 }
 
 @Override
 public void onBindViewHolder(@NonNull AdapterRequester.RequesterViewHolder holder, int position)
 {
  RequesterTemplate template = requesterTemplateList.get(position);
  Glide.with(context).load(template.getProfilePictureUrl()).placeholder(R.drawable.elon).error(R.drawable.elon).centerCrop().into(holder.profilePicture);
  holder.name.setText(template.getName());
  holder.address.setText(template.getAddress());
  holder.seeProfile.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: see Profile : " + position);
   }
  });
  
  holder.give.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: Give : " + position);
   }
  });
  
  holder.deny.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: Deny : " + position);
   }
  });
 }
 
 @Override
 public int getItemCount()
 {
  return requesterTemplateList.size();
 }
 
 public void addItem(RequesterTemplate template)
 {
  requesterTemplateList.add(template);
  notifyItemInserted(requesterTemplateList.size());
 }
 
 public void addItem(List<RequesterTemplate> templateList)
 {
  for(RequesterTemplate template : templateList)
  {
   requesterTemplateList.add(template);
   notifyItemChanged(requesterTemplateList.size() - 1);
  }
  
 }
 
 class RequesterViewHolder extends RecyclerView.ViewHolder
 {
  
  public CircularImageView profilePicture;
  public TextView name;
  public TextView address;
  public LinearLayout seeProfile;
  public LinearLayout give;
  public LinearLayout deny;
  
  public RequesterViewHolder(@NonNull View view)
  {
   super(view);
   
   profilePicture = view.findViewById(R.id.profile_picture);
   name = view.findViewById(R.id.name);
   address = view.findViewById(R.id.address);
   seeProfile = view.findViewById(R.id.see_profile);
   give = view.findViewById(R.id.give);
   deny = view.findViewById(R.id.deny);
  }
 }
}
