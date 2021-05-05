package com.example.geem.fragments.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.geem.R;

public class FragmentAbout extends Fragment
{
 
 
 public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View root = inflater.inflate(R.layout.fragment_about, container, false);
  Toast.makeText(getContext(), "About Fragment", Toast.LENGTH_SHORT).show();
  return root;
 }
}