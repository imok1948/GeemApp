package com.example.geem.fragments.browse.history.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geem.R;
import com.example.geem.extra.FeildsNames;
import com.example.geem.extra.ShivankUserItems;
import com.example.geem.extra.Variables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentBrowseHistory2 extends Fragment
{
 
 private static final String TAG = "FragmentBrowseHistory2";
 private View view;
 
 
 private RecyclerView recyclerView;
 private AdapterBrowseHistory adapterBrowseHistory;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_browse_history2, container, false);
  init();
  setup();
  fetchItems();
  return view;
 }
 
 private void fetchItems()
 {
  FirebaseFirestore.getInstance().collection(Variables.FEEDS_COLLECTION_NAME).orderBy(FeildsNames.TIME_STAMP, Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     for(DocumentSnapshot snapshot : task.getResult())
     {
      ShivankUserItems item = snapshot.toObject(ShivankUserItems.class);
      
      if(true)
      {
       TemplateBrowseHistory template = new TemplateBrowseHistory(item.getTitle(), item.getDescription(), snapshot.getId(), item.getImage(), item.getTimestamp().getTime());
       adapterBrowseHistory.addItem(template);
      }
     }
    }
   }
  });
 }
 
 private void setup()
 {
  adapterBrowseHistory = new AdapterBrowseHistory(getContext());
  recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
  recyclerView.setAdapter(adapterBrowseHistory);
 }
 
 private void init()
 {
  recyclerView = view.findViewById(R.id.recycler_view);
 }
}