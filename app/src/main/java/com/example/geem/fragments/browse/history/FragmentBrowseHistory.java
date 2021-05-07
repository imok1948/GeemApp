package com.example.geem.fragments.browse.history;


import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.activities.MainActivity;
import com.example.geem.fragments.browse.history.history.ActivityHistoryDetailStarter;
import com.example.geem.fragments.browse.history.history.FragmentHistoryDetail;
import com.example.geem.fragments.browse.history.history.HistoryStructure;
import com.example.geem.fragments.browse.history.history.Variables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FragmentBrowseHistory extends Fragment
{
 private final static String FEEDS_COLLECTION_NAME = "User_Items";
 private final int LOAD_ITEMS_AT_ONCE = 10;
 FragmentBrowseHistory fragmentHistoryInstance = null;
 HistoryAdapter historyAdapter;
 RecyclerView recyclerView;
 
 View globalView;
 
 private Button buttonUponRecyclerView;
 private FirebaseFirestore firebaseFirestore;
 private CollectionReference feedsCollectionReference;
 private DocumentSnapshot lastDocumentSnapshot = null;
 private boolean someDataRemaining = true;
 private String userName = "User 2";
 
 
 public FragmentBrowseHistory()
 {
  fragmentHistoryInstance = this;
 }
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
  Log.i("AUTH : ", firebaseAuth + "");
  
  View view = inflater.inflate(R.layout.fragment_browse_history, container, false);
  
  if(((MainActivity) getActivity()).checkLoggedIn())
  {
   Log.i("USER : ", userName);
   firebaseFirestore = FirebaseFirestore.getInstance();
   feedsCollectionReference = firebaseFirestore.collection(FEEDS_COLLECTION_NAME);
   
   globalView = view;
   
   recyclerView = view.findViewById(R.id.recyclerViewForHistory);
   
   HistoryAdapter historyAdapter = new HistoryAdapter(new LinkedList<>());
   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
   recyclerView.setLayoutManager(linearLayoutManager);
   recyclerView.setAdapter(historyAdapter);
   recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
   
   ProgressDialog progressDialog = new ProgressDialog(getActivity());
   progressDialog.setMessage("Loading ...");
   progressDialog.show();
   progressDialog.setCancelable(true);
   
   
   if(!netAvailable())
   {
    Log.i("FIRE : ", HistoryStructure.OWNER_USER_ID + ", " + "User : " + userName);
    feedsCollectionReference.orderBy(HistoryStructure.POST_UPLOADING_TIME).whereEqualTo(HistoryStructure.OWNER_USER_ID, userName).limit(LOAD_ITEMS_AT_ONCE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
    {
     @Override
     public void onComplete(@NonNull Task<QuerySnapshot> task)
     {
      progressDialog.cancel();
      if(task.isSuccessful())
      {
       List<HistoryStructure> historyStructureList = new LinkedList<>();
       
       someDataRemaining = false;
       for(DocumentSnapshot documentSnapshot : task.getResult())
       {
        HistoryStructure structure = new HistoryStructure();
        structure.setHistoryId(documentSnapshot.getId());
        
        structure.setTitle(documentSnapshot.get(HistoryStructure.POST_TITLE).toString());
        structure.setDescription(documentSnapshot.get(HistoryStructure.POST_DESCRIPTION).toString());
        structure.setAddress(documentSnapshot.get(HistoryStructure.OWNER_ADDRESS).toString());
        structure.setTimestamp((Timestamp) documentSnapshot.get(HistoryStructure.POST_UPLOADING_TIME));
        Log.i("TIME : ", documentSnapshot.get(HistoryStructure.POST_UPLOADING_TIME).toString());
        structure.setPlace(documentSnapshot.get(HistoryStructure.ITEM_AVAILABLE_PLACE).toString());
        
        structure.setType(documentSnapshot.get(HistoryStructure.POST_CATEGORY).toString());
        structure.setItemTaken((Boolean) documentSnapshot.get(HistoryStructure.ITEM_STATUS));
        
        
        //Delete these 2 lines after multiple images available
        List<String> photoURLlist = new LinkedList<>();
        photoURLlist.add(documentSnapshot.get(HistoryStructure.POST_IMAGES).toString());
        structure.setPhotoUrl(photoURLlist);
        //After multiple url available
        //structure.setPhotoUrl((List<String>) documentSnapshot.get(HistoryRowStructure.POST_IMAGES));
        
        structure.verbose();
        
        historyStructureList.add(structure);
        lastDocumentSnapshot = documentSnapshot;
        someDataRemaining = true;
       }
       historyAdapter.addData(historyStructureList);
      }
      else
      {
       Log.i("FIRE ERROR", "" + task.getException());
       Toast.makeText(getActivity(), "Error while retrieving names from server ", Toast.LENGTH_SHORT).show();
      }
      
      
     }
    });
   }
   else
   {
    if(!netAvailable())
    {
     //Snackbar.make(getView(), "Internet not available", Snackbar.LENGTH_SHORT).show();
    }
    else if(!someDataRemaining)
    {
     Snackbar.make(getView(), "No more history", Snackbar.LENGTH_SHORT).show();
    }
   }
   
   
   recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
   {
    boolean scrolledDown = true; //To checking if scrolled downor up using onScrolled (dy)
    
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
    {
     super.onScrolled(recyclerView, dx, dy);
     if(dy < 0)
     {
      Log.d("SCROLL STATE", "Up");
      scrolledDown = false;
     }
     else
     {
      Log.d("SCROLL STATE", "Down");
      scrolledDown = true;
     }
    }
    
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
    {
     super.onScrollStateChanged(recyclerView, newState);
     if(newState == RecyclerView.SCROLL_STATE_IDLE && scrolledDown && someDataRemaining && lastDocumentSnapshot != null) //Load new items if scrolled down and items vanished
     {
      if(!netAvailable())
      {
       ProgressDialog progressDialog = new ProgressDialog(getActivity());
       progressDialog.setMessage("Loading ...");
       progressDialog.show();
       progressDialog.setCancelable(true);
       
       
       feedsCollectionReference.orderBy(HistoryStructure.POST_UPLOADING_TIME).whereEqualTo(HistoryStructure.OWNER_USER_ID, userName).startAfter(lastDocumentSnapshot).limit(LOAD_ITEMS_AT_ONCE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
       {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task)
        {
         if(task.isSuccessful())
         {
          List<HistoryStructure> historyStructureList = new LinkedList<>();
          someDataRemaining = false;
          for(DocumentSnapshot documentSnapshot : task.getResult())
          {
           HistoryStructure structure = new HistoryStructure();
           structure.setHistoryId(documentSnapshot.getId());
           
           structure.setTitle(documentSnapshot.get(HistoryStructure.POST_TITLE).toString());
           structure.setDescription(documentSnapshot.get(HistoryStructure.POST_DESCRIPTION).toString());
           structure.setAddress(documentSnapshot.get(HistoryStructure.OWNER_ADDRESS).toString());
           structure.setTimestamp((Timestamp) documentSnapshot.get(HistoryStructure.POST_UPLOADING_TIME));
           structure.setPlace(documentSnapshot.get(HistoryStructure.ITEM_AVAILABLE_PLACE).toString());
           
           structure.setType(documentSnapshot.get(HistoryStructure.POST_CATEGORY).toString());
           structure.setItemTaken((Boolean) documentSnapshot.get(HistoryStructure.ITEM_STATUS));
           //Delete these 2 lines after multiple images available
           List<String> photoURLlist = new LinkedList<>();
           photoURLlist.add(documentSnapshot.get(HistoryStructure.POST_IMAGES).toString());
           structure.setPhotoUrl(photoURLlist);
           //After multiple url available
           //structure.setPhotoUrl((List<String>) documentSnapshot.get(HistoryRowStructure.POST_IMAGES));
           
           structure.verbose();
           
           
           historyStructureList.add(structure);
           lastDocumentSnapshot = documentSnapshot;
           someDataRemaining = true;
          }
          progressDialog.cancel();
          historyAdapter.addData(historyStructureList);
         }
         else
         {
          Toast.makeText(getActivity(), "Error while retrieving names from server 2", Toast.LENGTH_SHORT).show();
         }
         
        }
       });
      }
     }
     else
     {
      if(false)
      {
       Snackbar.make(getView(), "Internet not available", Snackbar.LENGTH_SHORT).show();
      }
      else if(someDataRemaining)
      {
       //Snackbar.make(getView(), "No more history", Snackbar.LENGTH_SHORT).show();
      }
      {
      
      }
     }
    }
    
   });
  }
  else
  {
   ((MainActivity) getActivity()).goToLoginFragment();
  }
  return view;
 }
 
 public boolean netAvailable()
 {
  return false;
  /*
  boolean netAvailable = false;
  try
  {
   NetworkInfo networkInfo = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
   netAvailable = (networkInfo != null && networkInfo.isConnected()) ? true : false;
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
  return netAvailable;*/
 }
}


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
//
//
//
//
//
//


class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryRowHolder>
{
 List<HistoryStructure> historyStructureList;
 
 public HistoryAdapter(List<HistoryStructure> currentStructures)
 {
  this.historyStructureList = new LinkedList<>();
  for(HistoryStructure historyStructure : currentStructures)
  {
   this.historyStructureList.add(historyStructure);
  }
 }
 
 @NonNull
 @Override
 public HistoryAdapter.HistoryRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
 {
  LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
  View view = layoutInflater.inflate(R.layout.recycler_row_history_items, parent, false);
  return new HistoryAdapter.HistoryRowHolder(view);
 }
 
 @Override
 public void onBindViewHolder(@NonNull HistoryAdapter.HistoryRowHolder holder, int position)
 {
  HistoryStructure currentStructure = historyStructureList.get(position);
  Timestamp timestamp = currentStructure.getTimestamp();
  
  holder.textViewTitle.setText(currentStructure.getTitle());
  holder.textViewCategory.setText(currentStructure.getType());
  
  //holder.textViewDate.setText("16 Feb");
  boolean itemStatus = currentStructure.isItemTaken();
  
  final String DATE_FORMAT = "EE, d MMMM";
  if(itemStatus)
  {
   holder.textViewDate.setText("Taken on " + (new SimpleDateFormat(DATE_FORMAT)).format(new Date(timestamp.getSeconds())));
   Log.i("355", "" + new Date(timestamp.getNanoseconds()));
  }
  else
  {
   holder.textViewDate.setText("Given on " + (new SimpleDateFormat(DATE_FORMAT)).format(new Date(timestamp.getSeconds())));
  }
  
  if(currentStructure.getPhotoUrl() != null && currentStructure.getPhotoUrl().size() >= 1)
  {
   Log.i("RECYCLER VIEW ", "Image loading : " + position);
   Glide.with(holder.imageViewImage.getContext()).load(currentStructure.getPhotoUrl().get(0)).into(holder.imageViewImage);
   // Picasso.get().load(currentStructure.getPhotoUrl().get(0)).error(R.drawable.common_full_open_on_phone).into(holder.imageViewImage);
  }
  else
  {
   Log.i("RECYCLER ERROR ", "url exists : " + (currentStructure.getPhotoUrl() == null));
   Log.i("RECYCLER ERROR ", "url size : " + currentStructure.getPhotoUrl().size());
   //Picasso.get().load(R.drawable.ic_tab_profile).into(holder.imageViewImage);
  }
  
  
  holder.itemView.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    FragmentHistoryDetail fragmentHistoryDetail = new FragmentHistoryDetail();
    Bundle bundle = new Bundle();
    bundle.putString(Variables.HISTORY_KEY_TO_DETAIL_FRAGMENT, currentStructure.getHistoryId());
    fragmentHistoryDetail.setArguments(bundle);
    
    
    /*
    FrameLayout frameLayout = ((AppCompatActivity) view.getContext()).findViewById(R.id.history_fragment_framelayout);
    frameLayout.setVisibility(View.GONE);
    */
    
    
    //  ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.history_fragment_framelayout, fragmentHistoryDetail).addToBackStack(null).commit();
    //((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction().remove(fragmentHistoryDetail);
    
    Intent intent = new Intent(view.getContext(), ActivityHistoryDetailStarter.class);
    intent.putExtra(Variables.HISTORY_KEY_TO_DETAIL_FRAGMENT, currentStructure.getHistoryId());
    view.getContext().startActivity(intent);
   }
  });
 }
 
 
 //WhatsApp kr dena me dusre tab pe hu, jb b kuch puchna ho to
 @Override
 public int getItemCount()
 {
  return historyStructureList.size();
 }
 
 public void addData(List<HistoryStructure> historyStructureListExtended)
 {
  for(HistoryStructure structure : historyStructureListExtended)
  {
   this.historyStructureList.add(structure);
  }
  
  //After inserting all items, notify to show these
  // notifyItemInserted(this.historyRowStructureList.size() - 1);
  notifyItemRangeChanged(0, 1);
 }
 
 
 class HistoryRowHolder extends RecyclerView.ViewHolder
 {
  TextView textViewTitle, textViewCategory, textViewDate, textViewItemStatus;
  ImageView imageViewImage, imageViewRightArrow;
  
  public HistoryRowHolder(@NonNull View itemView)
  {
   super(itemView);
   textViewTitle = itemView.findViewById(R.id.historyTitleTextView);
   textViewCategory = itemView.findViewById(R.id.historyCategoryTextView);
   textViewDate = itemView.findViewById(R.id.history_right_arrow_image);
   //textViewItemStatus = itemView.findViewById(R.id.historyItemStatusTextView);
   imageViewImage = itemView.findViewById(R.id.historyRowImage);
   
   //Picasso.get().load(R.drawable.ic_tab_profile).into((Target) itemView.findViewById(R.id.history_right_arrow_image));
   // imageViewRightArrow.setImageResource(R.drawable.right_arrow_background);
   
  }
 }
}
