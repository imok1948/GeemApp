package com.example.geem.temp.ui.ui.browse2.history2;

import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.temp.ui.ui.browse2.history2.history.ActivityHistoryDetailStarter;
import com.example.geem.fragments.FragmentHistoryDetail;
import com.example.geem.temp.ui.ui.browse2.history2.history.HistoryStructure;
import com.example.geem.temp.ui.ui.browse2.history2.history.Variables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FragmentHistory extends Fragment
{
 private final static String FEEDS_COLLECTION_NAME = "User_Items";
 private final int LOAD_ITEMS_AT_ONCE = 10;
 FragmentHistory fragmentHistoryInstance = null;
 HistoryAdapter historyAdapter;
 RecyclerView recyclerView;
 
 View globalView;
 
 private Button buttonUponRecyclerView;
 private FirebaseFirestore firebaseFirestore;
 private CollectionReference feedsCollectionReference;
 private DocumentSnapshot lastDocumentSnapshot = null;
 private boolean someDataRemaining = true;
 private String userName = "User 2";
 
 
 public FragmentHistory()
 {
  fragmentHistoryInstance = this;
 }
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
  Log.i("AUTH : ", firebaseAuth + "");
  if(firebaseAuth.getCurrentUser() != null)
  {
   userName = firebaseAuth.getCurrentUser().getUid();
  }
  Log.i("USER : ", userName);
  firebaseFirestore = FirebaseFirestore.getInstance();
  feedsCollectionReference = firebaseFirestore.collection(FEEDS_COLLECTION_NAME);
  
  View view = inflater.inflate(R.layout.fragment_history, container, false);
  globalView = view;
  
  recyclerView = view.findViewById(R.id.recyclerViewForHistory);
  
  HistoryAdapter historyAdapter = new HistoryAdapter(new LinkedList<>());
  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
  recyclerView.setLayoutManager(linearLayoutManager);
  recyclerView.setAdapter(historyAdapter);
  recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
  
  ProgressDialog progressDialog = new ProgressDialog(getActivity());
  progressDialog.setMessage("Loading ...");
  progressDialog.show();
  progressDialog.setCancelable(true);
  
  
  if(!netAvailable())
  {
   Log.i("FIRE : ", HistoryStructure.OWNER_USER_ID + ", " + "User : " + userName);
   feedsCollectionReference.orderBy(HistoryStructure.POST_UPLOADING_TIME).whereEqualTo(HistoryStructure.OWNER_USER_ID, userName).limit(LOAD_ITEMS_AT_ONCE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
   {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task)
    {
     progressDialog.cancel();
     if(task.isSuccessful())
     {
      List<HistoryStructure> historyStructureList = new LinkedList<>();
      
      someDataRemaining = false;
      for(DocumentSnapshot documentSnapshot : task.getResult())
      {
       HistoryStructure structure = new HistoryStructure();
       structure.setHistoryId(documentSnapshot.getId());
       
       structure.setTitle(documentSnapshot.get(HistoryStructure.POST_TITLE).toString());
       structure.setDescription(documentSnapshot.get(HistoryStructure.POST_DESCRIPTION).toString());
       structure.setAddress(documentSnapshot.get(HistoryStructure.OWNER_ADDRESS).toString());
       structure.setTimestamp((Timestamp) documentSnapshot.get(HistoryStructure.POST_UPLOADING_TIME));
       Log.i("TIME : ", documentSnapshot.get(HistoryStructure.POST_UPLOADING_TIME).toString());
       structure.setPlace(documentSnapshot.get(HistoryStructure.ITEM_AVAILABLE_PLACE).toString());
       
       structure.setType(documentSnapshot.get(HistoryStructure.POST_CATEGORY).toString());
       structure.setItemTaken((Boolean) documentSnapshot.get(HistoryStructure.ITEM_STATUS));
       
       
       //Delete these 2 lines after multiple images available
       List<String> photoURLlist = new LinkedList<>();
       photoURLlist.add(documentSnapshot.get(HistoryStructure.POST_IMAGES).toString());
       structure.setPhotoUrl(photoURLlist);
       //After multiple url available
       //structure.setPhotoUrl((List<String>) documentSnapshot.get(HistoryRowStructure.POST_IMAGES));
       
       structure.verbose();
       
       historyStructureList.add(structure);
       lastDocumentSnapshot = documentSnapshot;
       someDataRemaining = true;
      }
      historyAdapter.addData(historyStructureList);
     }
     else
     {
      Log.i("FIRE ERROR", "" + task.getException());
      Toast.makeText(getActivity(), "Error while retrieving names from server ", Toast.LENGTH_SHORT).show();
     }
     
     
    }
   });
  }
  else
  {
   if(!netAvailable())
   {
    //Snackbar.make(getView(), "Internet not available", Snackbar.LENGTH_SHORT).show();
   }
   else if(!someDataRemaining)
   {
    Snackbar.make(getView(), "No more history", Snackbar.LENGTH_SHORT).show();
   }
  }
  
  
  recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
  {
   boolean scrolledDown = true; //To checking if scrolled downor up using onScrolled (dy)
   
   @Override
   public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
   {
    super.onScrolled(recyclerView, dx, dy);
    if(dy < 0)
    {
     Log.d("SCROLL STATE", "Up");
     scrolledDown = false;
    }
    else
    {
     Log.d("SCROLL STATE", "Down");
     scrolledDown = true;
    }
   }
   
   @Override
   public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
   {
    super.onScrollStateChanged(recyclerView, newState);
    if(newState == RecyclerView.SCROLL_STATE_IDLE && scrolledDown && someDataRemaining && lastDocumentSnapshot != null) //Load new items if scrolled down and items vanished
    {
     if(!netAvailable())
     {
      ProgressDialog progressDialog = new ProgressDialog(getActivity());
      progressDialog.setMessage("Loading ...");
      progressDialog.show();
      progressDialog.setCancelable(true);
      
      
      feedsCollectionReference.orderBy(HistoryStructure.POST_UPLOADING_TIME).whereEqualTo(HistoryStructure.OWNER_USER_ID, userName).startAfter(lastDocumentSnapshot).limit(LOAD_ITEMS_AT_ONCE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
      {
       @Override
       public void onComplete(@NonNull Task<QuerySnapshot> task)
       {
        if(task.isSuccessful())
        {
         List<HistoryStructure> historyStructureList = new LinkedList<>();
         someDataRemaining = false;
         for(DocumentSnapshot documentSnapshot : task.getResult())
         {
          HistoryStructure structure = new HistoryStructure();
          structure.setHistoryId(documentSnapshot.getId());
          
          structure.setTitle(documentSnapshot.get(HistoryStructure.POST_TITLE).toString());
          structure.setDescription(documentSnapshot.get(HistoryStructure.POST_DESCRIPTION).toString());
          structure.setAddress(documentSnapshot.get(HistoryStructure.OWNER_ADDRESS).toString());
          structure.setTimestamp((Timestamp) documentSnapshot.get(HistoryStructure.POST_UPLOADING_TIME));
          structure.setPlace(documentSnapshot.get(HistoryStructure.ITEM_AVAILABLE_PLACE).toString());
          
          structure.setType(documentSnapshot.get(HistoryStructure.POST_CATEGORY).toString());
          structure.setItemTaken((Boolean) documentSnapshot.get(HistoryStructure.ITEM_STATUS));
          //Delete these 2 lines after multiple images available
          List<String> photoURLlist = new LinkedList<>();
          photoURLlist.add(documentSnapshot.get(HistoryStructure.POST_IMAGES).toString());
          structure.setPhotoUrl(photoURLlist);
          //After multiple url available
          //structure.setPhotoUrl((List<String>) documentSnapshot.get(HistoryRowStructure.POST_IMAGES));
          
          structure.verbose();
          
          
          historyStructureList.add(structure);
          lastDocumentSnapshot = documentSnapshot;
          someDataRemaining = true;
         }
         progressDialog.cancel();
         historyAdapter.addData(historyStructureList);
        }
        else
        {
         Toast.makeText(getActivity(), "Error while retrieving names from server 2", Toast.LENGTH_SHORT).show();
        }
        
       }
      });
     }
    }
    else
    {
     if(false)
     {
      Snackbar.make(getView(), "Internet not available", Snackbar.LENGTH_SHORT).show();
     }
     else if(someDataRemaining)
     {
      //Snackbar.make(getView(), "No more history", Snackbar.LENGTH_SHORT).show();
     }
     {
     
     }
    }
   }
   
  });
  return view;
 }
 
 public boolean netAvailable()
 {
  return false;
  /*
  boolean netAvailable = false;
  try
  {
   NetworkInfo networkInfo = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
   netAvailable = (networkInfo != null && networkInfo.isConnected()) ? true : false;
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
  return netAvailable;*/
 }
}


//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//