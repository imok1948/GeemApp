package com.example.geem.fragments.browse.messages.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.geem.R;
import com.example.geem.activities.ActivityShowUserProfile;
import com.example.geem.extra.TimeDetails;
import com.example.geem.extra.Variables;
import com.example.geem.fragments.browse.messages.ChatPeople;
import com.example.geem.fragments.browse.notifications.DummyTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MessageActivity extends AppCompatActivity
{
 
 private static final String TAG = "MessageActivity";
 
 private RecyclerView recyclerView;
 private AdapterMessages adapterMessages;
 
 private ImageView sendButton;
 private EditText typedText;
 private CircularImageView profilePicture;
 private TextView name;
 
 private int recyclerItemCounts = 0;
 
 
 //Messages things
 private static String MY_ID = "rahul";
 private static String OTHER_ID = "shiwank";
 private long lastTimestamp = 0;
 
 private List<MessageTemplateForAdapter> messageTemplatesForAdapterList = new ArrayList<>();
 
 //Firebase things
 private CollectionReference messageCollectionReference;
 private static final String MESSAGE_COLLECTION_NAME = "messages";
 private static final String PROFILE_COLLECTION_NAME = "dummy_profiles_do_not_delete";
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_message);
  
  
  if(checkLogin() && checkParameter())
  {
   MY_ID = "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
   OTHER_ID = getIntent().getStringExtra(Variables.OTHER_ID);
   Toast.makeText(getApplicationContext(), "Welcome : " + MY_ID, Toast.LENGTH_SHORT).show();
   Toast.makeText(getApplicationContext(), "MyId : " + MY_ID + ", OtherId : " + OTHER_ID, Toast.LENGTH_SHORT).show();
   Log.d(TAG, "onCreate: MY_ID : " + MY_ID + ", OTHER_ID : " + OTHER_ID);
   initializeComponents();
   getSupportActionBar().hide();
   setUserNamesAndProfilePicture();
  }
  else
  {
   try
   {
    Log.d(TAG, "onCreate: Exiting Firebase instance : " + FirebaseAuth.getInstance() + ", Current user : " + FirebaseAuth.getInstance().getCurrentUser() + ", Other id : " + getIntent().getStringExtra(Variables.OTHER_ID));
   }
   catch(Exception e)
   {
    e.printStackTrace();
   }
   finish();
  }
 }
 
 private void setUserNamesAndProfilePicture()
 {
  FirebaseFirestore.getInstance().collection(PROFILE_COLLECTION_NAME).document(OTHER_ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    //This thing can be replaced with better one, but due to lack of time and it's time for dinner, I am leaving with only this version :|
    DummyTemplate template = task.getResult().toObject(DummyTemplate.class);
    Glide.with(getApplicationContext()).load(template.getProfilePictureUrl()).placeholder(R.drawable.ic_tab_profile).error(R.drawable.profile_pic).into(profilePicture);
    name.setText(template.getName());
   }
  });
 }
 
 
 private boolean checkParameter()
 {
  return getIntent().getStringExtra(Variables.OTHER_ID) != null;
 }
 
 private boolean checkLogin()
 {
  return FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null;
 }
 
 
 private void init()
 {
  recyclerView = findViewById(R.id.recycler_view);
  typedText = findViewById(R.id.text_typed_message);
  sendButton = findViewById(R.id.button_send);
  profilePicture = findViewById(R.id.profile_picture);
  name = findViewById(R.id.name);
 }
 
 private void initializeFirebase()
 {
  if(checkLogin())//Firebase auth
  {
   messageCollectionReference = FirebaseFirestore.getInstance().collection(MESSAGE_COLLECTION_NAME);
   
   List<String> ids = new ArrayList<>();
   ids.add(MY_ID);
   ids.add(OTHER_ID);
   
   messageCollectionReference.orderBy("timestamp").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
   {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task)
    {
     if(task.isSuccessful())
     {
      Log.d(TAG, "onComplete: Fetch from collection, Success");
      messageTemplatesForAdapterList = new ArrayList<>();
      for(DocumentSnapshot snapshot : task.getResult())
      {
       MessageTemplate template = snapshot.toObject(MessageTemplate.class);
       
       boolean fromMySide = template.getMyId().equals(MY_ID) && template.getOtherId().equals(OTHER_ID);
       boolean toMySide = template.getMyId().equals(OTHER_ID) && template.getOtherId().equals(MY_ID);
       
       if((fromMySide || toMySide))
       {
        MessageTemplateForAdapter messageTemplateForAdapter = new MessageTemplateForAdapter(messageTemplatesForAdapterList.size(), template.getMyId().equals(MY_ID), template.getContent(), new TimeDetails(template.getTimestamp()));
        lastTimestamp = template.getTimestamp();
        //No need to store messages
        messageTemplatesForAdapterList.add(messageTemplateForAdapter);
        
        adapterMessages.insertItem(messageTemplateForAdapter);
        recyclerItemCounts++;
        recyclerView.scrollToPosition(adapterMessages.getMessagesSize() - 1);
        
        Log.d(TAG, "onComplete: Print item : " + template);
       }
      }
      
      
      //Here listen for new msg
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      //When all messages loaded then only listen for new messages
      
      
      messageCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>()
      {
       @Override
       public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
       {
        if(error != null)
        {
         Log.d(TAG, "onEvent: Error occured : " + error);
        }
        else
        {
         Log.d(TAG, "onEvent: Snapshotlistner called");
         for(DocumentChange documentChange : value.getDocumentChanges())
         {
          MessageTemplate template = documentChange.getDocument().toObject(MessageTemplate.class);
          
          boolean fromMySide = template.getMyId().equals(MY_ID) && template.getOtherId().equals(OTHER_ID);
          boolean toMySide = template.getMyId().equals(OTHER_ID) && template.getOtherId().equals(MY_ID);
          
          if(template.getTimestamp() > lastTimestamp && (fromMySide || toMySide))
          {
           lastTimestamp = template.getTimestamp();
           Log.d(TAG, "onEvent: Receieved msg : " + template);
           adapterMessages.insertItem(new MessageTemplateForAdapter(recyclerItemCounts - 1, template.getMyId().equals(MY_ID), template.getContent(), new TimeDetails(template.getTimestamp())));
           recyclerItemCounts++;
           recyclerView.scrollToPosition(adapterMessages.getMessagesSize() - 1);
          }
         }
        }
       }
      });
     }
     else
     {
      Log.d(TAG, "onComplete: Fetch from collection, Failed : " + task.getException());
     }
    }
   });
  }
  else
  {
   finish();
   //   Snackbar.make(getCurrentFocus(), "Not logged in", Snackbar.LENGTH_SHORT).show();
  }
 }
 
 
 private void setListeners()
 {
  sendButton.setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    if(typedText.getText() != null && typedText.getText().length() != 0)
    {
     String content = "" + typedText.getText();
     MessageTemplate messageTemplate = new MessageTemplate(new Date().getTime(), MY_ID, OTHER_ID, true, content);
     
     //adapterMessages.insertItem(new MessageTemplateForAdapter(recyclerItemCounts, messageTemplate.isSentByMe(), messageTemplate.getContent(), new TimeDetails(messageTemplate.getTimestamp())));
     
     
     messageCollectionReference.document().set(messageTemplate).addOnCompleteListener(new OnCompleteListener<Void>()
     {
      @Override
      public void onComplete(@NonNull Task<Void> task)
      {
       if(task.isSuccessful())
       {
        adapterMessages.setDelivered(recyclerItemCounts - 1);
        Log.d(TAG, "onComplete: Sending message success ==> " + messageTemplate);
       }
       else
       {
        Log.d(TAG, "onComplete: Sending message failed ==> " + messageTemplate + task.getException());
       }
      }
     });
     
     
     //adapterMessages.insertItem(new MessageTemplateForAdapter(recyclerItemCounts, true, typedText.getText() + "", new TimeDetails(new Date().getTime())));
     recyclerItemCounts++;
     recyclerView.scrollToPosition(adapterMessages.getMessagesSize());
     typedText.setText("");
    }
   }
  });
  
  
  findViewById(R.id.back_button_image).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    finish();
   }
  });
  
  findViewById(R.id.name).setOnClickListener(new View.OnClickListener()
  {
   @Override
   public void onClick(View view)
   {
    Intent intent = new Intent(getApplicationContext(), ActivityShowUserProfile.class);
    intent.putExtra(Variables.OTHER_ID, OTHER_ID);
    startActivity(intent);
   }
  });
 }
 
 
 private void initializeComponents()
 {
  init();
  setListeners();
  recyclerView.setLayoutManager(new LinearLayoutManager(this));
  recyclerView.setHasFixedSize(true);
  
  adapterMessages = new AdapterMessages(getApplicationContext(), messageTemplatesForAdapterList);
  initializeFirebase();
  recyclerView.setAdapter(adapterMessages);
  recyclerView.scrollToPosition(adapterMessages.getMessagesSize() - 1);
  
  profilePicture.setImageResource(R.drawable.ic_tab_history);
  
 }
 
 
 public static List<MessageTemplateForAdapter> getMessageList()
 {
  String[] messages = new String[]{
   "Ever find yourself staring at the ceiling? Or sitting on the toilet pondering about worldly issues? But sometimes, we drift off. Sometimes, there's just nothing to do or think about. That's when you get truly bored. It's a shitty feeling to behold. But it is something we all experience.", "So the next time you feel that way, perhaps you can think about these random silly things. I'm not saying that they'll help, but you're bored AF, so why not?", "We chase happiness but do not get it too often. Will we get it; if we stop chasing it?", "“Don’t Let Yesterday Take Up Too Much Of Today.” – Will Rogers", "4) “You Learn More From Failure Than From Success. Don’t Let It Stop You. Failure Builds Character.” – Unknown", "5) “It’s Not Whether You Get Knocked Down, It’s Whether You Get Up.” – Inspirational Quote By Vince Lombardi", "Throughout this chapter, we assumed that no two edges in the input graph\n" + "have equal weights, which implies that the minimum spanning tree is unique.\n" + "In fact, a weaker condition on the edge weights implies MST uniqueness.\n" + "(a) Describe an edge-weighted graph that has a unique minimum spanning\n" + "tree, even though two edges have equal weights.\n" + "(b) Prove that an edge-weighted graph G has a unique minimum spanning\n" + "tree if and only if the following conditions hold:\n" + "• For any partition of the vertices of G into two subsets, the minimum-\n" + "weight edge with one endpoint in each subset is unique.\n" + "• The maximum-weight edge in any cycle of G is unique.\n" + "(c) Describe and analyze an algorithm to determine whether or not a graph\n" + "has a unique minimum spanning tree.", "Most classical minimum-spanning-tree algorithms use the notions of “safe”\n" + "and “useless” edges described in the text, but there is an alternate formulation.\n" + "Let G be a weighted undirected graph, where the edge weights are distinct.\n" + "We say that an edge e is dangerous if it is the longest edge in some cycle\n" + "in G, and useful if it does not lie in any cycle in G.\n" + "(a) Prove that the minimum spanning tree of G contains every useful edge.\n" + "(b) Prove that the minimum spanning tree of G does not contain any\n" + "dangerous edge.\n" + "(c) Describe and analyze an efficient implementation of the following\n" + "algorithm, first described by Joseph Kruskal in the same\n" + "paper\n" + "where he proposed “Kruskal’s algorithm”. Examine the edges of G in\n" + "decreasing order; if an edge is dangerous, remo", "A feedback edge set of an undirected graph G is a subset F of the edges\n" + "such that every cycle in G contains at least one edge in F . In other\n" + "words, removing every edge in F makes the graph G acyclic. Describe\n" + "and analyze a fast algorithm to compute the minimum-weight feedback\n" + "edge set of a given edge-weighted graph.", "Minimum-spanning tree algorithms are often formulated using an operation\n" + "called edge contraction. To contract the edge uv, we insert a new node,\n" + "redirect any edge incident to u or v (except uv) to this new node, and then\n" + "delete u and v. After contraction, there may be multiple parallel edges\n" + "between the new node and other nodes in the graph; we remove all but the\n" + "lightest edge between any two nodes.\n" + "The three classical minimum-spanning tree algorithms described in this\n" + "chapter can all be expressed cleanly in terms of contraction as follows. All\n" + "three algorithms start by making a clean copy G 0 of the input graph G\n" + "and then repeatedly contract safe edges in G 0 ; the minimum spanning tree\n" + "consists of the contracted edge", "Discrete mathematics: High-school algebra, logarithm identities, naive\n" + "set theory, Boolean algebra, first-order predicate logic, sets, functions,\n" + "equivalences, partial orders, modular arithmetic, recursive definitions, trees\n" + "(as abstract objects, not data structures), graphs (vertices and edges, not\n" + "function plots).\n" + "• Proof techniques: direct, indirect, contradiction, exhaustive case analysis,\n" + "and induction (especially “strong” and “structural” induction). Chapter\n" + "uses induction, and whenever Chapter n 1 uses induction, so does Chapter n.\n" + "• Iterative programming concepts: variables, conditionals, loops, records,\n" + "indirection (addresses/pointers/references), subroutines, recursion. I do not\n" + "assume fluency in any particular programming language, but I do assume\n" + "experience with at least one language that supports both indirection and\n" + "recursion.\n" + "• Fundamental abstract data types: scalars, sequences, vectors, sets, stacks,\n" + "queues, maps/dictionaries, ordered maps/dictionaries, priority queues.\n" + "• Fundamental data structures: arrays, linked lists (single and double,\n" + "linear and circular), binary search trees, at least one form of balanced binary\n" + "search tree (such as AVL trees, red-black trees, treaps, skip lists, or splay\n" + "trees), hash tables, binary heaps, and most importantly, the difference\n" + "between this list and the previous list.\n" + "• Fundamental computational problems: elementary arithmetic, sorting,\n" + "searching, enumeration, tree traversal (preorder, inorder, postorder, level-\n" + "order, and so on).\n" + "• Fundamental algorithms: elementary algorism, sequential search, binary\n" + "search, sorting (selection, insertion, merge, heap, quick, radix, and so\n" + "on), breadth- and depth-first search in (at least binary) trees, and most\n" + "importantly, the difference between this list and the previous list.\n" + "• Elementary algorithm analysis: Asymptotic notation (o, O, ⇥, ⌦, !),\n" + "translating loops into sums and recursive calls into recurrences, evaluating\n" + "simple sums and recurrences.", "Additional", "References", "Reduction is the single most common technique used in designing algorithms", "Aesthetics", "Epistemology", "Ethics", "Philosophy of Science (Ed. Eran Asoulin): chapters include empiricism, Popper’s conjectures and\n" + "refutations; Kuhn’s normal and revolutionary science; the sociology of scientific knowledge;\n" + "feminism and the philosophy of science; the problem of induction; explanation"
  };
  
  //String[] messages = new String[]{"Hello", "What", "Are", "You", "Doing ?"};
  TimeDetails[] timeDetails = new TimeDetails[messages.length];
  boolean[] sentFromMe = new boolean[]{true, false};
  
  
  List<MessageTemplateForAdapter> messageTemplateForAdapterList = new ArrayList<>();
  Random random = new Random();
  
  for(int i = 0; i < 10; i++)
  {
   messageTemplateForAdapterList.add(new MessageTemplateForAdapter(i, random.nextBoolean(), messages[random.nextInt(messages.length)], new TimeDetails(1620_236_802_132L + random.nextInt(99000))));
  }
  return messageTemplateForAdapterList;
 }
}