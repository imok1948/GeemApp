package com.example.geem.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.temp.ui.ui.browse2.history2.history.HistoryStructure;
import com.example.geem.temp.ui.ui.browse2.history2.history.Variables;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FragmentHistoryDetail extends Fragment
{
 
 private final String HISTORY_ID = "History ID : ";
 private final String FROM = "FROM ";
 private final String ON_DATE = "ON ";
 private final String ADDRESS = "ADDRESS ";
 private final String LOADING_MESSAGE = "Loading ... ";
 private final String ERROR_MESSAGE_DOCUMENT_NOT_FOUND = "Error while retrieving document ";
 private String firestoreHistoryDocumentId;
 private TextView textViewHistoryId;
 
 private TextView textViewPostTitle, textViewPostDescription, textViewPostOwner, textViewItemPostedTimestamp, textViewPostAddress;
 private ImageView imageViewItemImage;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View view = inflater.inflate(R.layout.fragment_history_detail2, container, false);
  firestoreHistoryDocumentId = (getArguments() != null) ? getArguments().getString(Variables.HISTORY_KEY_TO_DETAIL_FRAGMENT) : "------------";
  
  textViewHistoryId = view.findViewById(R.id.history_detail_historyId);
  textViewHistoryId.setText("History ID - " + firestoreHistoryDocumentId);
  
  textViewPostTitle = view.findViewById(R.id.history_detail_post_title);
  imageViewItemImage = view.findViewById(R.id.history_detail_post_image);
  textViewPostDescription = view.findViewById(R.id.history_post_description);
  textViewPostOwner = view.findViewById(R.id.history_detail_post_owner);
  textViewItemPostedTimestamp = view.findViewById(R.id.history_detail_item_posting_time);
  textViewPostAddress = view.findViewById(R.id.history_detail_post_address);
  
  
  ProgressDialog progressDialog = new ProgressDialog(getActivity());
  progressDialog.setCancelable(false);
  progressDialog.setMessage(LOADING_MESSAGE);
  progressDialog.show();
  
  //Do this all in single class after feeds structure finalized
  DocumentReference feedDocumentReference = FirebaseFirestore.getInstance().collection(Variables.HISTORY_COLLECTION_REFERENCE).document(firestoreHistoryDocumentId);
  
  feedDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    progressDialog.cancel();
    DocumentSnapshot documentSnapshot = task.getResult();
    if(task.isSuccessful() && documentSnapshot.exists())
    {
     
     
     textViewHistoryId.setText(HISTORY_ID + documentSnapshot.getId());
     textViewPostTitle.setText(documentSnapshot.get(HistoryStructure.POST_TITLE).toString());
     
     //Delete these 2 lines after multiple images available
     List<String> photoURLlist = new LinkedList<>();
     photoURLlist.add(documentSnapshot.get(HistoryStructure.POST_IMAGES).toString());
     photoURLlist.add(documentSnapshot.get(HistoryStructure.POST_IMAGES).toString());
     photoURLlist.add(documentSnapshot.get(HistoryStructure.POST_IMAGES).toString());
     
     //After multiple url available
     //photoURLlist = (List<String>) documentSnapshot.get(HistoryStructure.POST_IMAGES);
     if(photoURLlist != null && photoURLlist.size() >= 0)
     {
      
      ViewPager viewPager = view.findViewById(R.id.history_detail_view_pager_images);
      //SliderHistoryDetail sliderHistoryDetail = new SliderHistoryDetail(getActivity());
      SliderHistoryDetail sliderHistoryDetail = new SliderHistoryDetail();
      sliderHistoryDetail.addContext(getActivity());
      sliderHistoryDetail.setItemImagesList(photoURLlist);
      viewPager.setAdapter(sliderHistoryDetail);
      
      // Picasso.get().load(photoURLlist.get(0)).error(R.drawable.common_full_open_on_phone).into(imageViewItemImage);
      //Glide.with(imageViewItemImage.getContext()).load(photoURLlist.get(0)).into(imageViewItemImage);
     }
     
     textViewPostDescription.setText(documentSnapshot.get(HistoryStructure.POST_DESCRIPTION).toString());
     textViewPostOwner.setText(FROM + documentSnapshot.get(HistoryStructure.OWNER_USER_ID).toString());
     Timestamp timestamp = (Timestamp) documentSnapshot.get(HistoryStructure.POST_UPLOADING_TIME);
     String postingDate = (new SimpleDateFormat(Variables.HISTORY_DETAIL_DATE_FORMAT)).format(new Date(timestamp.getNanoseconds()));
     textViewItemPostedTimestamp.setText(ON_DATE + postingDate);
     textViewPostAddress.setText(documentSnapshot.get(HistoryStructure.OWNER_ADDRESS).toString());
     double gpsLongitude = 0, gpsLatitude = 0;
     
     gpsLatitude = (double) documentSnapshot.get(HistoryStructure.ITEM_GPS_LATITUDE);
     gpsLongitude = (double) documentSnapshot.get(HistoryStructure.ITEM_GPS_LONGITUDE);
     
     MapView mapView = view.findViewById(R.id.mapView);
     mapView.onCreate(savedInstanceState);
     mapView.getMapAsync(new OnMapReadyCallback()
     {
      @Override
      public void onMapReady(GoogleMap googleMap)
      {
      
      }
     });
     
     GoogleMap globalMap;
     mapView.getMapAsync(new OnMapReadyCallback()
     {
      @Override
      public void onMapReady(GoogleMap googleMap)
      {
       googleMap.getUiSettings().setMyLocationButtonEnabled(false);
       googleMap.setMyLocationEnabled(true);
       googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(43.54, -87.98)));
      }
     });
    }
    else
    {
     snack(ERROR_MESSAGE_DOCUMENT_NOT_FOUND);
     //Toast.makeText(getActivity(), "Error while getting doc history", Toast.LENGTH_SHORT).show();
    }
   }
  });
  
  return view;
 }
 
 private void toast(String msg)
 {
  Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
 }
 
 
 private void snack(String msg)
 {
  Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
 }
}


///
//
//
//
//
//
//
//
//
//
//

class SliderHistoryDetail extends PagerAdapter
{
 private Context myContext;
 
 private List<String> itemImagesList;
 
 
 public SliderHistoryDetail()
 {
 
 }
 
 public SliderHistoryDetail(List<String> itemImagesList)
 {
  this.itemImagesList = new LinkedList<>();
  for(String s : itemImagesList)
  {
   this.itemImagesList.add(s);
  }
 }
 
 public void addContext(Context context)
 {
  myContext = context;
 }
 
 public List<String> getItemImagesList()
 {
  return itemImagesList;
 }
 
 public void setItemImagesList(List<String> itemImagesList)
 {
  this.itemImagesList = itemImagesList;
 }
 
 @Override
 public int getCount()
 {
  return itemImagesList.size();
 }
 
 @Override
 public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
 {
  return view == object;
 }
 
 @NonNull
 @Override
 public Object instantiateItem(@NonNull ViewGroup container, int position)
 {
  LayoutInflater layoutInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  View slidingImageLayout = layoutInflater.inflate(R.layout.history_detail_image_slider, null);
  
  //View slidingImageLayout = LayoutInflater.from(myContext).inflate(R.layout.history_detail_image_slider, container, false);
  
  //  assert slidingImageLayout != null;
  
  ImageView imageView = slidingImageLayout.findViewById(R.id.history_detail_image_slider_imageview);
  
  Picasso.get().load(itemImagesList.get(position)).error(R.drawable.elon).into(imageView);
  //  Picasso.get().load("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png").error(R.drawable.elon).into(imageView);
  //imageView.setImageResource(itemImagesList.get(position));
  ((ViewPager) container).addView(slidingImageLayout);
  
  return slidingImageLayout;
 }
 
 @Override
 public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
 {
  container.removeView((View) object);
 }
 
 
}























