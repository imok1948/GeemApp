package com.example.geem.fragments.browse.feeds.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.geem.R;
import com.example.geem.extra.ShivankUserItems;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

public class ActivityViewItem extends AppCompatActivity
{
 ShivankUserItems item;
 AppBarLayout appBar;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_view_item);
  item = (ShivankUserItems) getIntent().getSerializableExtra("item_details");
  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
  setSupportActionBar(toolbar);
  CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
  toolBarLayout.setTitle(getTitle());
  appBar = findViewById(R.id.app_bar);
  Glide.with(this).asBitmap().load(item.getImage()).into(new CustomTarget<Bitmap>()
  {
   @Override
   public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
   {
    appBar.setBackground(new BitmapDrawable(resource));
   }
   
   @Override
   public void onLoadCleared(@Nullable Drawable placeholder)
   {
   }
  });
  
  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
  fab.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
   }
  });
 }
}