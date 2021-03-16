package com.example.geem.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class FragmentAddItem extends Fragment
{

 public static final int CAM_REQUEST_CODE = 111;
 public static final int CAM_INTENT_REQUEST_CODE = 112;
 ImageView imgView;  //this imageView is for displaying the captured image
 Button captureImgBtn, saveBtn;  //this button enables user to use camera to capture image
 Spinner spinnerMenu;
 String category, description, title;

 FirebaseFirestore db;


 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  //Toast.makeText(getContext(), getArguments().getString(Variables.GREETING_KEY), Toast.LENGTH_SHORT).show();
  View view = inflater.inflate(R.layout.fragment_add_item, container, false);
  imgView = view.findViewById(R.id.imageView);
  captureImgBtn = view.findViewById(R.id.capture_image);
  saveBtn =  view.findViewById(R.id.save_btn);

  spinnerMenu = view.findViewById(R.id.spinner);
     ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.spinner_items,android.R.layout.simple_spinner_item);
     mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     spinnerMenu.setAdapter(mAdapter);



  captureImgBtn.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
      //ask user for camera permission at runtime
     if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
      ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},CAM_REQUEST_CODE);
     }else{
      cameraLaunch();
     }
   }
  });



  saveBtn.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {

    uploadImage();

    String itemTitle, itemDescription, itemCategory, donorPlace;
    Boolean isAvailable = true;
    // Compute the GeoHash for a lat/lng point
    double lat = 51.5074;
    double lng = 0.1278;
    String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(lat, lng));

// Add the hash and the lat/lng to the document. We will use the hash

    Map<String, Object> updates = new HashMap<>();
    updates.put("geohash", hash);
    updates.put("lat", lat);
    updates.put("lng", lng);

    DocumentReference londonRef = db.collection("cities").document("LON");
    londonRef.update(updates)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful())
              {
               Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
              }
              else
              {
               Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
              }
             }
            });



/*
    userName = editTextUserName.getText().toString();
    categoryType = spinnerCategory.getSelectedItem().toString();
    place = editTextPlace.getText().toString();
    takerName = editTextTakerName.getText().toString();
    Timestamp timestamp = Timestamp.now();
*/


    //Map<String, Object> dataMap = new HashMap<>();
   // dataMap.put("name", userName);


   }


  });

  return view;
 }


 // this method uploads the captured image to Firebase storage
 private void uploadImage() {

 }


 // this method launches the implicit intent to start the default camera app
 private void cameraLaunch() {
  Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  startActivityForResult(cam,CAM_INTENT_REQUEST_CODE);
 }


 @Override
 public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
  if(requestCode == CAM_INTENT_REQUEST_CODE){
   Bitmap img = (Bitmap) data.getExtras().get("data");
   imgView.setImageBitmap(img);
  }
 }



 //----------------------------------------------

 String currentPhotoPath;

 private File createImageFile() throws IOException {
  // Create an image file name
  File dirname = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
  File img = File.createTempFile("User_image", ".jpg", dirname );
  // Save a file: path for use with ACTION_VIEW intents
  currentPhotoPath = img.getAbsolutePath();
  return img;
 }




 private void dispatchTakePictureIntent() {
  Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  // Ensure that there's a camera activity to handle the intent
  if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
   // Create the File where the photo should go
   File photoFile = null;
   try {
    photoFile = createImageFile();
   } catch (IOException ex) {
    // Error occurred while creating the File

   }
   // Continue only if the File was successfully created
   if (photoFile != null) {
    Uri photoURI = FileProvider.getUriForFile(getActivity(),
            "com.example.android.fileprovider",
            photoFile);
    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
    startActivityForResult(takePictureIntent, CAM_INTENT_REQUEST_CODE);
   }
  }
 }




 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
   if(requestCode == CAM_REQUEST_CODE){
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length>0){
         cameraLaunch();
        }
        else{
         Toast.makeText(getActivity(),"Please grant the Camera permission to use this feature",Toast.LENGTH_SHORT).show();
        }
   }

 }

}
