package com.example.geem.fragments.history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.fragments.FragmentHistoryDetail;

public class ActivityHistoryDetailStarter extends AppCompatActivity
{
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_history_detail_starter);
  
  Toast.makeText(this, "Hist : " + getIntent().getExtras().getString(Variables.HISTORY_KEY_TO_DETAIL_FRAGMENT), Toast.LENGTH_SHORT).show();
  //Toast.makeText(this, "Hist : " + getIntent().getStringExtra(Variables.HISTORY_KEY_TO_DETAIL_FRAGMENT), Toast.LENGTH_SHORT).show();
  
  FragmentHistoryDetail fragmentHistoryDetail = new FragmentHistoryDetail();
  Bundle bundle = new Bundle();
  bundle.putString(Variables.HISTORY_KEY_TO_DETAIL_FRAGMENT, getIntent().getExtras().getString(Variables.HISTORY_KEY_TO_DETAIL_FRAGMENT));
  fragmentHistoryDetail.setArguments(bundle);
  getSupportFragmentManager().beginTransaction().replace(R.id.activity_history_detail_starter_container, fragmentHistoryDetail).commit();
 }
}