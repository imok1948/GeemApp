package com.example.geem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.geem.fragments.FragmentMessages;
import com.example.geem.fragments.TempFragment;

public class RahulStart2 extends AppCompatActivity
{
 
 
 private Button button2;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_rahul_start2);
  
  TempFragment fragmentMessages = new TempFragment();
  getSupportFragmentManager().beginTransaction().replace(R.id.layoutTemp, fragmentMessages).commit();
 }
}