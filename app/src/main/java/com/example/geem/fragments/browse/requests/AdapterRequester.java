package com.example.geem.fragments.browse.requests;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.activities.ActivityShowUserProfile;
import com.example.geem.extra.FeildsNames;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.notifications.FirebaseNotificationTemplate;
import com.example.geem.fragments.browse.notifications.FragmentNotifications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Date;
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
  RequesterTemplate requesterTemplate = requesterTemplateList.get(position);
  Glide.with(context).load(requesterTemplate.getProfilePictureUrl()).placeholder(R.drawable.elon).error(R.drawable.elon).centerCrop().into(holder.profilePicture);
  holder.name.setText(requesterTemplate.getName());
  holder.address.setText(requesterTemplate.getAddress());
  
  holder.layoutProfile.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Intent intent = new Intent(context, ActivityShowUserProfile.class);
    intent.putExtra(Variables.OTHER_ID, requesterTemplate.getUserId());
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
   }
  });
  
  
  holder.layoutProfile.setOnLongClickListener(new View.OnLongClickListener()
  {
   @Override
   public boolean onLongClick(View view)
   {
    Log.d(TAG, "onLongClick: Notification id ==> " + requesterTemplate.getNotificationId());
    return true;
   }
  });
  
  
  if(requesterTemplate.getNotificationType().equals(Variables.NOTIFICATION_TYPE_REQUEST))
  {
   holder.give.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     Log.d(TAG, "onClick: Give : " + position);
     verifyGive(requesterTemplate, position);
    }
   });
   
   
   holder.deny.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     Log.d(TAG, "onClick: Deny : " + position);
     verifyDenyAndDoNext(requesterTemplate, position);
    }
   });
  }
  else //Respone
  {
   holder.textGive.setText("Taken");
   holder.textDeny.setText("Decline");
   holder.give.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     Log.d(TAG, "onClick: Give : " + position);
     confirmAndRate(requesterTemplate, position);
    }
   });
   
   
   holder.deny.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     Log.d(TAG, "onClick: Deny : " + position);
     confirmAndRemoveNotification(requesterTemplate, position);
    }
   });
  }
  
 }
 
 private void confirmAndRemoveNotification(RequesterTemplate template, int position)
 {
  Dialog dialog = new Dialog(context);
  dialog.setContentView(R.layout.dialog_confirm);
  dialog.show();
  dialog.setCancelable(true);
  
  Window window = dialog.getWindow();
  window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
  
  ((TextView) dialog.findViewById(R.id.name)).setText(template.getName());
  ((TextView) dialog.findViewById(R.id.city)).setText(template.getAddress());
  ((TextView) dialog.findViewById(R.id.title)).setText("Are you sure to deny ");
  
  dialog.findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: Deleting notification id ==> " + template.getNotificationId());
    FirebaseFirestore.getInstance().collection(Variables.NOTIFICATIONS_COLLECTION_NAME).document(template.getNotificationId()).delete();
    
    Toast.makeText(context, "Ok, item was not given", Toast.LENGTH_SHORT).show();
    requesterTemplateList.remove(position);
    notifyItemRemoved(position);
    dialog.cancel();
   }
  });
  dialog.findViewById(R.id.button_no).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    dialog.cancel();
   }
  });
 }
 
 private void confirmAndRate(RequesterTemplate template, int position)
 {
  Dialog dialog = new Dialog(context);
  dialog.setContentView(R.layout.dialog_rate);
  dialog.show();
  dialog.setCancelable(true);
  
  Window window = dialog.getWindow();
  window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
  
  ((TextView) dialog.findViewById(R.id.name)).setText(template.getName());
  ((TextView) dialog.findViewById(R.id.city)).setText(template.getAddress());
  ((TextView) dialog.findViewById(R.id.title)).setText("Rate & Review");
  
  ((AppCompatButton) dialog.findViewById(R.id.button_yes)).setText("Rate");
  ((AppCompatButton) dialog.findViewById(R.id.button_no)).setText("Cancel");
  
  Glide.with(context).load(template.getProfilePictureUrl()).placeholder(R.drawable.rahul_profile).error(R.drawable.elon).into((CircularImageView) dialog.findViewById(R.id.profile_picture));
  
  dialog.findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Log.d(TAG, "onClick: Deleting notification id ==> " + template.getNotificationId());
    FirebaseFirestore.getInstance().collection(Variables.NOTIFICATIONS_COLLECTION_NAME).document(template.getNotificationId()).delete();
    FirebaseNotificationTemplate ratingNotificationTemplate = new FirebaseNotificationTemplate(new Date().getTime(), Variables.NOTIFICATION_RATING_REQUEST, myId, template.getUserId(), template.getItemId(), true, true, template.getNotificationId());
    FirebaseFirestore.getInstance().collection(Variables.NOTIFICATIONS_COLLECTION_NAME).document().set(ratingNotificationTemplate);
    AppCompatRatingBar ratingBar = dialog.findViewById(R.id.rating_bar);
    int rating = (int) ratingBar.getRating();
    String review = ((EditText) dialog.findViewById(R.id.review)).getText() + "";
    insertRateInFirebase(template, rating, review);
    requesterTemplateList.remove(position);
    notifyItemRemoved(position);
    dialog.cancel();
   }
  });
  
  dialog.findViewById(R.id.button_no).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    dialog.cancel();
   }
  });
 }
 
 
 private void verifyGive(RequesterTemplate template, int position)
 {
  Dialog dialog = new Dialog(context);
  dialog.setContentView(R.layout.dialog_confirm);
  dialog.show();
  dialog.setCancelable(true);
  
  Window window = dialog.getWindow();
  window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
  
  ((TextView) dialog.findViewById(R.id.name)).setText(template.getName());
  ((TextView) dialog.findViewById(R.id.city)).setText(template.getAddress());
  ((TextView) dialog.findViewById(R.id.title)).setText("Confirm Giving");
  
  Glide.with(context).load(template.getProfilePictureUrl()).placeholder(R.drawable.rahul_profile).error(R.drawable.elon).into((CircularImageView) dialog.findViewById(R.id.profile_picture));
  
  dialog.findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: Deleting notification id ==> " + template.getNotificationId());
    FirebaseFirestore.getInstance().collection(Variables.NOTIFICATIONS_COLLECTION_NAME).document(template.getNotificationId()).delete();
    
    Log.d(TAG, "onClick: Dialog ==> Yes");
    String myId = "";
    
    try
    {
     myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
     Log.d(TAG, "onClick: My id received in adapter ==> " + myId);
    }
    catch(Exception e)
    {
     e.printStackTrace();
     Log.d(TAG, "onClick: Stack Trace ==> " + e.getMessage());
    }
    
    
    //Create a response
    FirebaseNotificationTemplate notificationTemplate = new FirebaseNotificationTemplate(new Date().getTime(), Variables.NOTIFICATION_TYPE_RESPONSE, myId, template.getUserId(), template.getItemId(), true, false, template.getNotificationId());
    Log.d(TAG, "onClick: Notification template to upload ==> " + notificationTemplate);
    
    FirebaseFirestore.getInstance().collection(FragmentNotifications.NOTIFICATIONS_COLLECTION_NAME).document().set(notificationTemplate).addOnCompleteListener(new OnCompleteListener<Void>()
    {
     @Override
     public void onComplete(@NonNull Task<Void> task)
     {
      if(task.isSuccessful())
      {
       Log.d(TAG, "onComplete: Responed with yes ==> " + template);
       Toast.makeText(context, "Confirmed", Toast.LENGTH_SHORT).show();
       requesterTemplateList.remove(position);
       notifyItemRemoved(position);
       dialog.cancel();
      }
     }
    });
   }
  });
  
  dialog.findViewById(R.id.button_no).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    dialog.cancel();
   }
  });
  
  
 }
 
 
 private void verifyDenyAndDoNext(RequesterTemplate template, int position)
 {
  Dialog dialog = new Dialog(context);
  dialog.setContentView(R.layout.dialog_confirm);
  dialog.show();
  dialog.setCancelable(true);
  
  Window window = dialog.getWindow();
  window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
  
  
  ((TextView) dialog.findViewById(R.id.name)).setText(template.getName());
  ((TextView) dialog.findViewById(R.id.city)).setText(template.getAddress());
  ((TextView) dialog.findViewById(R.id.title)).setText("Confirm Deny");
  
  Glide.with(context).load(template.getProfilePictureUrl()).placeholder(R.drawable.rahul_profile).error(R.drawable.elon).into((CircularImageView) dialog.findViewById(R.id.profile_picture));
  
  dialog.findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Log.d(TAG, "onClick: Deleting notification id ==> " + template.getNotificationId());
    FirebaseFirestore.getInstance().collection(Variables.NOTIFICATIONS_COLLECTION_NAME).document(template.getNotificationId()).delete();
    
    Log.d(TAG, "onClick: Dialog ==> Yes");
    Toast.makeText(context, "Request denied", Toast.LENGTH_SHORT).show();
    requesterTemplateList.remove(position);
    notifyItemRemoved(position);
    dialog.cancel();
   }
  });
  
  dialog.findViewById(R.id.button_no).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    dialog.cancel();
   }
  });
  
  
 }
 
 
 private void insertRateInFirebase(RequesterTemplate template, int rating, String review)
 {
  String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
  TemplateFirebaseReview firebaseReview = new TemplateFirebaseReview(myId, template.getUserId(), template.getItemId(), rating, review);
  FirebaseFirestore.getInstance().collection(Variables.REVIEW_COLLECTION_NAME).document().set(firebaseReview).addOnCompleteListener(new OnCompleteListener<Void>()
  {
   @Override
   public void onComplete(@NonNull Task<Void> task)
   {
    if(task.isSuccessful())
    {
     Log.d(TAG, "onComplete: Write review success ==> " + firebaseReview);
     Toast.makeText(context, "Review Added", Toast.LENGTH_SHORT).show();
     deleteItemFromPost(template.getItemId());
    }
   }
  });
 }
 
 private void deleteItemFromPost(String itemId)
 {
  FirebaseFirestore.getInstance().collection(Variables.FEEDS_COLLECTION_NAME).document(itemId).update(FeildsNames.IS_AVAILABLE, false).addOnCompleteListener(new OnCompleteListener<Void>()
  {
   @Override
   public void onComplete(@NonNull Task<Void> task)
   {
    if(task.isSuccessful())
    {
     Log.d(TAG, "onComplete: Updated Item in feeds ==> " + itemId);
    }
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
  public LinearLayout give;
  public LinearLayout deny;
  public LinearLayout layoutProfile;
  public TextView textGive;
  public TextView textDeny;
  
  public RequesterViewHolder(@NonNull View view)
  {
   super(view);
   
   profilePicture = view.findViewById(R.id.profile_picture);
   name = view.findViewById(R.id.name);
   address = view.findViewById(R.id.address);
   layoutProfile = view.findViewById(R.id.layout_profile);
   give = view.findViewById(R.id.give);
   deny = view.findViewById(R.id.deny);
   textGive = view.findViewById(R.id.text_give);
   textDeny = view.findViewById(R.id.text_deny);
  }
 }
 
 
}
