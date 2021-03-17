package com.example.geem.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.extra.ShivankUserItems;
import com.example.geem.extra.Variables;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FragmentFeeds extends Fragment
{
 //Recycler View Container
 RecyclerView feedsRecyclerView;
 ItemsAdapter itemsAdapter;
 FirestoreRecyclerOptions<ShivankUserItems> options;

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View view = inflater.inflate(R.layout.fragment_feeds, container, false);
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  db.collection("user_items")
          .get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
             for (QueryDocumentSnapshot document : task.getResult()) {
              Log.i(TAG, document.getId() + " => " + document.getData());
             }
            } else {
             Log.i(TAG, "Error getting documents.", task.getException());
            }
           }
          });

  feedsRecyclerView = view.findViewById(R.id.feeds_recycler_view);
  feedsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  options =
          new FirestoreRecyclerOptions.Builder<ShivankUserItems>()
                  .setQuery(FirebaseFirestore.getInstance().collection("items").orderBy("timestamp", Query.Direction.DESCENDING)
                          .limit(50), ShivankUserItems.class)
                  .build();

  updateUI(options);
  //Toast.makeText(getContext(), getArguments().getString(Variables.GREETING_KEY), Toast.LENGTH_SHORT).show();
  return view;
 }
 public void updateUI(FirestoreRecyclerOptions<ShivankUserItems> options){
  //if(itemsAdapter == null){
  itemsAdapter= new ItemsAdapter(options);
  feedsRecyclerView.setAdapter(itemsAdapter);
  itemsAdapter.startListening();
  Log.i("INFO","New Adapter Generated");

  //}

  //else{
  //itemsAdapter.notifyDataSetChanged();
  //Log.i("INFO","Old Adapter Refreshed");
  //}
 }

 @Override
 public void onResume() {
  super.onResume();
  updateUI(options);
 }

 private class ItemsAdapter extends FirestoreRecyclerAdapter<ShivankUserItems,CardViewHolder> {

  /**
   * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
   * FirestoreRecyclerOptions} for configuration options.
   *
   * @param options
   */
  public ItemsAdapter(@NonNull FirestoreRecyclerOptions<ShivankUserItems> options) {
   super(options);
  }
  @Override

  public void onError(FirebaseFirestoreException e) {
   Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
  }

  @NonNull
  @Override
  public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
   return  new CardViewHolder(layoutInflater,parent);
  }



  @Override
  protected void onBindViewHolder(@NonNull CardViewHolder holder, int position, @NonNull ShivankUserItems model) {
   holder.itemTitle.setText(model.getTitle());
   Glide.with(holder.itemImage.getContext()).load(model.getImage()).into(holder.itemImage);
  }

 }

 //View Holder Class
 private class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
  TextView itemTitle;
  ImageView itemImage;
  public CardViewHolder(LayoutInflater inflater, ViewGroup parent){
   super(inflater.inflate(R.layout.card_view_item,parent,false));
   itemView.setOnClickListener(this);
   itemTitle = itemView.findViewById(R.id.item_title);
   itemImage = itemView.findViewById(R.id.item_image);
  }


  @Override
  public void onClick(View v) {
   Toast.makeText(getActivity() , "Clicked", Toast.LENGTH_SHORT).show();
   //Intent intent = StudentDetails.newIntent(getActivity(),rollNo.getText().toString());
   //startActivity(intent);
  }

 }


}
