package com.example.geem.fragments.browse.add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geem.R;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;

public class FragmentAddItem2 extends Fragment implements StepperFormListener
{
 
 private View view;
 
 private TitleStep titleStep;
 private DescriptionStep descriptionStep;
 private PhotoStep photoStep;
 private AddressStep addressStep;
 private VerticalStepperFormView verticalStepperForm;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_add_item2, container, false);
  init();
  setup();
  return view;
 }
 
 private void setup()
 {
  verticalStepperForm.setup(this, titleStep, descriptionStep, photoStep, addressStep).init();
 }
 
 private void init()
 {
  verticalStepperForm = view.findViewById(R.id.stepper_form);
  titleStep = new TitleStep("Title");
  descriptionStep = new DescriptionStep("Description");
  photoStep = new PhotoStep("Photos");
  addressStep = new AddressStep("Address");
 }
 
 @Override
 public void onCompletedForm()
 {
  Toast.makeText(getContext(), "Form completed ", Toast.LENGTH_SHORT).show();
 }
 
 @Override
 public void onCancelledForm()
 {
 
 }
}