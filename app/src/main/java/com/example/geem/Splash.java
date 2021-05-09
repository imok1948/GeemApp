package com.example.geem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geem.activities.MainActivity;

public class Splash extends AppCompatActivity
{
 TextView txt;
 ImageView img;
 Animation leftAnime, downAnime;
 private static int TIMER = 1500;
 SharedPreferences sp;
 
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  //remove status bar
  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_splash);
  txt = findViewById(R.id.powerView);
  img = findViewById(R.id.backimg);
  
  leftAnime = AnimationUtils.loadAnimation(this, R.anim.left_anime);
  downAnime = AnimationUtils.loadAnimation(this, R.anim.bottom_anime);
  
  img.setAnimation(leftAnime);
  txt.setAnimation(downAnime);
  
  new Handler().postDelayed(new Runnable()
  {
   
   @Override
   public void run()
   {
    //shared preferences insert
    sp = getSharedPreferences("onboard", MODE_PRIVATE);
    boolean checkFirst = sp.getBoolean("first", true);
    
    if(checkFirst)
    {
     
     SharedPreferences.Editor edit = sp.edit();
     edit.putBoolean("first", false);
     edit.commit();
     Intent i = new Intent(getApplicationContext(), Onboarding.class);
     startActivity(i);
     finish();
     
    }
    else
    {
     Intent i = new Intent(getApplicationContext(), MainActivity.class);
     startActivity(i);
     finish();
    }
    
   }
  }, TIMER);
  
  
 }
}