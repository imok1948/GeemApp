package com.example.geem.fragments.browse.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.activities.MainActivity;
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
 public static final String NOTIFICATIONS_COLLECTION_NAME = "notifications";
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View view = inflater.inflate(R.layout.fragment_notifications, container, false);
  
  if(((MainActivity) getActivity()).checkLoggedIn())
  {
  
  }
  else
  {
   ((MainActivity) getActivity()).goToLoginFragment();
  }
  return view;
 }
}