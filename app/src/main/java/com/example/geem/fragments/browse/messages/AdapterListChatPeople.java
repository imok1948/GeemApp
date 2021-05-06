package com.example.geem.fragments.browse.messages;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geem.R;
import com.example.geem.extra.TimeDetails;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class AdapterListChatPeople extends RecyclerView.Adapter<AdapterListChatPeople.ChatPeopleDetailHolder>
{
 
 private static final String TAG = "AdapterListChatPeople";
 
 private Context context;
 private List<ChatPeople> chatPeopleList = new ArrayList<>();
 
 public AdapterListChatPeople(Context context, List<ChatPeople> chatPeopleList)
 {
  this.context = context;
  this.chatPeopleList = chatPeopleList;
 }
 
 @NonNull
 @Override
 public ChatPeopleDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_people_chat_detail, parent, false);
  ChatPeopleDetailHolder holder = new ChatPeopleDetailHolder(view);
  return holder;
 }
 
 @Override
 public void onBindViewHolder(@NonNull ChatPeopleDetailHolder holder, int position)
 {/*
  holder.name.setText("Rahul Meena");
  holder.time.setText("20:00PM");
  holder.profilePicture.setImageResource(R.drawable.profile_pic);
  */
  ChatPeople chatPeople = chatPeopleList.get(position);
  
  holder.name.setText(chatPeople.getName());
  holder.time.setText(chatPeople.getTimeDetails().getTime() + ", " + chatPeople.getTimeDetails().getDate());
  holder.message.setText(chatPeople.getMessage());
  holder.profilePicture.setImageResource(chatPeople.getProfilePicture());
  
  holder.layoutParent.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: position ==> " + position);
   }
  });
 }
 
 @Override
 public int getItemCount()
 {
  return chatPeopleList.size();
 }
 
 class ChatPeopleDetailHolder extends RecyclerView.ViewHolder
 {
  public CircularImageView profilePicture;
  public TextView name;
  public TextView time;
  public TextView message;
  public View layoutParent;
  
  public ChatPeopleDetailHolder(@NonNull View view)
  {
   super(view);
   profilePicture = view.findViewById(R.id.profile_picture);
   name = view.findViewById(R.id.name);
   time = view.findViewById(R.id.message_time);
   message = view.findViewById(R.id.last_message_description);
   layoutParent = view.findViewById(R.id.layout_parent);
  }
 }
}
