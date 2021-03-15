package com.example.geem.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.extra.Variables;

public class FragmentAddItem extends Fragment implements AdapterView.OnItemSelectedListener
{

 public static final int CAM_REQUEST_CODE = 111;
 public static final int CAM_INTENT_REQUEST_CODE = 112;
 ImageView imgView;  //this imageView is for displaying the captured image
 Button captureImgBtn;  //this button enables user to use camera to capture image
 Spinner spinnerMenu;
 String category, description, title;


 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  //Toast.makeText(getContext(), getArguments().getString(Variables.GREETING_KEY), Toast.LENGTH_SHORT).show();
  View view = inflater.inflate(R.layout.fragment_add_item, container, false);
  imgView = view.findViewById(R.id.imageView);
  captureImgBtn = view.findViewById(R.id.capture_image);

  spinnerMenu = view.findViewById(R.id.spinner);
     ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.spinner_items,android.R.layout.simple_spinner_item);
     mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     spinnerMenu.setAdapter(mAdapter);
     spinnerMenu.setOnItemSelectedListener(this);


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
  return view;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     category = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
