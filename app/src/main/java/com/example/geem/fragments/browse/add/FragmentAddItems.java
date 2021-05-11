package com.example.geem.fragments.browse.add;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.activities.MainActivity;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FragmentAddItems extends Fragment
{
 
 private static final String TAG = "FragmentAddItems";
 String currentPhotoPath;
 public static final int CAM_REQUEST_CODE = 111;
 public static final int CAM_INTENT_REQUEST_CODE = 112;
 public static final int GPS_REQUEST_CODE = 101;
 public static final int NET_REQUEST_CODE = 102;
 ImageView imgView;  //this imageView is for displaying the captured image
 Button captureImgBtn, saveBtn, locationBtn;  //this button enables user to use camera to capture image
 Spinner spinnerMenu;
 EditText mTitle, mDescription, mAddress;
 Uri imageUri, downloadUri;
 double lat;
 double lng;
 
 LocationManager locationManager;
 
 private FirebaseFirestore firebaseFireStore;
 private StorageReference storageRef;
 String userId = "random";
 private FirebaseAuth firebaseAuth;
 
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  //firebaseAuth = FirebaseAuth.getInstance();
  //user_id = firebaseAuth.getCurrentUser().getUid();
  
  View view = inflater.inflate(R.layout.fragment_browse_add_item, container, false);
  
  if(((MainActivity) getActivity()).checkLoggedIn())
  {
   userId = "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
   askPermissionAndAddLocation();
   //Toast.makeText(getContext(), "Welcome : " + userId, Toast.LENGTH_SHORT).show();
   firebaseFireStore = FirebaseFirestore.getInstance();
   storageRef = FirebaseStorage.getInstance().getReference();
   imgView = view.findViewById(R.id.image);
   saveBtn = view.findViewById(R.id.button_add);
   mTitle = view.findViewById(R.id.title);
   mAddress = view.findViewById(R.id.address);
   mDescription = view.findViewById(R.id.description);
   
   spinnerMenu = view.findViewById(R.id.category);
   ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_items, android.R.layout.simple_spinner_item);
   mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   spinnerMenu.setAdapter(mAdapter);
   
   
   saveBtn.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View v)
    {
     // this function uploads to database
     ConnectivityManager connMan = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
     NetworkInfo netInfo = connMan.getActiveNetworkInfo();
     
     //checking for network
     if(netInfo != null && netInfo.isConnected())
     {
      if(!TextUtils.isEmpty(mTitle.getText().toString()) && !TextUtils.isEmpty(mDescription.getText().toString()) && lat != 0 && lng != 0 && !TextUtils.isEmpty(mAddress.getText().toString()) && imageUri != null)
      {
       uploadData();
      }
      else
      {
       Toast.makeText(getActivity(), "Please complete all the details first", Toast.LENGTH_SHORT).show();
      }
     }
     else
     {
      Toast.makeText(getActivity(), "Network Connection is not Available", Toast.LENGTH_SHORT).show();
     }
    }
   });
   
   imgView.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View view)
    {
     askPermissionAndAddPicture();
    }
   });
   
  }
  else
  {
   ((MainActivity) getActivity()).goToLoginFragment();
  }
  return view;
 }
 
 private void askPermissionAndAddPicture()
 {
  if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
  {
   ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAM_REQUEST_CODE);
  }
  else
  {
   //launch the implicit intent
   cameraImplicitIntent();
  }
 }
 
 private void askPermissionAndAddLocation()
 {
  if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {
   ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
  }
  else
  {
   getUserLocation();
  }
 }
 
 private void getUserLocation()
 {
  locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
  if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {
   ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
  }
  
  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
  boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
  if(!isGPS)
  {
   Toast.makeText(getActivity(), "Please Start GPS to get more Accurate location", Toast.LENGTH_SHORT).show();
  }
  
  if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
  {
   
   ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, NET_REQUEST_CODE);
  }
  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
 }
 
 // this code corresponds to getting result from the camera intent
 @Override
 public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
 {
  if(requestCode == CAM_INTENT_REQUEST_CODE)
  {
   if(resultCode == getActivity().RESULT_OK)
   {
    File newFile = new File(currentPhotoPath);
    
    //save the orientation metadata of image
    ExifInterface oldExif = null;
    try
    {
     oldExif = new ExifInterface(currentPhotoPath);
    }
    catch(IOException e)
    {
     e.printStackTrace();
    }
    String exifOrientation = oldExif.getAttribute(ExifInterface.TAG_ORIENTATION);
    imageUri = Uri.fromFile(newFile);
    imgView.setImageURI(imageUri);
    imgView.getLayoutParams().height = 800;
    imgView.getLayoutParams().width = 800;
    imgView.requestLayout();
    
    File file = new File(currentPhotoPath);
    try
    {
     Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
     bitmap.compress(Bitmap.CompressFormat.JPEG, 60, new FileOutputStream(file));
     downloadUri = Uri.fromFile(file);
     
     //add the metadata of orientation to compressed image
     if(exifOrientation != null)
     {
      ExifInterface newExif = null;
      try
      {
       newExif = new ExifInterface(currentPhotoPath);
      }
      catch(IOException e)
      {
       Log.e("Error", e.toString());
      }
      newExif.setAttribute(ExifInterface.TAG_ORIENTATION, exifOrientation);
      try
      {
       newExif.saveAttributes();
      }
      catch(IOException e)
      {
       Log.e("Error", e.toString());
       ;
      }
     }
    }
    catch(Throwable t)
    {
     Log.e("ERROR", "Error compressing file." + t.toString());
     
    }
   }
  }
 }
 
 //this code generates the image file for the clicked picture
 private File createImageFile() throws IOException
 {
  File dirname = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
  File img = File.createTempFile("User_image", ".jpg", dirname);
  currentPhotoPath = img.getAbsolutePath();
  return img;
 }
 
 // this code launches the intent to take picture from camera and generate URI of that image
 private void cameraImplicitIntent()
 {
  Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  // Ensure that there's a camera activity to handle the intent
  if(camIntent.resolveActivity(getActivity().getPackageManager()) != null)
  {
   File myFile = null;
   try
   {
    myFile = createImageFile();
   }
   catch(IOException e)
   {
    Log.e("Error", e.toString());
   }
   
   if(myFile != null)
   {
    Uri fileUri = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider", myFile);
    camIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
    startActivityForResult(camIntent, CAM_INTENT_REQUEST_CODE);
   }
  }
 }
 
 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
 {
  if(requestCode == CAM_REQUEST_CODE)
  {
   if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0)
   {
    cameraImplicitIntent();
   }
   else
   {
    Toast.makeText(getActivity(), "Please grant the Camera permission to use this feature", Toast.LENGTH_SHORT).show();
   }
  }
 }
 
 
 // this code uploads data to firebase
 public void uploadData()
 {
  if(downloadUri != null)
  {
   StorageReference imgPath = storageRef.child("fetch_images").child(Timestamp.now().toString() + ".jpeg");
   //setting up progressDialog
   ProgressDialog progressDialog = new ProgressDialog(getActivity());
   progressDialog.setMessage("Uploading the item...");
   progressDialog.setCancelable(false);
   progressDialog.show();
   imgPath.putFile(downloadUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
   {
    @Override
    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
    {
     if(!task.isSuccessful())
     {
      throw task.getException();
      
     }
     return imgPath.getDownloadUrl();
    }
   }).addOnCompleteListener(new OnCompleteListener<Uri>()
   {
    @Override
    public void onComplete(@NonNull Task<Uri> task)
    {
     if(task.isSuccessful())
     {
      Uri url = task.getResult();
      Timestamp timestamp = Timestamp.now();
      Boolean isAvailable = true;
      String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(lat, lng));
      Map<String, Object> userItems = new HashMap<>();
      userItems.put("userid", userId);
      userItems.put("category", spinnerMenu.getSelectedItem().toString());
      userItems.put("title", mTitle.getText().toString());
      userItems.put("description", mDescription.getText().toString());
      userItems.put("address", mAddress.getText().toString());
      userItems.put("geohash", hash);
      userItems.put("latitude", lat);
      userItems.put("longitude", lng);
      userItems.put("image", url.toString());
      userItems.put("isavailable", isAvailable);
      userItems.put("timestamp", timestamp);
      
      
      firebaseFireStore.collection("fetch_items_final").document().set(userItems).addOnCompleteListener(new OnCompleteListener<Void>()
      {
       @Override
       public void onComplete(@NonNull Task<Void> task)
       {
        if(task.isSuccessful())
        {
         progressDialog.cancel();
         
         Log.d(TAG, "onComplete: added new item : ");
         String temp = "";
         for(Map.Entry<String, Object> map : userItems.entrySet())
         {
          temp += map.getKey() + " ==> " + map.getValue() + "\n";
         }
         Log.d(TAG, "Data : " + temp);
         
         Toast.makeText(getActivity(), "Item uploaded successfully in feeds", Toast.LENGTH_LONG).show();
         
         if(((MainActivity) getActivity()).tabLayoutForBrowseFragments != null)
         {
          ((MainActivity) getActivity()).tabLayoutForBrowseFragments.selectTab(((MainActivity) getActivity()).tabLayoutForBrowseFragments.getTabAt(1));
         }
         
         
        }
        else
        {
         Toast.makeText(getActivity(), "Error, please try again", Toast.LENGTH_SHORT).show();
        }
       }
      });
     }
    }
    
   });
   
  }
 }
 
 private final LocationListener mLocationListener = new LocationListener()
 {
  @Override
  public void onLocationChanged(@NonNull Location location)
  {
   if(location != null)
   {
    lat = location.getLatitude();
    lng = location.getLongitude();
    locationManager.removeUpdates(mLocationListener);
   }
   
  }
  
  @Override
  public void onStatusChanged(String provider, int status, Bundle extras)
  {
  }
  
  @Override
  public void onProviderEnabled(@NonNull String provider)
  {
  
  }
  
  @Override
  public void onProviderDisabled(@NonNull String provider)
  {
  
  }
 };
}
