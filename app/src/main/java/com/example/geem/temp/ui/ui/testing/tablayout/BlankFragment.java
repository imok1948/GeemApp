package com.example.geem.temp.ui.ui.testing.tablayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geem.R;

public class BlankFragment extends Fragment
{
 
 private final static String TAG = "Fragment 1";
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  // Inflate the layout for this fragment
  View root = inflater.inflate(R.layout.fragment_blank, container, false);
  Toast.makeText(getContext(), TAG, Toast.LENGTH_SHORT).show();
  return root;
 }
}