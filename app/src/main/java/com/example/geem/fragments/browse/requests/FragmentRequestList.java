package com.example.geem.fragments.browse.requests;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.notifications.FragmentNotifications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FragmentRequestList extends Fragment
{
 
 
 private static final String TAG = "FragmentRequestList";
 
 
 private RecyclerView recyclerView;
 private AdapterRequestedItems adapterRequestedItems;
 private View view;
 
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_request_list, container, false);
  init();
  return view;
 }
 
 private void init()
 {
  recyclerView = view.findViewById(R.id.recycler_view);
  adapterRequestedItems = new AdapterRequestedItems(getContext());
  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  recyclerView.setAdapter(adapterRequestedItems);
  recyclerView.setHasFixedSize(true);
  recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
  
  for(RequestedItemsTemplate template : getTemplates(10))
  {
   adapterRequestedItems.insertItems(template);
  }
 }
 
 
 static public List<RequestedItemsTemplate> getTemplates(int n)
 {
  
  String[] names = new String[]{
   "Camera 2 MP", "Sony Speaker 10 watt water proof", "Samsung Headphones white color without damaged in good condition", "Flipkart mouse"
  };
  String[] urls = new String[]{
   "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/fetch_images%2FTimestamp(seconds%3D1615975672%2C%20nanoseconds%3D117000000).jpeg?alt=media&token=ee8ba6db-aaba-4bc4-b83a-455f05202257", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/fetch_images%2FTimestamp(seconds%3D1619946101%2C%20nanoseconds%3D255000000).jpeg?alt=media&token=b07d752a-a5c1-4b87-b506-f55d0a815ef6", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/fetch_images%2FTimestamp(seconds%3D1615975966%2C%20nanoseconds%3D157000000).jpeg?alt=media&token=24c797e1-1d17-46de-b3da-55035bbf5065", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/fetch_images%2FTimestamp(seconds%3D1616067620%2C%20nanoseconds%3D519000000).jpeg?alt=media&token=cbe95fdf-9fb1-4a1d-8c69-846b8d851611"
  };
  Random random = new Random();
  List<RequestedItemsTemplate> templateList = new ArrayList<>();
  
  for(int i = 0; i < n; i++)
  {
   templateList.add(new RequestedItemsTemplate(urls[random.nextInt(urls.length)], names[random.nextInt(names.length)], random.nextInt(100), "", Variables.NOTIFICATION_TYPE_REQUEST, "", "NA"));
  }
  return templateList;
 }
}