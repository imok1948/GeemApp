package com.example.geem.fragments.browse.notifications;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.activities.MainActivity;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.requests.AdapterRequestedItems;
import com.example.geem.fragments.browse.requests.FragmentRequestList;
import com.example.geem.fragments.browse.requests.RequestedItemsTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FragmentNotifications2 extends Fragment
{
 
 private static final String TAG = "FragmentNotifications2";
 
 private String myId = "";
 private View view;
 
 private final int EXPANDING_TIME_IN_MILLISECONDS = 1000;
 
 ImageView toggle1, toggle2, toggle3, toggle4;
 RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
 LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;
 AdapterRequestedItems adapterRequestedItems1, adapterRequestedItems2, adapterRequestedItems3, adapterRequestedItems4;
 
 boolean toggledStatus1, toggledStatus2, toggledStatus3, toggledStatus4;
 
 NestedScrollView nestedScrollView;
 
 int LINEAR_LAYOUT_HEIGHT = 1600;
 
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_notifications2, container, false);
  
  boolean loggedIn = true;
  if(loggedIn)
  {
   try
   {
    myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Log.d(TAG, "onCreateView: My id ==> " + myId);
   }
   catch(Exception e)
   {
    Log.d(TAG, "onCreateView: Error while getting my id" + FirebaseAuth.getInstance().getCurrentUser());
   }
   init();
   initializeComponents();
   setListeners();
   setInitialToggle();
   initFirebase();
   
   
   // setRandomTemplates(adapterRequestedItems1);
   //   setRandomTemplates(adapterRequestedItems2);
   //   setRandomTemplates(adapterRequestedItems3);
   //   setRandomTemplates(adapterRequestedItems4);
   
  }
  else
  {
   Toast.makeText(getContext(), "Please log in", Toast.LENGTH_SHORT).show();
   ((MainActivity) getActivity()).navController.navigate(R.id.nav_profile);
  }
  return view;
 }
 
 private void initFirebase()
 {
  FirebaseFirestore.getInstance().collection(FragmentNotifications.NOTIFICATIONS_COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     for(DocumentSnapshot snapshot : task.getResult())
     {
      NotificationTemplate template = snapshot.toObject(NotificationTemplate.class);
      Log.d(TAG, "onComplete: Template ==> " + template);
      if(true)
      {
       fetchImageAndSetToAdapter(template.getItemId());
      }
     }
    }
    else
    {
     Log.d(TAG, "onComplete: Error while receiving ==> " + task.getException());
    }
   }
  });
 }
 
 private void fetchImageAndSetToAdapter(String itemId)
 {
  FirebaseFirestore.getInstance().collection(Variables.FEEDS_COLLECTION_NAME).document(itemId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    if(task.isSuccessful())
    {
    
    }
   }
  });
 }
 
 
 private void initializeComponents()
 {
  recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
  recyclerView1.setAdapter(adapterRequestedItems1);
  
  recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
  recyclerView2.setAdapter(adapterRequestedItems2);
  
  recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
  recyclerView3.setAdapter(adapterRequestedItems3);
  
  recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));
  recyclerView4.setAdapter(adapterRequestedItems4);
 }
 
 
 private void init()
 {
  nestedScrollView = view.findViewById(R.id.nested_scroll_view);
  
  recyclerView1 = view.findViewById(R.id.recycler_view_1);
  recyclerView2 = view.findViewById(R.id.recycler_view_2);
  recyclerView3 = view.findViewById(R.id.recycler_view_3);
  recyclerView4 = view.findViewById(R.id.recycler_view_4);
  
  toggle1 = view.findViewById(R.id.button_1);
  toggle2 = view.findViewById(R.id.button_2);
  toggle3 = view.findViewById(R.id.button_3);
  toggle4 = view.findViewById(R.id.button_4);
  
  linearLayout1 = view.findViewById(R.id.expand_1);
  linearLayout2 = view.findViewById(R.id.expand_2);
  linearLayout3 = view.findViewById(R.id.expand_3);
  linearLayout4 = view.findViewById(R.id.expand_4);
  
  
  adapterRequestedItems1 = new AdapterRequestedItems(getContext());
  adapterRequestedItems2 = new AdapterRequestedItems(getContext());
  adapterRequestedItems3 = new AdapterRequestedItems(getContext());
  adapterRequestedItems4 = new AdapterRequestedItems(getContext());
  
 }
 
 
 private void setRandomTemplates(AdapterRequestedItems adapter)
 {
  
  Random random = new Random();
  
  for(RequestedItemsTemplate template : FragmentRequestList.getTemplates(random.nextInt(18)))
  {
   adapter.insertItems(template);
  }
  
 }
 
 private void setInitialToggle()
 {
  //Button1 opened
  toggle1.setRotation(180);
  
  toggledStatus2 = toggleView(linearLayout2, false, toggle2);
  toggledStatus3 = toggleView(linearLayout3, false, toggle3);
  toggledStatus4 = toggleView(linearLayout4, false, toggle4);
 }
 
 private void setListeners()
 {
  toggle1.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    toggledStatus1 = toggleView(linearLayout1, toggledStatus1, toggle1);
   }
  });
  
  
  toggle2.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    toggledStatus2 = toggleView(linearLayout2, toggledStatus2, toggle2);
   }
  });
  
  
  toggle3.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    toggledStatus3 = toggleView(linearLayout3, toggledStatus3, toggle3);
   }
  });
  
  
  toggle4.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    toggledStatus4 = toggleView(linearLayout4, toggledStatus4, toggle4);
   }
  });
 }
 
 
 private boolean toggleView(View view, boolean toggleStatus, View toggleButton)
 {
  if(toggleStatus)
  {
   expand(view, EXPANDING_TIME_IN_MILLISECONDS, LINEAR_LAYOUT_HEIGHT);
   toggleButton.setRotation(180);
  }
  else
  {
   collapse(view, EXPANDING_TIME_IN_MILLISECONDS, 0);
   toggleButton.setRotation(0);
  }
  return !toggleStatus;
 }
 
 
 public static void expand(final View v, int duration, int targetHeight)
 {
  int prevHeight;
  v.setVisibility(View.VISIBLE);
  ValueAnimator valueAnimator = ValueAnimator.ofInt(v.getHeight(), targetHeight);
  valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
  {
   @Override
   public void onAnimationUpdate(ValueAnimator animation)
   {
    v.getLayoutParams().height = (int) animation.getAnimatedValue();
    v.requestLayout();
   }
  });
  valueAnimator.setInterpolator(new DecelerateInterpolator());
  valueAnimator.setDuration(duration);
  valueAnimator.start();
 }
 
 
 public static void collapse(final View v, int duration, int targetHeight)
 {
  int prevHeight;
  ValueAnimator valueAnimator = ValueAnimator.ofInt(v.getHeight(), targetHeight);
  valueAnimator.setInterpolator(new DecelerateInterpolator());
  
  valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
  {
   @Override
   public void onAnimationUpdate(ValueAnimator animation)
   {
    v.getLayoutParams().height = (int) animation.getAnimatedValue();
    v.requestLayout();
   }
  });
  
  valueAnimator.setInterpolator(new DecelerateInterpolator());
  valueAnimator.setDuration(duration);
  valueAnimator.start();
 }
 
}