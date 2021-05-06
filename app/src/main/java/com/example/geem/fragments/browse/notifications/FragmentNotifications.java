package com.example.geem.fragments.browse.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentNotifications extends Fragment
{
 
 private static final String TAG = "FragmentNotifications";
 private FirebaseFirestore firebaseFirestore;
 private CollectionReference feedsCollectionReference;
 private static final String MESSAGE_COLLECTION_NAME = "messages";
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View view = inflater.inflate(R.layout.fragment_notifications, container, false);
  
  
  firebaseFirestore = FirebaseFirestore.getInstance();
  feedsCollectionReference = firebaseFirestore.collection(MESSAGE_COLLECTION_NAME);
  
  
  view.findViewById(R.id.button_upload).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    NotificationTemplate template = new NotificationTemplate("Temp", "Data");
    feedsCollectionReference.document().set(template).addOnCompleteListener(new OnCompleteListener<Void>()
    {
     @Override
     public void onComplete(@NonNull Task<Void> task)
     {
      if(task.isSuccessful())
      {
       Log.d(TAG, "onComplete: uploading success ");
      }
      else
      {
       Log.d(TAG, "onComplete: uploading failed : " + task.getException());
       
      }
     }
    });
   }
  });
 
 
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  //Recreate this class;
  
  
  view.findViewById(R.id.button_download).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    feedsCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
    {
     @Override
     public void onComplete(@NonNull Task<QuerySnapshot> task)
     {
      if(task.isSuccessful())
      {
       Log.d(TAG, "onComplete: Downloading success ");
       for(DocumentSnapshot snapshot : task.getResult())
       {
        NotificationTemplate template = snapshot.toObject(NotificationTemplate.class);
        Log.d(TAG, "onComplete: Title ==> " + template.getTitle() + " Content ==> " + template.getContentOfDataTempForCheckingCamleCase());
       }
      }
      else
      {
       Log.d(TAG, "onComplete: Downloading failed : " + task.getException());
      }
     }
    });
   }
  });
  
  return view;
 }
}

