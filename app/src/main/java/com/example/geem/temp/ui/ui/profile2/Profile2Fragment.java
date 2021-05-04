package com.example.geem.temp.ui.ui.profile2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geem.R;

public class Profile2Fragment extends Fragment
{
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View root = inflater.inflate(R.layout.fragment_profile2, container, false);
  
  Toast.makeText(getContext(), "Login Fragment", Toast.LENGTH_SHORT).show();
  
  return root;
 }
}