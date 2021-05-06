package com.example.geem.fragments.browse.messages.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.geem.R;
import com.example.geem.extra.TimeDetails;
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
 
 private int recyclerItemCounts = 0;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_message);
  initializeComponents();
  getSupportActionBar().hide();
 }
 
 
 private void init()
 {
  recyclerView = findViewById(R.id.recycler_view);
  typedText = findViewById(R.id.text_typed_message);
  sendButton = findViewById(R.id.button_send);
  profilePicture = findViewById(R.id.profile_picture);
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
     adapterMessages.insertItem(new Message(recyclerItemCounts, true, typedText.getText() + "", new TimeDetails(new Date().getTime())));
     recyclerItemCounts++;
     recyclerView.scrollToPosition(recyclerItemCounts - 1);
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
  
  
 }
 
 
 private void initializeComponents()
 {
  init();
  setListeners();
  recyclerView.setLayoutManager(new LinearLayoutManager(this));
  recyclerView.setHasFixedSize(true);
  
  adapterMessages = new AdapterMessages(getApplicationContext(), getMessageList());
  recyclerView.setAdapter(adapterMessages);
  recyclerView.scrollToPosition(recyclerItemCounts - 1);
  
  profilePicture.setImageResource(R.drawable.ic_tab_history);
  
 }
 
 
 private List<Message> getMessageList()
 {
  String[] messages = new String[]{"Ever find yourself staring at the ceiling? Or sitting on the toilet pondering about worldly issues? But sometimes, we drift off. Sometimes, there's just nothing to do or think about. That's when you get truly bored. It's a shitty feeling to behold. But it is something we all experience.", "So the next time you feel that way, perhaps you can think about these random silly things. I'm not saying that they'll help, but you're bored AF, so why not?", "We chase happiness but do not get it too often. Will we get it; if we stop chasing it?", "“Don’t Let Yesterday Take Up Too Much Of Today.” – Will Rogers", "4) “You Learn More From Failure Than From Success. Don’t Let It Stop You. Failure Builds Character.” – Unknown", "5) “It’s Not Whether You Get Knocked Down, It’s Whether You Get Up.” – Inspirational Quote By Vince Lombardi", "Throughout this chapter, we assumed that no two edges in the input graph\n" + "have equal weights, which implies that the minimum spanning tree is unique.\n" + "In fact, a weaker condition on the edge weights implies MST uniqueness.\n" + "(a) Describe an edge-weighted graph that has a unique minimum spanning\n" + "tree, even though two edges have equal weights.\n" + "(b) Prove that an edge-weighted graph G has a unique minimum spanning\n" + "tree if and only if the following conditions hold:\n" + "• For any partition of the vertices of G into two subsets, the minimum-\n" + "weight edge with one endpoint in each subset is unique.\n" + "• The maximum-weight edge in any cycle of G is unique.\n" + "(c) Describe and analyze an algorithm to determine whether or not a graph\n" + "has a unique minimum spanning tree.", "Most classical minimum-spanning-tree algorithms use the notions of “safe”\n" + "and “useless” edges described in the text, but there is an alternate formulation.\n" + "Let G be a weighted undirected graph, where the edge weights are distinct.\n" + "We say that an edge e is dangerous if it is the longest edge in some cycle\n" + "in G, and useful if it does not lie in any cycle in G.\n" + "(a) Prove that the minimum spanning tree of G contains every useful edge.\n" + "(b) Prove that the minimum spanning tree of G does not contain any\n" + "dangerous edge.\n" + "(c) Describe and analyze an efficient implementation of the following\n" + "algorithm, first described by Joseph Kruskal in the same\n" + "paper\n" + "where he proposed “Kruskal’s algorithm”. Examine the edges of G in\n" + "decreasing order; if an edge is dangerous, remo", "A feedback edge set of an undirected graph G is a subset F of the edges\n" + "such that every cycle in G contains at least one edge in F . In other\n" + "words, removing every edge in F makes the graph G acyclic. Describe\n" + "and analyze a fast algorithm to compute the minimum-weight feedback\n" + "edge set of a given edge-weighted graph.", "Minimum-spanning tree algorithms are often formulated using an operation\n" + "called edge contraction. To contract the edge uv, we insert a new node,\n" + "redirect any edge incident to u or v (except uv) to this new node, and then\n" + "delete u and v. After contraction, there may be multiple parallel edges\n" + "between the new node and other nodes in the graph; we remove all but the\n" + "lightest edge between any two nodes.\n" + "The three classical minimum-spanning tree algorithms described in this\n" + "chapter can all be expressed cleanly in terms of contraction as follows. All\n" + "three algorithms start by making a clean copy G 0 of the input graph G\n" + "and then repeatedly contract safe edges in G 0 ; the minimum spanning tree\n" + "consists of the contracted edge", "Discrete mathematics: High-school algebra, logarithm identities, naive\n" + "set theory, Boolean algebra, first-order predicate logic, sets, functions,\n" + "equivalences, partial orders, modular arithmetic, recursive definitions, trees\n" + "(as abstract objects, not data structures), graphs (vertices and edges, not\n" + "function plots).\n" + "• Proof techniques: direct, indirect, contradiction, exhaustive case analysis,\n" + "and induction (especially “strong” and “structural” induction). Chapter\n" + "uses induction, and whenever Chapter n 1 uses induction, so does Chapter n.\n" + "• Iterative programming concepts: variables, conditionals, loops, records,\n" + "indirection (addresses/pointers/references), subroutines, recursion. I do not\n" + "assume fluency in any particular programming language, but I do assume\n" + "experience with at least one language that supports both indirection and\n" + "recursion.\n" + "• Fundamental abstract data types: scalars, sequences, vectors, sets, stacks,\n" + "queues, maps/dictionaries, ordered maps/dictionaries, priority queues.\n" + "• Fundamental data structures: arrays, linked lists (single and double,\n" + "linear and circular), binary search trees, at least one form of balanced binary\n" + "search tree (such as AVL trees, red-black trees, treaps, skip lists, or splay\n" + "trees), hash tables, binary heaps, and most importantly, the difference\n" + "between this list and the previous list.\n" + "• Fundamental computational problems: elementary arithmetic, sorting,\n" + "searching, enumeration, tree traversal (preorder, inorder, postorder, level-\n" + "order, and so on).\n" + "• Fundamental algorithms: elementary algorism, sequential search, binary\n" + "search, sorting (selection, insertion, merge, heap, quick, radix, and so\n" + "on), breadth- and depth-first search in (at least binary) trees, and most\n" + "importantly, the difference between this list and the previous list.\n" + "• Elementary algorithm analysis: Asymptotic notation (o, O, ⇥, ⌦, !),\n" + "translating loops into sums and recursive calls into recurrences, evaluating\n" + "simple sums and recurrences.", "Additional", "References", "Reduction is the single most common technique used in designing algorithms", "Aesthetics", "Epistemology", "Ethics", "Philosophy of Science (Ed. Eran Asoulin): chapters include empiricism, Popper’s conjectures and\n" + "refutations; Kuhn’s normal and revolutionary science; the sociology of scientific knowledge;\n" + "feminism and the philosophy of science; the problem of induction; explanation"};
  
  //String[] messages = new String[]{"Hello", "What", "Are", "You", "Doing ?"};
  TimeDetails[] timeDetails = new TimeDetails[messages.length];
  boolean[] sentFromMe = new boolean[]{true, false};
  
  
  List<Message> messageList = new ArrayList<>();
  Random random = new Random();
  
  for(int i = 0; i < messages.length; i++)
  {
   recyclerItemCounts++;
   messageList.add(new Message(i, random.nextBoolean(), messages[random.nextInt(messages.length)], new TimeDetails(1620_236_802_132L + random.nextInt(99000))));
  }
  return messageList;
 }
}