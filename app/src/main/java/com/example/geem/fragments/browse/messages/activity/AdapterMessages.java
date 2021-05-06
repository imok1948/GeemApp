package com.example.geem.fragments.browse.messages.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geem.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterMessages extends RecyclerView.Adapter<AdapterMessages.MessagesHolder>
{
 private static final String TAG = "AdapterMessages";
 
 private static final int MY_ID = 78;
 private static final int OTHER_ID = 98;
 
 private Context context;
 private List<MessageTemplateForAdapter> messageTemplateForAdapterList = new ArrayList<>();
 
 public AdapterMessages(Context context, List<MessageTemplateForAdapter> messageTemplateForAdapterList)
 {
  this.context = context;
  this.messageTemplateForAdapterList = messageTemplateForAdapterList;
  
  for(MessageTemplateForAdapter messageTemplateForAdapter : messageTemplateForAdapterList)
  {
   Log.d(TAG, "AdapterMessags: message ==>" + messageTemplateForAdapter);
  }
 }
 
 @NonNull
 @Override
 public MessagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  MessagesHolder holder;
  if(viewType == MY_ID)
  {
   View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_messages_me, parent, false);
   holder = new MessagesHolder(view);
  }
  else
  {
   View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_messages_other, parent, false);
   holder = new MessagesHolder(view);
  }
  return holder;
 }
 
 @Override
 public void onBindViewHolder(@NonNull MessagesHolder holder, int position)
 {
  
  MessageTemplateForAdapter messageTemplateForAdapter = messageTemplateForAdapterList.get(position);
  
  holder.content.setText(messageTemplateForAdapter.getData());
  holder.time.setText(messageTemplateForAdapter.getTimeDetails().getTime() + ", " + messageTemplateForAdapter.getTimeDetails().getDate());
  
  /*holder.content.setText("Hey, Rahul how are you ???");
  holder.time.setText("22:00, May 7");
  */
  
 /* holder.layoutParent.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: Position ==> " + position);
   }
  });*/
  
 }
 
 @Override
 public int getItemCount()
 {
  //return 10;
  return messageTemplateForAdapterList.size();
 }
 
 @Override
 public int getItemViewType(int position)
 {
  if(messageTemplateForAdapterList.get(position).isSentByMe())
  {
   return MY_ID;
  }
  else
  {
   return OTHER_ID;
  }
 }
 
 public void insertItem(MessageTemplateForAdapter messageTemplateForAdapter)
 {
  this.messageTemplateForAdapterList.add(messageTemplateForAdapter);
  notifyItemInserted(messageTemplateForAdapterList.size());
 }
 
 
 public void setDelivered(int position)
 {
 
 }
 
 public int getMessagesSize()
 {
  return messageTemplateForAdapterList.size();
 }
 
 
 class MessagesHolder extends RecyclerView.ViewHolder
 {
  public TextView content;
  public TextView time;
  public View layoutParent;
  
  public MessagesHolder(@NonNull View view)
  {
   super(view);
   content = view.findViewById(R.id.text_content);
   time = view.findViewById(R.id.text_time);
   layoutParent = view.findViewById(R.id.layout_parent);
  }
 }
}


