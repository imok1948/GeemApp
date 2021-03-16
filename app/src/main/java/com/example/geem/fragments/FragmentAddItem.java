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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class FragmentAddItem extends Fragment
{
 String currentPhotoPath;
 public static final int CAM_REQUEST_CODE = 111;
 public static final int CAM_INTENT_REQUEST_CODE = 112;
 ImageView imgView;  //this imageView is for displaying the captured image
 Button captureImgBtn, saveBtn;  //this button enables user to use camera to capture image
 Spinner spinnerMenu;
 EditText mTitle, mDescription;
 Uri imageUri;


 private FirebaseFirestore firebaseFireStore;
 private StorageReference storageRef;
 //String user_id;
 //private FirebaseAuth firebaseAuth;



 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  //firebaseAuth = FirebaseAuth.getInstance();
  //user_id = firebaseAuth.getCurrentUser().getUid();
  firebaseFireStore = FirebaseFirestore.getInstance();
  storageRef = FirebaseStorage.getInstance().getReference();


  //Toast.makeText(getContext(), getArguments().getString(Variables.GREETING_KEY), Toast.LENGTH_SHORT).show();
  View view = inflater.inflate(R.layout.fragment_add_item, container, false);
  imgView = view.findViewById(R.id.imageView);
  captureImgBtn = view.findViewById(R.id.capture_image);
  saveBtn =  view.findViewById(R.id.save_btn);
  mTitle = view.findViewById(R.id.editTitle);
  mDescription = view.findViewById(R.id.editDescription);

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
      //launch the implicit intent
      dispatchTakePictureIntent();
     }
   }
  });



  saveBtn.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
   // this function uploads to database
    uploadData();

   }


  });

  return view;
 }




// this code corresponds to getting result from the camera intent
 @Override
 public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
  if(requestCode == CAM_INTENT_REQUEST_CODE){
   if(resultCode == getActivity().RESULT_OK){
        File newFile = new File(currentPhotoPath);
        imageUri = Uri.fromFile(newFile);
        imgView.setImageURI(imageUri);
   }
  }
 }





//this code generates the image file for the clicked picture
 private File createImageFile() throws IOException {
  // Create an image file name
  File dirname = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
  File img = File.createTempFile("User_image", ".jpg", dirname );
  // Save a file: path for use with ACTION_VIEW intents
  currentPhotoPath = img.getAbsolutePath();
  return img;
 }



// this code launches the intent to take picture from camera and generate URI of that image
 private void dispatchTakePictureIntent() {
  Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  // Ensure that there's a camera activity to handle the intent
  if (camIntent.resolveActivity(getActivity().getPackageManager()) != null) {
   // Create the File where the photo should go
   File photoFile = null;
   try {
    photoFile = createImageFile();
   } catch (IOException ex) {
    // Error occurred while creating the File

   }
   // Continue only if the File was successfully created
   if (photoFile != null) {
    Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider", photoFile);
    camIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
    startActivityForResult(camIntent, CAM_INTENT_REQUEST_CODE);
   }
  }
 }




 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
   if(requestCode == CAM_REQUEST_CODE){
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length>0){
         dispatchTakePictureIntent();
        }
        else{
         Toast.makeText(getActivity(),"Please grant the Camera permission to use this feature",Toast.LENGTH_SHORT).show();
        }
   }

 }

 public void  uploadData (){

  if (imageUri != null){


   StorageReference imgPath = storageRef.child("user_img").child(Timestamp.now().toString()+".jpeg");


   imgPath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
    @Override
    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
     if (!task.isSuccessful()) {
      throw task.getException();
     }
     return imgPath.getDownloadUrl();
    }
   }).addOnCompleteListener(new OnCompleteListener<Uri>() {
    @Override
    public void onComplete(@NonNull Task<Uri> task) {
     if (task.isSuccessful()) {
      Uri url = task.getResult();
      Timestamp timestamp = Timestamp.now();

      Boolean isAvailable = true;
      // ------------------------------------------call function that returns address, lat and long)----------------------------------------
      double lat = 28.5355;
      double lng = 77.3910;
      String address = "Noida";
      String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(lat, lng));
      Map<String , Object> userItems = new HashMap<>();
      userItems.put("UserId","User 2");
      userItems.put("Category" ,spinnerMenu.getSelectedItem().toString());
      userItems.put("Title", mTitle.getText().toString());
      userItems.put("Description", mDescription.getText().toString());
      userItems.put("Address", address);
      userItems.put("geohash", hash);
      userItems.put("latitude", lat);
      userItems.put("longitude", lng);
      userItems.put("Image", url.toString());
      userItems.put("isAvailable",isAvailable);
      userItems.put("Timestamp",timestamp);


      firebaseFireStore.collection("User_Items").document().set(userItems).addOnCompleteListener(new OnCompleteListener<Void>() {
       @Override
       public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
         Toast.makeText(getActivity(),"Item has been added successfully",Toast.LENGTH_SHORT).show();
        }
        else{
         Toast.makeText(getActivity(),"Error, please try again",Toast.LENGTH_SHORT).show();
        }
       }
      });
     }
     }

   });

  }
 }

}
