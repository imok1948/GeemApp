package com.example.geem.temp.ui.ui.about2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.geem.R;

public class About2Fragment extends Fragment
{
 
 
 public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View root = inflater.inflate(R.layout.fragment_about, container, false);
  Toast.makeText(getContext(), "About Fragment", Toast.LENGTH_SHORT).show();
  return root;
 }
}