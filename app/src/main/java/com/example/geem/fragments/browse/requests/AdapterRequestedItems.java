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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.activities.MainActivity;
import com.example.geem.extra.FeildsNames;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.notifications.DummyTemplate;
import com.example.geem.fragments.browse.requests.activity.ActivityRequesters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

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
  
  if(template.getRequestType().equals(Variables.NOTIFICATION_TYPE_REQUEST))
  {
   holder.totalRequests.setText(template.getTotalRequests() + " Requests");
  }
  else
  {
   holder.totalRequests.setText("");
  }
  
  if(template.getRequestType().equals(Variables.NOTIFICATION_RATING_REQUEST))
  {
   holder.view.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     ratePerson(template, position);
    }
   });
  }
  else
  {
   holder.view.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     Intent intent = new Intent(context, ActivityRequesters.class);
     intent.putExtra(Variables.NOTIFICATION_TYPE_FOR_PASSING_ACTIVITY, template.getRequestType());
     intent.putExtra(Variables.NOTIFICATION_ID_FOR_PASSING_ACTIVITY, template.getNotificationId());
     intent.putExtra(Variables.ITEM_ID, template.getItemId());
     context.startActivity(intent);
    }
   });
  }
 }
 
 private void ratePerson(RequestedItemsTemplate template, int position)
 {
  FirebaseFirestore.getInstance().collection(Variables.PROFILE_COLLECTION_NAME).document(template.getRequesterId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    Log.d(TAG, "onComplete: Requester id===>" + template.getRequesterId());
    if(task.isSuccessful())
    {
     DummyTemplate profileTemplate = task.getResult().toObject(DummyTemplate.class);
     
     Log.d(TAG, "onComplete: Rating for ==> " + template.getRequesterId());
     
     Dialog dialog = new Dialog(context);
     dialog.setContentView(R.layout.dialog_rate);
     dialog.show();
     dialog.setCancelable(true);
     
     Window window = dialog.getWindow();
     window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
     
     Log.d(TAG, "onComplete: Profile ==> " + profileTemplate);
     ((TextView) dialog.findViewById(R.id.name)).setText(profileTemplate.getName());
     ((TextView) dialog.findViewById(R.id.city)).setText(profileTemplate.getCity());
     ((TextView) dialog.findViewById(R.id.title)).setText("Rate & Review");
     
     ((AppCompatButton) dialog.findViewById(R.id.button_yes)).setText("Rate");
     ((AppCompatButton) dialog.findViewById(R.id.button_no)).setText("Cancel");
     
     
     Glide.with(context).load(profileTemplate.getProfilePictureUrl()).placeholder(R.drawable.rahul_profile).error(R.drawable.elon).into((CircularImageView) dialog.findViewById(R.id.profile_picture));
     
     dialog.findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener()
     {
      @Override
      public void onClick(View view)
      {
       AppCompatRatingBar ratingBar = dialog.findViewById(R.id.rating_bar);
       int rating = (int) ratingBar.getRating();
       String review = ((EditText) dialog.findViewById(R.id.review)).getText() + "";
       
       String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
       TemplateFirebaseReview firebaseReview = new TemplateFirebaseReview(myId, template.getRequesterId(), template.getItemId(), rating, review);
       
       FirebaseFirestore.getInstance().collection(Variables.REVIEW_COLLECTION_NAME).document().set(firebaseReview);
       FirebaseFirestore.getInstance().collection(Variables.NOTIFICATIONS_COLLECTION_NAME).document(template.getNotificationId()).delete();
       
       templatesList.remove(position);
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
