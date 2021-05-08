package com.example.geem.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geem.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.Step;


class TitleStep extends Step<String>
{
 
 EditText userNameView;
 
 public TitleStep(String title)
 {
  super(title);
 }
 
 @Override
 protected View createStepContentLayout()
 {
  userNameView = new EditText(getContext());
  userNameView.setSingleLine(true);
  userNameView.setHint("Title");
  
  userNameView.addTextChangedListener(new TextWatcher()
  {
   @Override
   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
   {
   
   }
   
   @Override
   public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
   {
    markAsCompletedOrUncompleted(true);
   }
   
   @Override
   public void afterTextChanged(Editable editable)
   {
   
   }
  });
  return userNameView;
 }
 
 @Override
 protected IsDataValid isStepDataValid(String stepData)
 {
  boolean isNameValid = stepData.length() >= 5;
  String errorMsg = !isNameValid ? "5 characters required" : "";
  return new IsDataValid(isNameValid, errorMsg);
 }
 
 @Override
 public String getStepData()
 {
  Editable userName = userNameView.getText();
  return userName != null ? userName.toString() : "";
 }
 
 @Override
 public String getStepDataAsHumanReadableString()
 {
  String userName = getStepData();
  return !userName.isEmpty() ? userName : "(Empty)";
 }
 
 @Override
 public void restoreStepData(String data)
 {
  userNameView.setText(data);
 }
 
 @Override
 protected void onStepOpened(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepClosed(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepMarkedAsCompleted(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepMarkedAsUncompleted(boolean animated)
 {
 
 }
}


class DescriptionStep extends Step<String>
{
 
 EditText userNameView;
 
 public DescriptionStep(String title)
 {
  super(title);
 }
 
 @Override
 protected View createStepContentLayout()
 {
  userNameView = new EditText(getContext());
  userNameView.setSingleLine(true);
  userNameView.setHint("Description");
  
  userNameView.addTextChangedListener(new TextWatcher()
  {
   @Override
   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
   {
   
   }
   
   @Override
   public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
   {
    markAsCompletedOrUncompleted(true);
   }
   
   @Override
   public void afterTextChanged(Editable editable)
   {
   
   }
  });
  return userNameView;
 }
 
 @Override
 protected IsDataValid isStepDataValid(String stepData)
 {
  boolean isNameValid = stepData.split(" ").length >= 5;
  
  // boolean isNameValid = stepData.length() >= 5;
  String errorMsg = !isNameValid ? "5 words required" : "";
  return new IsDataValid(isNameValid, errorMsg);
 }
 
 @Override
 public String getStepData()
 {
  Editable userName = userNameView.getText();
  return userName != null ? userName.toString() : "";
 }
 
 @Override
 public String getStepDataAsHumanReadableString()
 {
  String userName = getStepData();
  return !userName.isEmpty() ? userName : "(Empty)";
 }
 
 @Override
 public void restoreStepData(String data)
 {
  userNameView.setText(data);
 }
 
 @Override
 protected void onStepOpened(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepClosed(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepMarkedAsCompleted(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepMarkedAsUncompleted(boolean animated)
 {
 
 }
}


class AddressStep extends Step<String>
{
 
 EditText userNameView;
 
 public AddressStep(String title)
 {
  super(title);
 }
 
 @Override
 protected View createStepContentLayout()
 {
  userNameView = new EditText(getContext());
  userNameView.setSingleLine(true);
  userNameView.setHint("Address");
  
  userNameView.addTextChangedListener(new TextWatcher()
  {
   @Override
   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
   {
   
   }
   
   @Override
   public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
   {
    markAsCompletedOrUncompleted(true);
   }
   
   @Override
   public void afterTextChanged(Editable editable)
   {
   
   }
  });
  return userNameView;
 }
 
 @Override
 protected IsDataValid isStepDataValid(String stepData)
 {
  boolean isNameValid = stepData.split(" ").length >= 8;
  
  // boolean isNameValid = stepData.length() >= 5;
  String errorMsg = !isNameValid ? "8 words required" : "";
  return new IsDataValid(isNameValid, errorMsg);
 }
 
 @Override
 public String getStepData()
 {
  Editable userName = userNameView.getText();
  return userName != null ? userName.toString() : "";
 }
 
 @Override
 public String getStepDataAsHumanReadableString()
 {
  String userName = getStepData();
  return !userName.isEmpty() ? userName : "(Empty)";
 }
 
 @Override
 public void restoreStepData(String data)
 {
  userNameView.setText(data);
 }
 
 @Override
 protected void onStepOpened(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepClosed(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepMarkedAsCompleted(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepMarkedAsUncompleted(boolean animated)
 {
 
 }
}


class PhotoStep extends Step<String>
{
 
 EditText userNameView;
 
 ImageView imageView;
 RecyclerView recyclerViewList;
 HorizontalImageShow recyclerAdapter;
 
 public PhotoStep(String title)
 {
  super(title);
 }
 
 
 public void addImageUri(Uri uri)
 {
  recyclerAdapter.addImage(uri);
 }
 
 @Override
 protected View createStepContentLayout()
 {
  
  recyclerViewList = new RecyclerView(getContext());
  recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
  
  List<Uri> imageUriList = new LinkedList<>();
  
  Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.ic_tab_additem);
  
  imageUriList.add(uri);
  
  recyclerAdapter = new HorizontalImageShow(getContext(), imageUriList);
  recyclerViewList.setAdapter(recyclerAdapter);
  
  userNameView = new EditText(getContext());
  userNameView.setText("Select Photos");
  imageView = new ImageView(getContext());
  imageView.setImageResource(R.drawable.rahul_profile);
  
  LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400, 400);
  imageView.setLayoutParams(layoutParams);
  return recyclerViewList;
 }
 
 
 @Override
 protected IsDataValid isStepDataValid(String stepData)
 {
  boolean isNameValid = stepData.length() >= 3;
  String errorMsg = !isNameValid ? "Only 2 photos can be included" : "";
  return new IsDataValid(true, errorMsg);
 }
 
 @Override
 public String getStepData()
 {
  Editable userName = userNameView.getText();
  return userName != null ? userName.toString() : "";
 }
 
 @Override
 public String getStepDataAsHumanReadableString()
 {
  String userName = getStepData();
  return !userName.isEmpty() ? userName : "(Empty)";
 }
 
 @Override
 public void restoreStepData(String data)
 {
  userNameView.setText(data);
 }
 
 @Override
 protected void onStepOpened(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepClosed(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepMarkedAsCompleted(boolean animated)
 {
 
 }
 
 @Override
 protected void onStepMarkedAsUncompleted(boolean animated)
 {
 
 }
}


class HorizontalImageShow extends RecyclerView.Adapter<HorizontalImageShow.HorizontalImageHolder>
{
 Context context;
 List<Uri> imageUriList;
 private View myView;
 
 public void toast(String msg)
 {
  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
 }
 
 public HorizontalImageShow(Context context, List<Uri> imageUriList)
 {
  this.context = context;
  this.imageUriList = imageUriList;
 }
 
 
 @NonNull
 @Override
 public HorizontalImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  LayoutInflater inflater = LayoutInflater.from(parent.getContext());
  View view = inflater.inflate(R.layout.stepper_row_show_images, parent, false);
  myView = view;
  return new HorizontalImageHolder(view);
 }
 
 @Override
 public void onBindViewHolder(@NonNull HorizontalImageHolder holder, int position)
 {
  Picasso.get().load(imageUriList.get(position)).into(holder.imageView);
  
  if(imageUriList.get(position).equals(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.ic_tab_additem)))
  {
   holder.imageView.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     if(imageUriList.size() <= 3)
     {
      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      ((Activity) context).startActivityForResult(intent, 102);
     }
     else
     {
      Snackbar.make(view, "3 Images allowed", Snackbar.LENGTH_SHORT).show();
     }
    }
   });
  }
  
  
  else
  {
   holder.imageView.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     toast("Removing...");
     imageUriList.remove(position);
     notifyDataSetChanged();
    }
   });
  }
 }
 
 public void addImage(Uri uri)
 {
  imageUriList.add(uri);
  notifyDataSetChanged();
 }
 
 @Override
 public int getItemCount()
 {
  return imageUriList.size();
 }
 
 public class HorizontalImageHolder extends RecyclerView.ViewHolder
 {
  ImageView imageView;
  
  public HorizontalImageHolder(@NonNull View itemView)
  {
   super(itemView);
   this.imageView = itemView.findViewById(R.id.stepper_view_image);
  }
 }
}
