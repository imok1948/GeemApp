package com.example.geem.fragments.browse.requests;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geem.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentRequester extends Fragment
{
 
 private View view;
 private RecyclerView recyclerView;
 private AdapterRequester adapterRequester;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_requester, container, false);
  
  init();
  return view;
 }
 
 private void init()
 {
  adapterRequester = new AdapterRequester(getContext());
  recyclerView = view.findViewById(R.id.recycler_view);
  recyclerView.setAdapter(adapterRequester);
  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
 }
 
}