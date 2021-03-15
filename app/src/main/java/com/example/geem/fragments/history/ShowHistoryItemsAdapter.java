package com.example.geem.fragments.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geem.R;

import java.util.LinkedList;
import java.util.List;

public class ShowHistoryItemsAdapter extends RecyclerView.Adapter<ShowHistoryItemsAdapter.HistoryItemHolder>
{
 public List<String> names;
 
 public ShowHistoryItemsAdapter()
 {
  names = new LinkedList<>();
  names.add("Rahul1");
  names.add("Rahul2");
  names.add("Rahul3");
  names.add("Rahul4");
  names.add("Rahul5");
  names.add("Rahul6");
  names.add("Rahul7");
  names.add("Rahul8");
  names.add("Rahul9");
  names.add("Rahul0");
  names.add("Rahul10");
 }
 
 
 public void addData(List<String> data)
 {
  for(int i = 0; i < data.size(); i++)
  {
   names.add(data.get(i));
  }
  notifyItemInserted(names.size() - 1);
 }
 
 @NonNull
 @Override
 public HistoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
  View view = layoutInflater.inflate(R.layout.row_history_items, parent, false);
  return new HistoryItemHolder(view);
 }
 
 
 @Override
 public void onBindViewHolder(@NonNull HistoryItemHolder holder, int position)
 {
  holder.textViewName.setText(names.get(position));
 }
 
 @Override
 public int getItemCount()
 {
  return names.size();
 }
 
 public class HistoryItemHolder extends RecyclerView.ViewHolder
 {
  TextView textViewName;
  
  public HistoryItemHolder(@NonNull View itemView)
  {
   super(itemView);
   textViewName = itemView.findViewById(R.id.textViewName);
  }
 }
}
