package com.example.geem.fragments.browse.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geem.R;

public class FragmentNotifications extends Fragment
{
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View view = inflater.inflate(R.layout.fragment_notifications, container, false);
  return view;
 }
}