package com.example.geem.fragments.browse.add;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geem.R;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;

public class VerticalStepperTestActivity extends AppCompatActivity implements StepperFormListener//AppCompatActivity
{
 TitleStep titleStep;
 DescriptionStep descriptionStep;
 PhotoStep photoStep;
 AddressStep addressStep;
 
 private VerticalStepperFormView verticalStepperForm;
 
 @Override
 protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
 {
  super.onActivityResult(requestCode, resultCode, data);
  if(requestCode == 102 && resultCode == RESULT_OK && data != null)
  {
   Uri imageUri = data.getData();
   Toast.makeText(getApplicationContext(), "URI : " + imageUri, Toast.LENGTH_SHORT).show();
   photoStep.addImageUri(imageUri);
  }
 }
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_vertical_form_stepper);
 
  titleStep = new TitleStep("Title");
  descriptionStep = new DescriptionStep("Description");
  photoStep = new PhotoStep("Photos");
  addressStep = new AddressStep("Address");
  
  verticalStepperForm = findViewById(R.id.stepper_form);
  verticalStepperForm.setup(this, titleStep, descriptionStep, photoStep, addressStep).init();
 }
 
 @Override
 public void onCompletedForm()
 {
  Toast.makeText(getApplicationContext(), "Successfully added new item", Toast.LENGTH_SHORT).show();
 }
 
 @Override
 public void onCancelledForm()
 {
  // This method will be called when the user clicks on the cancel button of the form.
 }
}