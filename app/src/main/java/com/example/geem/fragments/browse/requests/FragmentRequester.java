package com.example.geem.fragments.browse.requests;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geem.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentRequester extends Fragment
{
 
 private View view;
 private RecyclerView recyclerView;
 private AdapterRequester adapterRequester;
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  view = inflater.inflate(R.layout.fragment_requester, container, false);
  
  init();
  return view;
 }
 
 private void init()
 {
  adapterRequester = new AdapterRequester(getContext());
  recyclerView = view.findViewById(R.id.recycler_view);
  recyclerView.setAdapter(adapterRequester);
  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  adapterRequester.addItem(getTemplateList());
 }
 
 
 private List<RequesterTemplate> getTemplateList()
 {
  Random random = new Random();
  String[] names = new String[]{"Kirti Sanon", "Ajay Devgon", "Rishabh Patil", "Jayamurity Desikanchan Chandra Mukha Vijay", "Vijay", "Ram"};
  String[] profilePictureLinks = new String[]{
   "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F1.jpg?alt=media&token=55902603-49ef-4997-a2a5-68a4a59bdb26", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F2.jpg?alt=media&token=36b72bdf-f67f-482c-8f64-6b97a1381d82", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F3.jpg?alt=media&token=6d7df674-cada-4c9d-be72-11c82846d7f3", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F4.jpg?alt=media&token=f3906ed8-d971-45c2-b3a3-42c8f3e69a86", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F5.jpg?alt=media&token=00ce602a-6ab3-4522-8d01-54037db3834b", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F6.jpg?alt=media&token=7c5c9841-46fa-4ce0-93d9-3c3ae2c0ba42", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F7.jpg?alt=media&token=f314b274-0b1e-4ecf-9975-a3053d2ce12f", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F8.jpg?alt=media&token=f25258ff-729a-4f87-9564-c5a243a24224", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F9.jpg?alt=media&token=e8731de8-71aa-4348-95ad-e026d2035830", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2FScreenshot%20from%202020-12-23%2009-53-51.png?alt=media&token=cc99babb-dfa0-47e6-be96-d3364975d2b5", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2FScreenshot%20from%202021-03-16%2011-56-22.png?alt=media&token=4118bb3d-d851-48d0-9038-6c0ad44a2d99"
  };
  String[] addresses = new String[]{"Bhopal", "Delhi", "Thiruanantapuram Near Sagar Lake", "Goa"};
  
  
  List<RequesterTemplate> templates = new ArrayList<>();
  for(int i = 0; i < 50; i++)
  {
   templates.add(new RequesterTemplate(profilePictureLinks[random.nextInt(profilePictureLinks.length)], names[random.nextInt(names.length)], addresses[random.nextInt(addresses.length)]));
  }
  return templates;
 }
}