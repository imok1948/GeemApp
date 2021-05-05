package com.example.geem.fragments.browse.messages;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geem.R;

public class FragmentBrowseMessages extends Fragment
{
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View root = inflater.inflate(R.layout.fragment_browse_messages, container, false);
  
  
  return root;
 }
}


class People
{
 public int image;
 public Drawable imageDrw;
 public String name;
 public String email;
 public boolean section = false;
 
 public People()
 {
 }
 
 public People(String name, boolean section)
 {
  this.name = name;
  this.section = section;
 }
}

