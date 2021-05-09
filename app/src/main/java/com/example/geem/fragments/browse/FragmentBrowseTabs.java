package com.example.geem.fragments.browse;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.example.geem.activities.MainActivity;
import com.example.geem.fragments.browse.notifications.DummyTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;


public class FragmentBrowseTabs extends Fragment
{
 private final static String TAG = "FragmentBrowseTabs";
 ViewPagerAdapter adapterTemp;
 private TabLayout tabLayout;
 private ViewPager viewPager;
 
 public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View root = inflater.inflate(R.layout.fragment_browse_tabs, container, false);
  
  tabLayout = root.findViewById(R.id.tabLayout1);
  viewPager = root.findViewById(R.id.viewpages1);
  
  Log.d(TAG, "onCreateView: tablayout : " + ((MainActivity) getActivity()).tabLayoutForBrowseFragments);
  
  adapterTemp = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
  viewPager.setAdapter(adapterTemp);
  tabLayout.setupWithViewPager(viewPager);
  
  
  for(int i = 0; i < tabLayout.getTabCount(); i++)
  {
   tabLayout.getTabAt(i).setIcon(Variables.TAB_ICONS[i]);
  }
  
  try
  {
   Toast.makeText(getContext(), "Welcome : " + FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
  
  if(((MainActivity) getActivity()).tabLayoutForBrowseFragments != null)
  {
   int position = ((MainActivity) getActivity()).tabLayoutForBrowseFragments.getSelectedTabPosition();
   tabLayout.selectTab(tabLayout.getTabAt(position));
  }
  else
  {
   tabLayout.selectTab(tabLayout.getTabAt(2));
  }
  return root;
 }
 
 @Override
 public void onSaveInstanceState(@NonNull Bundle outState)
 {
  super.onSaveInstanceState(outState);
  outState.putString("rahul", "meena");
 }
 
 
 private void toast(String s)
 {
  Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
 }
}