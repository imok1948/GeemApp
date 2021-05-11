package com.example.geem;


import android.content.Intent;
import android.os.Bundle;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;
import com.example.geem.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Onboarding extends OnboarderActivity
{
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  
  OnboarderPage p1 = new OnboarderPage("\nGiveaway Items", "\nA place to Giveaway items you no longer Need", R.drawable.slide1);
  OnboarderPage p2 = new OnboarderPage("\nBrowse Categories", "\nBrowse items of various categories for Reuse", R.drawable.slide2);
  OnboarderPage p3 = new OnboarderPage("\nThanks and Ratings", "\nThanks on item GiveAway and User Ratings", R.drawable.slide3);
  OnboarderPage p4 = new OnboarderPage("\nMinimalist Lifestyle", "\nJourney of a Minimalist Lifestyle starts Here", R.drawable.slide4);
  
  
  List<OnboarderPage> pages = new ArrayList<>();
  
  pages.add(p1);
  pages.add(p2);
  pages.add(p3);
  pages.add(p4);
  for(OnboarderPage page : pages)
  {
   page.setTitleColor(R.color.black);
   page.setDescriptionColor(R.color.teal_700);
   page.setBackgroundColor(R.color.white);
   page.setTitleTextSize(30);
   page.setDescriptionTextSize(16);
  }
  
  shouldUseFloatingActionButton(true);
  setActiveIndicatorColor(R.color.teal_200);
  shouldDarkenButtonsLayout(true);
  setDividerHeight(3);
  setOnboardPagesReady(pages);
 }
 
 
 @Override
 public void onFinishButtonPressed()
 {
  Intent i = new Intent(getApplicationContext(), MainActivity.class);
  startActivity(i);
  finish();
 }
}