package com.example.geem.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.RahulStart2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

public class TempFragment extends Fragment
{
 
 RecyclerView recyclerView;
 RecyclerNames recyclerNames;
 View globalView;
 RahulStart2 rahulStart2;
 
 
 private FirebaseFirestore firebaseFirestore;
 private CollectionReference profileCollectionReference;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View view = inflater.inflate(R.layout.fragment_temp, container, false);
  globalView = view;
  
  
  List<String> list = new LinkedList<>();
  
  firebaseFirestore = FirebaseFirestore.getInstance();
  profileCollectionReference = firebaseFirestore.collection("profile");
  
  profileCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     for(DocumentSnapshot documentSnapshot : task.getResult())
     {
      list.add((String) documentSnapshot.get("name"));
      Toast.makeText(getActivity(), (String) documentSnapshot.get("name"), Toast.LENGTH_SHORT).show();
      
      recyclerView = view.findViewById(R.id.recyclerViewNames);
      recyclerNames = new RecyclerNames(list);
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      recyclerView.setAdapter(recyclerNames);
      recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
     }
    }
    else
    {
     Toast.makeText(getActivity(), "Error while retrieving names from server ", Toast.LENGTH_SHORT).show();
    }
   }
  });
  
  
  return view;
 }
}


class RecyclerNames extends RecyclerView.Adapter<RecyclerNames.NamesHolder>
{
 List<String> names;
 
 DocumentSnapshot lastSnapshot = null;
 
 public RecyclerNames(List<String> namesList)
 {
  names = new LinkedList<>();
  for(String s : namesList)
  {
   names.add(s);
  }
 }
 
 @NonNull
 @Override
 public NamesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
  View view = layoutInflater.inflate(R.layout.row, parent, false);
  return new NamesHolder(view);
 }
 
 @Override
 public void onBindViewHolder(@NonNull NamesHolder holder, int position)
 {
  
  holder.textViewName.setText(names.size() + " : " + names.get(position));
 }
 
 @Override
 public int getItemCount()
 {
  return names.size();
 }
 
 class NamesHolder extends RecyclerView.ViewHolder
 {
  TextView textViewName;
  
  public NamesHolder(@NonNull View itemView)
  {
   super(itemView);
   textViewName = itemView.findViewById(R.id.textViewNameRow);
  }
 }
}