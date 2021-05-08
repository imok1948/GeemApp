package com.example.geem.fragments.browse.messages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geem.R;
import com.example.geem.activities.MainActivity;
import com.example.geem.extra.TimeDetails;
import com.example.geem.fragments.browse.messages.activity.MessageTemplate;
import com.example.geem.fragments.browse.notifications.DummyTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FragmentBrowseMessages extends Fragment
{
 
 private static final String TAG = "FragmentBrowseMessages";
 private View root;
 
 //Ids
 private static String MY_ID = "rahul";
 private static String OTHER_ID = "shiwank";
 
 
 //View things
 private RecyclerView recyclerView;
 private AdapterListChatPeople adapterListChatPeople;
 
 
 //Firebase things
 private FirebaseFirestore firebaseFirestore;
 private CollectionReference messagesCollectionReference;
 private static final String MESSAGE_COLLECTION_NAME = "messages";
 private static final String PROFILE_COLLECTION_NAME = "dummy_profiles_do_not_delete";
 
 //Adapter things
 List<ChatPeople> peopleList = new ArrayList<>();
 
 
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  root = inflater.inflate(R.layout.fragment_browse_messages, container, false);
  
  if(((MainActivity) getActivity()).checkLoggedIn())
  {
   initializeComponents();
   initFirebase();
  }
  else
  {
   ((MainActivity) getActivity()).goToLoginFragment();
  }
  return root;
 }
 
 
 private void initFirebase()
 {
  
  messagesCollectionReference.orderBy(VariablesForFirebase.TIMESTAMP, Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task)
   {
    if(task.isSuccessful())
    {
     for(DocumentSnapshot snapshot : task.getResult())
     {
      MessageTemplate template = snapshot.toObject(MessageTemplate.class);
      Log.d(TAG, "onComplete: Fetched Message : " + template);
      
      getProfileDetailsAndAddToAdapter(template);
      
     }
    }
    else
    {
     Log.d(TAG, "onComplete: fetched from firestore ==> Failed");
    }
   }
  });
 }
 
 private void getProfileDetailsAndAddToAdapter(MessageTemplate template)
 {
  FirebaseFirestore.getInstance().collection(PROFILE_COLLECTION_NAME).document(template.getOtherId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
  {
   @Override
   public void onComplete(@NonNull Task<DocumentSnapshot> task)
   {
    DummyTemplate profileTemplate = task.getResult().toObject(DummyTemplate.class);
    adapterListChatPeople.addItem(new ChatPeople(profileTemplate.getProfilePictureUrl(), profileTemplate.getName(), template.getContent(), new TimeDetails(template.getTimestamp())));
   }
  });
  
 }
 
 
 private void initializeComponents()
 {
  firebaseFirestore = FirebaseFirestore.getInstance();
  messagesCollectionReference = firebaseFirestore.collection(MESSAGE_COLLECTION_NAME);
  
  recyclerView = root.findViewById(R.id.recyclerViewForMessagesList);
  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  //recyclerView.setHasFixedSize(true);
  
  adapterListChatPeople = new AdapterListChatPeople(getActivity());
  recyclerView.setAdapter(adapterListChatPeople);
  
  /*for(ChatPeople people : getRandomChats())
  {
   adapterListChatPeople.addItem(people);
  }
  */
 }
 
 private List<ChatPeople> getRandomChats()
 {
  String[] messages = new String[]{
   "Ever find yourself staring at the ceiling? Or sitting on the toilet pondering about worldly issues? But sometimes, we drift off. Sometimes, there's just nothing to do or think about. That's when you get truly bored. It's a shitty feeling to behold. But it is something we all experience.", "So the next time you feel that way, perhaps you can think about these random silly things. I'm not saying that they'll help, but you're bored AF, so why not?", "We chase happiness but do not get it too often. Will we get it; if we stop chasing it?", "“Don’t Let Yesterday Take Up Too Much Of Today.” – Will Rogers", "4) “You Learn More From Failure Than From Success. Don’t Let It Stop You. Failure Builds Character.” – Unknown", "5) “It’s Not Whether You Get Knocked Down, It’s Whether You Get Up.” – Inspirational Quote By Vince Lombardi", "Throughout this chapter, we assumed that no two edges in the input graph\n" + "have equal weights, which implies that the minimum spanning tree is unique.\n" + "In fact, a weaker condition on the edge weights implies MST uniqueness.\n" + "(a) Describe an edge-weighted graph that has a unique minimum spanning\n" + "tree, even though two edges have equal weights.\n" + "(b) Prove that an edge-weighted graph G has a unique minimum spanning\n" + "tree if and only if the following conditions hold:\n" + "• For any partition of the vertices of G into two subsets, the minimum-\n" + "weight edge with one endpoint in each subset is unique.\n" + "• The maximum-weight edge in any cycle of G is unique.\n" + "(c) Describe and analyze an algorithm to determine whether or not a graph\n" + "has a unique minimum spanning tree.", "Most classical minimum-spanning-tree algorithms use the notions of “safe”\n" + "and “useless” edges described in the text, but there is an alternate formulation.\n" + "Let G be a weighted undirected graph, where the edge weights are distinct.\n" + "We say that an edge e is dangerous if it is the longest edge in some cycle\n" + "in G, and useful if it does not lie in any cycle in G.\n" + "(a) Prove that the minimum spanning tree of G contains every useful edge.\n" + "(b) Prove that the minimum spanning tree of G does not contain any\n" + "dangerous edge.\n" + "(c) Describe and analyze an efficient implementation of the following\n" + "algorithm, first described by Joseph Kruskal in the same\n" + "paper\n" + "where he proposed “Kruskal’s algorithm”. Examine the edges of G in\n" + "decreasing order; if an edge is dangerous, remo", "A feedback edge set of an undirected graph G is a subset F of the edges\n" + "such that every cycle in G contains at least one edge in F . In other\n" + "words, removing every edge in F makes the graph G acyclic. Describe\n" + "and analyze a fast algorithm to compute the minimum-weight feedback\n" + "edge set of a given edge-weighted graph.", "Minimum-spanning tree algorithms are often formulated using an operation\n" + "called edge contraction. To contract the edge uv, we insert a new node,\n" + "redirect any edge incident to u or v (except uv) to this new node, and then\n" + "delete u and v. After contraction, there may be multiple parallel edges\n" + "between the new node and other nodes in the graph; we remove all but the\n" + "lightest edge between any two nodes.\n" + "The three classical minimum-spanning tree algorithms described in this\n" + "chapter can all be expressed cleanly in terms of contraction as follows. All\n" + "three algorithms start by making a clean copy G 0 of the input graph G\n" + "and then repeatedly contract safe edges in G 0 ; the minimum spanning tree\n" + "consists of the contracted edge", "Discrete mathematics: High-school algebra, logarithm identities, naive\n" + "set theory, Boolean algebra, first-order predicate logic, sets, functions,\n" + "equivalences, partial orders, modular arithmetic, recursive definitions, trees\n" + "(as abstract objects, not data structures), graphs (vertices and edges, not\n" + "function plots).\n" + "• Proof techniques: direct, indirect, contradiction, exhaustive case analysis,\n" + "and induction (especially “strong” and “structural” induction). Chapter\n" + "uses induction, and whenever Chapter n 1 uses induction, so does Chapter n.\n" + "• Iterative programming concepts: variables, conditionals, loops, records,\n" + "indirection (addresses/pointers/references), subroutines, recursion. I do not\n" + "assume fluency in any particular programming language, but I do assume\n" + "experience with at least one language that supports both indirection and\n" + "recursion.\n" + "• Fundamental abstract data types: scalars, sequences, vectors, sets, stacks,\n" + "queues, maps/dictionaries, ordered maps/dictionaries, priority queues.\n" + "• Fundamental data structures: arrays, linked lists (single and double,\n" + "linear and circular), binary search trees, at least one form of balanced binary\n" + "search tree (such as AVL trees, red-black trees, treaps, skip lists, or splay\n" + "trees), hash tables, binary heaps, and most importantly, the difference\n" + "between this list and the previous list.\n" + "• Fundamental computational problems: elementary arithmetic, sorting,\n" + "searching, enumeration, tree traversal (preorder, inorder, postorder, level-\n" + "order, and so on).\n" + "• Fundamental algorithms: elementary algorism, sequential search, binary\n" + "search, sorting (selection, insertion, merge, heap, quick, radix, and so\n" + "on), breadth- and depth-first search in (at least binary) trees, and most\n" + "importantly, the difference between this list and the previous list.\n" + "• Elementary algorithm analysis: Asymptotic notation (o, O, ⇥, ⌦, !),\n" + "translating loops into sums and recursive calls into recurrences, evaluating\n" + "simple sums and recurrences.", "Additional", "References", "Reduction is the single most common technique used in designing algorithms", "Aesthetics", "Epistemology", "Ethics", "Philosophy of Science (Ed. Eran Asoulin): chapters include empiricism, Popper’s conjectures and\n" + "refutations; Kuhn’s normal and revolutionary science; the sociology of scientific knowledge;\n" + "feminism and the philosophy of science; the problem of induction; explanation"
  };
  String[] names = new String[]{"Iyer", "Venugopal Iyer", "Muttuswami Venugopal Iyer", "Chinnaswami Muttuswami Venugopal Iyer", "Perambdur Chinnaswami Muttuswami Venugopal Iyer", "Yekeparampir Perambdur Chinnaswami Muttuswami Venugopal Iyer", "Trichipalli Yekeparampir Perambdur Chinnaswami Muttuswami Venugopal Iyer"};
  String[] profilePictureLinks = new String[]{
   "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F1.jpg?alt=media&token=55902603-49ef-4997-a2a5-68a4a59bdb26", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F2.jpg?alt=media&token=36b72bdf-f67f-482c-8f64-6b97a1381d82", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F3.jpg?alt=media&token=6d7df674-cada-4c9d-be72-11c82846d7f3", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F4.jpg?alt=media&token=f3906ed8-d971-45c2-b3a3-42c8f3e69a86", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F5.jpg?alt=media&token=00ce602a-6ab3-4522-8d01-54037db3834b", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F6.jpg?alt=media&token=7c5c9841-46fa-4ce0-93d9-3c3ae2c0ba42", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F7.jpg?alt=media&token=f314b274-0b1e-4ecf-9975-a3053d2ce12f", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F8.jpg?alt=media&token=f25258ff-729a-4f87-9564-c5a243a24224", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2F9.jpg?alt=media&token=e8731de8-71aa-4348-95ad-e026d2035830", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2FScreenshot%20from%202020-12-23%2009-53-51.png?alt=media&token=cc99babb-dfa0-47e6-be96-d3364975d2b5", "https://firebasestorage.googleapis.com/v0/b/iiitd-geemapp.appspot.com/o/profile_pictures_in_1%3A1_aspect_ratio_do_not_delete%2FScreenshot%20from%202021-03-16%2011-56-22.png?alt=media&token=4118bb3d-d851-48d0-9038-6c0ad44a2d99"
  };
  
  Random random = new Random();
  
  List<ChatPeople> chatPeopleList = new ArrayList<>();
  
  for(int i = 0; i < 5; i++)
  {
   //profilePictureLinks[random.nextInt(profilePictureLinks.length)]
   chatPeopleList.add(new ChatPeople(null, names[random.nextInt(names.length)], messages[random.nextInt(messages.length)], new TimeDetails(162_021_812_162_1L + random.nextInt(Integer.MAX_VALUE))));
  }
  return chatPeopleList;
 }
 
}


