package com.example.geem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geem.extra.Variables;
import com.example.geem.fragments.FragmentHistory;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.HashMap;
import java.util.Map;

public class RahulStartActivity extends AppCompatActivity
{
 
 FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
 
 private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
 DocumentReference documentReference = firebaseFirestore.document("/");
 
 private static final String AUTHER_NAME = "auther_name";
 private static final String QUOTE = "quote";
 private static final String TAG = "RahulStartActivity";
 
 
 private FirebaseFirestore db = FirebaseFirestore.getInstance();
 private CollectionReference profileReference = db.collection("profile");
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_rahul_start);
  firebaseFirestore.setFirestoreSettings(settings);
  
  FragmentHistory fragmentHistory = new FragmentHistory();
  Bundle argumentsBundle = new Bundle();
  argumentsBundle.putString(Variables.GREETING_KEY, "Calling from main activity");
  fragmentHistory.setArguments(argumentsBundle);
  getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragmentHistory).commit();
  
 }
 
 @Override
 protected void onStart()
 {
  super.onStart();
  //  profileAdapter.startListening();
 }
 
 
 @Override
 protected void onStop()
 {
  super.onStop();
  // profileAdapter.stopListening();
 }
 
 
 public void getData(View view)
 {
  CollectionReference historyRef = documentReference.collection("history");
  Query query = historyRef.whereEqualTo("user", "rahul20117");
  
  historyRef.whereEqualTo("user", "rahul20117").whereEqualTo("photo", "photoUrl2").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     for(QueryDocumentSnapshot documentSnapshot : task.getResult())
     {
      Log.d(TAG, documentSnapshot.getId() + " ==> " + documentSnapshot.getData());
     }
    }
    else
    {
     Log.d(TAG, "Error :", task.getException());
    }
   }
  });
 }
 
 
 public void getAndTestData(View view)
 {
  CollectionReference historyCollectionReference = firebaseFirestore.collection("history"); //documentReference.collection("history");
  
  EditText editTextUserName, editTextPlace, editTextTakerName;
  editTextUserName = findViewById(R.id.editTextUserName);
  editTextPlace = findViewById(R.id.editTextPlace);
  editTextTakerName = findViewById(R.id.editTextTakerName);
  Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
  
  String historyId, userName, categoryType, place, takerName, timestamp;
  
  historyId = String.valueOf(System.currentTimeMillis());
  userName = editTextUserName.getText().toString();
  categoryType = spinnerCategory.getSelectedItem().toString();
  place = editTextPlace.getText().toString();
  takerName = editTextTakerName.getText().toString();
  
  
  historyCollectionReference.whereEqualTo(History.CATEGORY_NAME, categoryType).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     
     for(DocumentSnapshot documentSnapshot : task.getResult())
     {
      History historyObject = new History(documentSnapshot);
      
      Toast.makeText(getApplicationContext(), historyObject.getStringObjectMap().toString(), Toast.LENGTH_SHORT).show();
      
      //Toast.makeText(getApplicationContext(), "Task success", Toast.LENGTH_SHORT).show();
      Log.d(TAG, documentSnapshot.getId() + " ==> " + documentSnapshot.getData());
     }
    }
    else
    {
     Log.d(TAG, "Error while retrieving data");
    }
   }
  });
  
 }
 
 public void saveData(View view)
 {
  
  CollectionReference historyCollectionReference = firebaseFirestore.collection("profile"); //documentReference.collection("history");
  DocumentReference historyDocumentReference = historyCollectionReference.document();
  
  EditText editTextUserName, editTextPlace, editTextTakerName;
  editTextUserName = findViewById(R.id.editTextUserName);
  editTextPlace = findViewById(R.id.editTextPlace);
  editTextTakerName = findViewById(R.id.editTextTakerName);
  Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
  
  String historyId, userName, categoryType, place, takerName, timestamp;
  
  historyId = String.valueOf(System.currentTimeMillis());
  userName = editTextUserName.getText().toString();
  categoryType = spinnerCategory.getSelectedItem().toString();
  place = editTextPlace.getText().toString();
  takerName = editTextTakerName.getText().toString();
  Timestamp timestamp1 = Timestamp.now();
  
  Log.d(TAG, "Before uploading : " + historyId + " : " + userName + " : " + categoryType + " : " + place + " : " + takerName);
  
  
  Map<String, Object> dataMap = new HashMap<>();
  dataMap.put("name", userName);
  
  historyDocumentReference.set(dataMap).addOnCompleteListener(new OnCompleteListener<Void>()
  {
   @Override
   public void onComplete(@NonNull Task<Void> task)
   {
    if(task.isSuccessful())
    {
     Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
    }
    else
    {
     Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
    }
   }
  });
  
 /* History history = new History(historyId, userName, categoryType, takerName, place, timestamp1);
  
  Map<String, Object> historyObjectMap = history.getStringObjectMap();
  
  
  historyDocumentReference.set(historyObjectMap).addOnSuccessListener(new OnSuccessListener<Void>()
  {
   @Override
   public void onSuccess(Void aVoid)
   {
    Toast.makeText(getApplicationContext(), "Successfully uploaded data : " + historyDocumentReference.getId(), Toast.LENGTH_SHORT).show();
   }
  }).addOnFailureListener(new OnFailureListener()
  {
   @Override
   public void onFailure(@NonNull Exception e)
   {
    Toast.makeText(getApplicationContext(), "Error uploaded data : " + e.getMessage(), Toast.LENGTH_SHORT).show();
   }
  });
  */
  
  /*
 
  String quote = "";//editTextQuote.getText().toString();
  String autherName = "";//editTextName.getText().toString();
  
  if(quote.isEmpty() || autherName.isEmpty())
  {
   return;
  }
  
  Map<String, Object> dataToSave = new HashMap<String, Object>();
  dataToSave.put(QUOTE, quote);
  dataToSave.put(AUTHER_NAME, autherName);
  
  documentReference.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>()
  {
   @Override
   public void onSuccess(Void aVoid)
   {
    Toast.makeText(getApplicationContext(), "Added to firebase", Toast.LENGTH_SHORT).show();
   }
  }).addOnFailureListener(new OnFailureListener()
  {
   @Override
   public void onFailure(@NonNull Exception e)
   {
    Toast.makeText(getApplicationContext(), "Failed to add to firebase", Toast.LENGTH_SHORT).show();
   }
  });
 }
 
 */
 }
 
 public void uploadImage(View view)
 {
  Intent chooseImageIntent = new Intent();
  chooseImageIntent.setType("image/*");
  chooseImageIntent.setAction(Intent.ACTION_GET_CONTENT);
  startActivityForResult(chooseImageIntent, 1);
 }
 
 @Override
 protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
 {
  super.onActivityResult(requestCode, resultCode, data);
  
  if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
  {
   String fileName = "user1_photo1.jpg";
   Uri imageUri = data.getData();
   
   
   StorageReference storageReference = FirebaseStorage.getInstance().getReference("prifle/rishabh.jpg");
   
   storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
   {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
    {
     Toast.makeText(getApplicationContext(), "Image successfully uploaded : " + storageReference.getDownloadUrl(), Toast.LENGTH_SHORT).show();
     Log.d(TAG, "URL ==> " + storageReference.getDownloadUrl());
    }
   }).addOnFailureListener(new OnFailureListener()
   {
    @Override
    public void onFailure(@NonNull Exception e)
    {
     Toast.makeText(getApplicationContext(), "Image uploading failed", Toast.LENGTH_SHORT).show();
    }
   });
   
   
  }
 }
}

class History
{
 private String username;
 private String category;
 private String takerName;
 private String place;
 private String historyId;
 private Timestamp timestamp;
 
 
 public static final String HISTORY_ID = "history_id";
 public static final String USER_NAME = "user_name";
 public static final String CATEGORY_NAME = "catergory_name";
 public static final String TAKER_NAME = "taker_name";
 public static final String PLACE = "place";
 public static final String TIMESTAMP = "time";
 
 
 public History()
 {
 
 }
 
 public History(DocumentSnapshot documentSnapshot)
 {
  historyId = documentSnapshot.get(HISTORY_ID).toString();
  username = documentSnapshot.get(USER_NAME).toString();
  category = documentSnapshot.get(CATEGORY_NAME).toString();
  takerName = documentSnapshot.get(TAKER_NAME).toString();
  place = documentSnapshot.get(PLACE).toString();
  timestamp = (Timestamp) documentSnapshot.get(TIMESTAMP);
 }
 
 public History(String historyId, String username, String category, String takerName, String place, Timestamp timestamp)
 {
  this.historyId = historyId;
  this.username = username;
  this.category = category;
  this.takerName = takerName;
  this.place = place;
  this.timestamp = timestamp;
 }
 
 public Map<String, Object> getStringObjectMap()
 {
  
  Map<String, Object> stringObjectMap = new HashMap<>();
  
  stringObjectMap.put(HISTORY_ID, historyId);
  stringObjectMap.put(USER_NAME, username);
  stringObjectMap.put(CATEGORY_NAME, category);
  stringObjectMap.put(TAKER_NAME, takerName);
  stringObjectMap.put(PLACE, place);
  stringObjectMap.put(TIMESTAMP, timestamp);
  
  return stringObjectMap;
 }
}

