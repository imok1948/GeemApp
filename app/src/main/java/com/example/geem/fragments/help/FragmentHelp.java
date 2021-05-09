package com.example.geem.fragments.help;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.geem.R;


public class FragmentHelp extends Fragment
{
 
 
 private static final int MAX_STEP = 4;
 
 private ViewPager viewPager;
 private MyViewPagerAdapter myViewPagerAdapter;
 private String about_title_array[] = {"Giveaway Items", "Browse Categories", "Thanks and Ratings", "Minimalist Lifestyle"};
 private String about_description_array[] = {"A place to Giveaway items you no longer Need", "Browse items of various categories for Reuse", "Thanks on item GiveAway and User Ratings", "Journey of a Minimalist Lifestyle starts Here",};
 private int about_images_array[] = {R.drawable.slide1, R.drawable.slide2,R.drawable.slide3, R.drawable.slide4};
 
 
 View root;
 
 public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  root = inflater.inflate(R.layout.fragment_help, container, false);
 // Toast.makeText(getContext(), "Help & Feedback Fragment", Toast.LENGTH_SHORT).show();
  
  
  viewPager = (ViewPager) root.findViewById(R.id.view_pager);
  
  // adding bottom dots
  bottomProgressDots(0);
  
  myViewPagerAdapter = new MyViewPagerAdapter();
  viewPager.setAdapter(myViewPagerAdapter);
  viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
  
  return root;
 }
 
 
 private void bottomProgressDots(int current_index)
 {
  LinearLayout dotsLayout = (LinearLayout) root.findViewById(R.id.layoutDots);
  ImageView[] dots = new ImageView[MAX_STEP];
  
  dotsLayout.removeAllViews();
  for(int i = 0; i < dots.length; i++)
  {
   dots[i] = new ImageView(getActivity());
   int width_height = 15;
   LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
   params.setMargins(10, 10, 10, 10);
   dots[i].setLayoutParams(params);
   dots[i].setImageResource(R.drawable.right_arrow_new_foreground);
   dots[i].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
   dotsLayout.addView(dots[i]);
  }
  
  if(dots.length > 0)
  {
   dots[current_index].setImageResource(R.drawable.common_google_signin_btn_icon_light);
   dots[current_index].setColorFilter(getResources().getColor(R.color.light_green_600), PorterDuff.Mode.SRC_IN);
  }
 }
 
 //  viewpager change listener
 ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener()
 {
  
  @Override
  public void onPageSelected(final int position)
  {
   bottomProgressDots(position);
  }
  
  @Override
  public void onPageScrolled(int arg0, float arg1, int arg2)
  {
  
  }
  
  @Override
  public void onPageScrollStateChanged(int arg0)
  {
  
  }
 };
 
 /**
  * View pager adapter
  */
 public class MyViewPagerAdapter extends PagerAdapter
 {
  private LayoutInflater layoutInflater;
  private Button btnNext;
  
  public MyViewPagerAdapter()
  {
  }
  
  @Override
  public Object instantiateItem(ViewGroup container, int position)
  {
   layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   
   View view = layoutInflater.inflate(R.layout.item_card_wizard_light, container, false);
   ((TextView) view.findViewById(R.id.title)).setText(about_title_array[position]);
   ((TextView) view.findViewById(R.id.description)).setText(about_description_array[position]);
   ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);
   
   btnNext = (Button) view.findViewById(R.id.btn_next);
   
   if(position == about_title_array.length - 1)
   {
    btnNext.setText("Get Started");
   }
   else
   {
    btnNext.setText("Next");
   }
   
   
   btnNext.setOnClickListener(new View.OnClickListener()
   {
    @Override
    public void onClick(View v)
    {
     int current = viewPager.getCurrentItem() + 1;
     if(current < MAX_STEP)
     {
      // move to next screen
      viewPager.setCurrentItem(current);
     }
     else
     {
      //finish();
      getActivity().getFragmentManager().popBackStack();
     }
    }
   });
   
   container.addView(view);
   return view;
  }
  
  @Override
  public int getCount()
  {
   return about_title_array.length;
  }
  
  @Override
  public boolean isViewFromObject(View view, Object obj)
  {
   return view == obj;
  }
  
  
  @Override
  public void destroyItem(ViewGroup container, int position, Object object)
  {
   View view = (View) object;
   container.removeView(view);
  }
 }
}