package com.example.geem.fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.geem.Profile;
import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.history.ShowHistoryItemsAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FragmentHistory extends Fragment
{
 
 private FirebaseFirestore db = FirebaseFirestore.getInstance();
 private CollectionReference profileReference = db.collection("profile");
 
 RecyclerView recyclerViewHistoryItems;
 ShowHistoryItemsAdapter historyItemsAdapter;
 
 
 private ProfileAdapter profileAdapter;
 
 View gloablView;
 
 
 int counts = 10;
 
 private boolean loading = true;
 int pastVisiblesItems, visibleItemCount, totalItemCount;
 
 LinearLayoutManager linearLayoutManager;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  
  Toast.makeText(getContext(), getArguments().getString(Variables.GREETING_KEY), Toast.LENGTH_SHORT).show();
  View view = inflater.inflate(R.layout.fragment_history, container, false);
  
  
  recyclerViewHistoryItems = view.findViewById(R.id.recyclerViewHistoryItems);
  historyItemsAdapter = new ShowHistoryItemsAdapter();
  
  recyclerViewHistoryItems.setAdapter(historyItemsAdapter);
  recyclerViewHistoryItems.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
  
  linearLayoutManager = new LinearLayoutManager(getActivity());
  recyclerViewHistoryItems.setLayoutManager(linearLayoutManager);
  
  gloablView = view;
  
  
  recyclerViewHistoryItems.addOnScrollListener(new RecyclerView.OnScrollListener()
  {
   
   @Override
   public void onScrollStateChanged(RecyclerView recyclerView, int newState)
   {
    super.onScrollStateChanged(recyclerView, newState);
   }
   
   
   @Override
   public void onScrolled(RecyclerView recyclerView, int dx, int dy)
   {
    super.onScrolled(recyclerView, dx, dy);
    
    Toast.makeText(getContext(), "Scrolling dy : " + dy, Toast.LENGTH_SHORT).show();
    if(dy > 0)
    {
     //check for scroll down
     visibleItemCount = linearLayoutManager.getChildCount();
     totalItemCount = linearLayoutManager.getItemCount();
     pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
     
     if(loading)
     {
      if((visibleItemCount + pastVisiblesItems) >= totalItemCount && pastVisiblesItems >= 0)
      {
       loading = false;
       Log.v("...", "Last Item Wow !");
       // Do pagination.. i.e. fetch new data
       loadMore();
       loading = true;
      }
     }
    }
   }
  });
  
  //setupRecyclerView();
  return view;
 }
 
 private void loadMore()
 {
  List<String> l = new LinkedList<>();
  
  byte[] array = new byte[7]; // length is bounded by 7
  new Random().nextBytes(array);
  
  String generatedString = new String(array, Charset.forName("UTF-8"));
  
  for(int i = 0; i < 10; i++)
  {
   counts++;
   l.add("Rahul "+(i*counts + counts));
  }
  historyItemsAdapter.addData(l);
 }
 
 
 private void setupRecyclerView()
 {
  Query query = profileReference.orderBy("name", Query.Direction.DESCENDING);
  FirestoreRecyclerOptions<Profile> options = new FirestoreRecyclerOptions.Builder<Profile>().setQuery(query, Profile.class).build();
  
  profileAdapter = new ProfileAdapter(options);
  RecyclerView recyclerView = gloablView.findViewById(R.id.recyclerViewHistoryItems);
  
  //  recyclerView.setHasFixedSize(true);
  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  
  recyclerView.setAdapter(profileAdapter);
  
 }
}


class ProfileAdapter extends FirestoreRecyclerAdapter<Profile, ProfileAdapter.ProfileHolder>
{
 public ProfileAdapter(FirestoreRecyclerOptions<Profile> options)
 {
  super(options);
 }
 
 @Override
 protected void onBindViewHolder(ProfileAdapter.ProfileHolder profileHolder, int i, Profile profile)
 {
  profileHolder.textViewName.setText(profile.getName());
 }
 
 @NonNull
 @Override
 public ProfileAdapter.ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history_items, parent, false);
  return new ProfileAdapter.ProfileHolder(view);
 }
 
 
 class ProfileHolder extends RecyclerView.ViewHolder
 {
  TextView textViewName;
  
  public ProfileHolder(View itemView)
  {
   super(itemView);
   textViewName = itemView.findViewById(R.id.textViewName);
  }
 }
}