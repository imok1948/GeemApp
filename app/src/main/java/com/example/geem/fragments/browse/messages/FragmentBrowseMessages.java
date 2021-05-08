package com.example.geem.fragments.browse.messages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geem.R;
import com.example.geem.activities.MainActivity;
import com.example.geem.extra.TimeDetails;
import com.example.geem.fragments.browse.messages.activity.MessageTemplate;
import com.example.geem.fragments.browse.notifications.DummyTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FragmentBrowseMessages extends Fragment
{
 
 private static final String TAG = "FragmentBrowseMessages";
 private View root;
 
 //Ids
 private static String MY_ID = "rahul";
 private static String OTHER_ID = "shiwank";
 
 
 //View things
 private RecyclerView recyclerView;
 private AdapterListChatPeople adapterListChatPeople;
 
 
 //Firebase things
 private FirebaseFirestore firebaseFirestore;
 private CollectionReference messagesCollectionReference;
 private static final String MESSAGE_COLLECTION_NAME = "messages";
 private static final String PROFILE_COLLECTION_NAME = "dummy_profiles_do_not_delete";
 
 //Adapter things
 List<ChatPeople> peopleList = new ArrayList<>();
 
 //For storing unique msgs
 HashMap<String, Long> hashMap = new HashMap<>();
 
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  root = inflater.inflate(R.layout.fragment_browse_messages, container, false);
  
  if(((MainActivity) getActivity()).checkLoggedIn())
  {
   MY_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
   initializeComponents();
   initFirebase();
  }
  else
  {
   ((MainActivity) getActivity()).goToLoginFragment();
  }
  return root;
 }
 
 
 private void initFirebase()
 {
  
  messagesCollectionReference.orderBy(VariablesForFirebase.TIMESTAMP, Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     for(DocumentSnapshot snapshot : task.getResult())
     {
      MessageTemplate template = snapshot.toObject(MessageTemplate.class);
      
      boolean sentByMe = template.getMyId().equals(MY_ID);
      boolean sentToMe = template.getOtherId().equals(MY_ID);
      
      boolean uniqueChat = false;
      if(sentByMe && !hashMap.containsKey(template.getOtherId()))
      {
       uniqueChat = true;
       hashMap.put(template.getOtherId(), template.getTimestamp());
      }
      
      if(sentToMe && !hashMap.containsKey(template.getMyId()))
      {
       uniqueChat = true;
       hashMap.put(template.getMyId(), template.getTimestamp());
      }
      
      if((sentByMe || sentToMe) && uniqueChat)
      {
       Log.d(TAG, "onComplete: Fetched Message : " + template);
       getProfileDetailsAndAddToAdapter(template);
      }
     }
    }
    else
    {
     Log.d(TAG, "onComplete: fetched from firestore ==> Failed");
    }
   }
  });
 }
 
 private void getProfileDetailsAndAddToAdapter(MessageTemplate template)
 {
  String otherId = "";
  if(template.getMyId().equals(MY_ID))
  {
   otherId = template.getOtherId();
  }
  else
  {
   otherId = template.getMyId();
  }
  
  FirebaseFirestore.getInstance().collection(PROFILE_COLLECTION_NAME).document(otherId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    //This thing can be replaced with better one, but due to lack of time and it's time for dinner, I am leaving with only this version :|
    String otherId = "";
    if(template.getMyId().equals(MY_ID))
    {
     otherId = template.getOtherId();
    }
    else
    {
     otherId = template.getMyId();
    }
    DummyTemplate profileTemplate = task.getResult().toObject(DummyTemplate.class);
    adapterListChatPeople.addItem(new ChatPeople(profileTemplate.getProfilePictureUrl(), profileTemplate.getName(), template.getContent(), new TimeDetails(template.getTimestamp()), otherId));
   }
  });
  
 }
 
 private void initializeComponents()
 {
  firebaseFirestore = FirebaseFirestore.getInstance();
  messagesCollectionReference = firebaseFirestore.collection(MESSAGE_COLLECTION_NAME);
  
  recyclerView = root.findViewById(R.id.recyclerViewForMessagesList);
  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  //recyclerView.setHasFixedSize(true);
  
  adapterListChatPeople = new AdapterListChatPeople(getActivity());
  recyclerView.setAdapter(adapterListChatPeople);
  
  /*for(ChatPeople people : getRandomChats())
  {
   adapterListChatPeople.addItem(people);
  }
  */
 }
}


