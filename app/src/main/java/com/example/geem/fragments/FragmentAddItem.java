package com.example.geem.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
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

import com.example.geem.MainActivity;
import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class FragmentAddItem extends Fragment
{
 
 //private String[] randomUsernames = new String[]{"aman", "vinay", "shubham", "sujeet", "bramha", "vishnu", "mahesh", "narad", "shani", "shiv", "tanmoy", "dev", "akhil", "pragya", "binod", "hitesh", "danish"};
 //private String[] randomAddresses = new String[]{"A,37/1, Midc Indl Area, Bhnd Bhangar Kata, Taloja, Navi Mumbai", "1, Agarwal Nagar, 1 Marol Chimatpada, J.b.nagar\n" + "\n" + "City:   Mumbai", "484/71 \" Laxmi Vilas \", Mitramandal Colony", "138, Bapu Khote Street, Mandvi", "Shop No 4, Vishnu Apt, L T Rd, Babhi Naka, Borivli (w)", "Shop No 46, United Bldg, Bhd Hotel Ambasador, Paradise", "86, Appaavu Garamani Street Mount Road", "330,1st Floor, Sampige Road, Opp Food World, Malleswaram", "74, Ibrahim Manzil, I Rehem - Tulla Road, Bhendi Bazar, Mandvi", "3/3, 4 A Main K R Road, Near Garadi Apts/tata Silk Farm Cir, Yediyur", "1604, 14, Amba Deep Building, K G Marg, Connaught Place", "radha-swami \", Opp Yesh Complex, Gotri", "2, Lily Apt, Agiary Lane, Thane (west)", "Bahadur Shah Zafar Marg", "1. Vms Shpg Centre, New Sama Road, New Sama Road", "Paldi Char Rasta Paldi, Nr Udipi Hote, Paldi", "4/27, Kirti Nagar Indl Area"};
 //private double[][] randomGPScoordinates = new double[][]{new double[]{24.879999, 74.629997}, new double[]{16.994444, 73.300003}, new double[]{19.155001, 72.849998}, new double[]{24.794500, 73.055000}, new double[]{21.250000, 81.629997}, new double[]{16.166700, 74.833298}, new double[]{26.850000, 80.949997}, new double[]{28.679079, 77.069710}, new double[]{19.076090, 72.877426}, new double[]{14.167040, 75.040298}, new double[]{26.540457, 88.719391}, new double[]{24.633568, 87.849251}, new double[]{28.440554, 74.493011}, new double[]{24.882618, 72.858894}, new double[]{16.779877, 74.556374}, new double[]{12.715035, 77.281296}, new double[]{13.432515, 77.727478}, new double[]{12.651805, 77.208946}, new double[]{22.728392, 71.637077}, new double[]{9.383452, 76.574059}, new double[]{14.623801, 75.621788}, new double[]{10.925440, 79.838005}, new double[]{15.852792, 74.498703}, new double[]{19.354979, 84.986732}, new double[]{23.905445, 87.524620}, new double[]{20.296059, 85.824539}, new double[]{21.105001, 71.771645}, new double[]{30.172716, 77.299492}, new double[]{25.477585, 85.709091}, new double[]{21.045521, 75.801094}, new double[]{26.491890, 89.527100}, new double[]{8.893212, 76.614143}, new double[]{22.430889, 87.321487}, new double[]{23.849325, 72.126625}, new double[]{11.786253, 77.800781}, new double[]{13.583274, 76.540154}, new double[]{14.530457, 75.801094}, new double[]{18.901457, 73.176132}, new double[]{22.960510, 88.567406}, new double[]{15.756595, 76.192696}, new double[]{22.656015, 86.352882}, new double[]{25.989836, 79.450035}, new double[]{23.223047, 82.870560}, new double[]{19.186354, 73.191948}, new double[]{30.525005, 75.890121}, new double[]{22.422455, 85.760651}, new double[]{18.106659, 83.395554}, new double[]{21.190449, 81.284920}, new double[]{23.597969, 72.969818}, new double[]{28.590361, 78.571762}, new double[]{25.369179, 85.530060}, new double[]{11.623377, 92.726486}, new double[]{24.618393, 88.024338}, new double[]{23.546757, 74.433830}, new double[]{41.643414, 41.639900}, new double[]{25.077787, 87.900375}, new double[]{29.854263, 77.888000}, new double[]{14.913181, 79.992981}, new double[]{14.413745, 77.712616}, new double[]{18.101904, 78.852074}, new double[]{23.173939, 81.565125}, new double[]{15.812074, 80.355377}, new double[]{15.764501, 79.259491}, new double[]{10.311879, 76.331978}, new double[]{21.961946, 70.792297}, new double[]{16.544893, 81.521240}, new double[]{21.049540, 76.532028}, new double[]{26.182245, 91.754723}, new double[]{27.897551, 77.384117}, new double[]{18.245655, 76.505356}, new double[]{23.486839, 75.659157}, new double[]{32.041943, 75.405334}, new double[]{24.474380, 85.688744}, new double[]{23.427221, 87.287018}, new double[]{19.487707, 75.380768}, new double[]{19.853060, 74.000633}, new double[]{15.167409, 77.373627}, new double[]{24.417534, 88.250343}, new double[]{22.744108, 77.736969}, new double[]{14.752805, 78.552757}, new double[]{23.520399, 87.311897}, new double[]{25.771315, 73.323685}, new double[]{28.148735, 77.332024}, new double[]{29.138407, 76.693245}, new double[]{25.375710, 86.474373}, new double[]{20.388794, 78.120407}, new double[]{23.669296, 86.151115}, new double[]{21.761524, 70.627625}, new double[]{22.657402, 88.867180}, new double[]{22.700474, 88.319069}, new double[]{23.344315, 85.296013}, new double[]{14.146319, 79.850388}, new double[]{28.078636, 80.471588}, new double[]{27.108416, 78.584602}, new double[]{9.823619, 77.986565}, new double[]{12.946366, 79.959244}, new double[]{17.143908, 79.623924}};
 
 String currentPhotoPath;
 public static final int CAM_REQUEST_CODE = 111;
 public static final int CAM_INTENT_REQUEST_CODE = 112;
 ImageView imgView;  //this imageView is for displaying the captured image
 Button captureImgBtn, saveBtn;  //this button enables user to use camera to capture image
 Spinner spinnerMenu;
 EditText mTitle, mDescription;
 Uri imageUri, downloadUri;
 double lat;
 double lng;
 String address;
 
 
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
  saveBtn = view.findViewById(R.id.save_btn);
  mTitle = view.findViewById(R.id.editTitle);
  mDescription = view.findViewById(R.id.editDescription);
  
  spinnerMenu = view.findViewById(R.id.spinner);
  ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_items, android.R.layout.simple_spinner_item);
  mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  spinnerMenu.setAdapter(mAdapter);
  
  
  captureImgBtn.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View v)
   {
    //ask user for camera permission at runtime
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
  });
  
  
  saveBtn.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View v)
   {
    // this function uploads to database
    
    uploadData();
   }
  });
  return view;
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
    try {
     oldExif = new ExifInterface(currentPhotoPath);
    } catch (IOException e) {
     e.printStackTrace();
    }
    String exifOrientation = oldExif.getAttribute(ExifInterface.TAG_ORIENTATION);
    imageUri = Uri.fromFile(newFile);
    imgView.setImageURI(imageUri);

    File file = new File (currentPhotoPath);
    try {
     Bitmap bitmap = BitmapFactory.decodeFile (file.getPath ());
     bitmap.compress (Bitmap.CompressFormat.JPEG,60, new FileOutputStream(file));
     downloadUri = Uri.fromFile(file);

     //add the metadata of orientation to compressed image
     if (exifOrientation != null) {
      ExifInterface newExif = null;
      try {
       newExif = new ExifInterface(currentPhotoPath);
      } catch (IOException e) {
       Log.e("Error",e.toString());
      }
      newExif.setAttribute(ExifInterface.TAG_ORIENTATION, exifOrientation);
      try {
       newExif.saveAttributes();
      } catch (IOException e) {
       Log.e("Error",e.toString());;
      }
     }
    }
    catch (Throwable t) {
     Log.e("ERROR", "Error compressing file." + t.toString ());

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
      // ------------------------------------------call function that returns address, lat and long)----------------------------------------
      
      
      double lat = 28.5355;
      double lng = 77.3910;
      String address = "Noida";
      String username = "user2";
      

      /*
      Random random = new Random();
      int gpsRand = (int) random.nextInt(randomGPScoordinates.length);
      int userRand = (int) random.nextInt(randomUsernames.length);
      int addressRand = (int) random.nextInt(randomAddresses.length);
      
      lat = randomGPScoordinates[gpsRand][0];
      lng = randomGPScoordinates[gpsRand][1];
      address = randomAddresses[addressRand];
      username = randomUsernames[userRand];
      
      Log.i("RANDOM GPS :", lat + " , " + lng);
      Log.i("RANDOM USER :", username);
      Log.i("RANDOM ADDRESS :", address);
        */
      
      String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(lat, lng));
      Map<String, Object> userItems = new HashMap<>();
      userItems.put("userid", username);
      userItems.put("category", spinnerMenu.getSelectedItem().toString());
      userItems.put("title", mTitle.getText().toString());
      userItems.put("description", mDescription.getText().toString());
      userItems.put("address", address);
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

         Toast.makeText(getActivity(), "Item uploaded successfully in feeds", Toast.LENGTH_LONG).show();
         Intent i = new Intent(getActivity().getApplicationContext(),MainActivity.class);
         getActivity().startActivity(i);

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
 
}
