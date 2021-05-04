package com.example.geem.temp.ui.ui.browse2.messages2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geem.R;

import java.util.ArrayList;
import java.util.List;

public class BrowseMessagesFragment extends Fragment
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

