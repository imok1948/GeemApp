package com.example.geem.fragments.address;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geem.R;
import com.example.geem.activities.MainActivity;

public class FragmentAddress extends Fragment
{
 private View view;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_address, container, false);
  
  if(((MainActivity) getActivity()).checkLoggedIn())
  {
  
  }
  else
  {
   ((MainActivity) getActivity()).goToLoginFragment();
  }
  return view;
 }
}