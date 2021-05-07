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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FragmentNotifications extends Fragment
{
 
 private static final String TAG = "FragmentNotifications";
 private FirebaseFirestore firebaseFirestore;
 private CollectionReference feedsCollectionReference;
 public static final String NOTIFICATIONS_COLLECTION_NAME = "notifications";
 
 public static final String DUMMY_PROFILE_COLLECTION_NAME = "dummy_profiles_do_not_delete";
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View view = inflater.inflate(R.layout.fragment_notifications, container, false);
  
  if(((MainActivity) getActivity()).checkLoggedIn())
  {
   view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     feedsCollectionReference = FirebaseFirestore.getInstance().collection(DUMMY_PROFILE_COLLECTION_NAME);
     
     
     String[] documentIds = new String[]{
      "Mi44ruQiwrYjRmxYtQzjf8NaqV52", "ZhdDwBYZrJfdAUFsIvoV2OHcWx53", "bRBGZ3a7HySxw8hSEcgcCi4RsEw1", "f3D2aHKaOlYB6caTCzLZyXspFXj2", "ixu8JucKuOUqN5LitJWSLD0eHuR2", "prdEd8kRjHWX3IRJr8ju36Fkf3C3", "rBlJNO9OSrbZYruMd7u7B4SNVPa2"
     };
     List<DummyTemplate> dummyTemplateList = getTemplates();
     
     
     for(int i = 0; i < dummyTemplateList.size(); i++)
     {
      DummyTemplate template = dummyTemplateList.get(i);
      feedsCollectionReference.document(documentIds[i]).set(template).addOnCompleteListener(new OnCompleteListener<Void>()
      {
       @Override
       public void onComplete(@NonNull Task<Void> task)
       {
        if(task.isSuccessful())
        {
         //         Toast.makeText(getContext(), "inserted template : " + template, Toast.LENGTH_SHORT).show();
         Log.d(TAG, "onComplete: inserted template : " + template);
        }
        else
        {
         //         Toast.makeText(getContext(), "Failed to insert template : " + template, Toast.LENGTH_SHORT).show();
         Log.d(TAG, "onComplete: failed to insert template : " + template);
        }
       }
      });
     }
    }
   });
   
  }
  else
  {
   ((MainActivity) getActivity()).goToLoginFragment();
  }
  return view;
 }
 
 private List<DummyTemplate> getTemplates()
 {
  List<DummyTemplate> templateList = new ArrayList<>();
  
  templateList.add(new DummyTemplate("https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2FScreenshot%20from%202021-03-16%2011-56-22.png?alt=media&token=4118bb3d-d851-48d0-9038-6c0ad44a2d99", "Rishabh Praveen Kumar Bafna this long NAmE iS fOR ChecIng", "rishabh8798.908@gmail.com", "Pune"));
  
  templateList.add(new DummyTemplate("https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F9.jpg?alt=media&token=e8731de8-71aa-4348-95ad-e026d2035830", "Kapil Shakya", "kapil89034756526789522348938925@gmail.com", "Bhopal Vidisha Road Berasia Madhya Pradesh India 463106"));
  
  templateList.add(new DummyTemplate("https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F5.jpg?alt=media&token=00ce602a-6ab3-4522-8d01-54037db3834b", "John Wick", "john_wick008@john.com", "Washington"));
  
  templateList.add(new DummyTemplate("https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F3.jpg?alt=media&token=6d7df674-cada-4c9d-be72-11c82846d7f3", "Pappu", "pappu@gmail.com", "Bhopal"));
  
  templateList.add(new DummyTemplate("https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F2.jpg?alt=media&token=36b72bdf-f67f-482c-8f64-6b97a1381d82", "Ravi", "ravi@gmail.com", "Mumbai"));
  return templateList;
 }
 
}


