package com.example.geem.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geem.MainActivity;
import com.example.geem.R;
import com.example.geem.extra.Variables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class FragmentProfile extends Fragment implements View.OnClickListener
{
 //Related to Firebase
 private FirebaseAuth mAuth;
 FirebaseFirestore db;

 //Related to Login section
 ConstraintLayout loginFragment;
 TextView loginRegister, loginForgotPassword;
 Button loginSignIn;
 EditText loginEmail, loginPassword;
 ProgressBar loginProgressBar;

 //Related to Register section
 ConstraintLayout registerFragment;
 TextView registerBannerDescription;
 EditText registerFullName, registerBirthYear, registerEmail, registerPassword;
 Button registerRegisterUser, registerGoBackToLogin;
 ProgressBar registerProgressBar;

 //Related to ForgotPassword section
 ConstraintLayout passwordFragment;
 TextView passwordBanner, passwordBannerDescription;
 EditText passwordEmail;
 Button passwordResetPassword, passwordGoBackToLogin;
 ProgressBar passwordProgressBar;

 //Related to UserProfile section
 ConstraintLayout userProfile;
 TextView userProfileFullName, userProfileEmail, userProfileBirthYear;
 Button userProfileSignOut;

 //Related to User ID
 String currentUserID;

 //String for EditText Purpose
 private String temp;

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View view = inflater.inflate(R.layout.fragment_profile, container, false);

  //Declare fragments
  loginFragment = view.findViewById(R.id.loginFragment);
  registerFragment = view.findViewById(R.id.registerFragment);
  passwordFragment = view.findViewById(R.id.passwordFragment);
  userProfile = view.findViewById(R.id.userProfile);


  //Declare TextView
  loginRegister = view.findViewById(R.id.loginRegister);
  loginForgotPassword = view.findViewById(R.id.loginForgotPassword);
  registerBannerDescription = view.findViewById(R.id.registerBannerDescription);
  passwordBanner = view.findViewById(R.id.passwordBanner);
  passwordBannerDescription = view.findViewById(R.id.passwordBannerDescription);
  userProfileFullName = view.findViewById(R.id.userProfileFullName);
  userProfileEmail = view.findViewById(R.id.userProfileEmail);
  userProfileBirthYear = view.findViewById(R.id.userProfileBirthYear);


  //Declare EditText
  loginEmail = view.findViewById(R.id.loginEmail);
  loginPassword = view.findViewById(R.id.loginPassword);
  registerFullName = view.findViewById(R.id.registerFullName);
  registerBirthYear = view.findViewById(R.id.registerBirthYear);
  registerEmail = view.findViewById(R.id.registerEmail);
  registerPassword = view.findViewById(R.id.registerPassword);
  passwordEmail = view.findViewById(R.id.passwordEmail);


  //Declare Buttons
  registerGoBackToLogin = view.findViewById(R.id.registerGoBackToLogin);
  passwordGoBackToLogin = view.findViewById(R.id.passwordGoBackToLogin);
  loginSignIn = view.findViewById(R.id.loginSignIn);
  registerRegisterUser = view.findViewById(R.id.registerRegisterUser);
  passwordResetPassword = view.findViewById(R.id.passwordResetPassword);
  userProfileSignOut = view.findViewById(R.id.userProfileSignOut);

  //Declare ProgressBar
  loginProgressBar = view.findViewById(R.id.loginProgressBar);
  registerProgressBar = view.findViewById(R.id.registerProgressBar);
  passwordProgressBar = view.findViewById(R.id.passwordProgressBar);

  //Declare Firebase and FireStore
  mAuth = FirebaseAuth.getInstance();
  db = FirebaseFirestore.getInstance();

  //Declare String value (for overwriting EditText)
  temp = null;

  //onClickListeners in Login section
  loginRegister.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    loginFragment.setVisibility(View.GONE);
    registerFragment.setVisibility(View.VISIBLE);
    passwordFragment.setVisibility(View.GONE);
   }
  });

  loginForgotPassword.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    loginFragment.setVisibility(View.GONE);
    registerFragment.setVisibility(View.GONE);
    passwordFragment.setVisibility(View.VISIBLE);
   }
  });


  passwordGoBackToLogin.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    loginFragment.setVisibility(View.VISIBLE);
    registerFragment.setVisibility(View.GONE);
    passwordFragment.setVisibility(View.GONE);
    passwordEmail.setText(temp);
    passwordProgressBar.setVisibility(View.GONE);
   }
  });

  loginSignIn.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    userLogin();
   }
  });

  //onClickListeners in Register section
  registerRegisterUser.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    registerUser();
   }
  });

  registerGoBackToLogin.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    loginFragment.setVisibility(View.VISIBLE);
    registerFragment.setVisibility(View.GONE);
    passwordFragment.setVisibility(View.GONE);
   }
  });


  //onClickListeners in Password section
  passwordResetPassword.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    resetPassword();
   }
  });

  //onClickListeners in User Profile section
  userProfileSignOut.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    loginEmail.setText(temp);
    loginPassword.setText(temp);
    loginFragment.setVisibility(View.VISIBLE);
    registerFragment.setVisibility(View.GONE);
    passwordFragment.setVisibility(View.GONE);
    userProfile.setVisibility(View.GONE);
   }
  });





  //Toast.makeText(getContext(), getArguments().getString(Variables.GREETING_KEY), Toast.LENGTH_SHORT).show();
  return view;
 }

 @Override
 public void onClick(View v) {

 }

 //userLogin() starts
 private void userLogin() {
  String email = loginEmail.getText().toString().trim();
  String password = loginPassword.getText().toString().trim();

  if(email.isEmpty()){
   loginEmail.setError("Email is required!");
   loginEmail.requestFocus();
   return;
  }

  if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
   loginEmail.setError("Please enter a valid email!");
   loginEmail.requestFocus();
   return;
  }

  if(password.isEmpty()){
   loginPassword.setError("Password is required!");
   loginPassword.requestFocus();
   return;
  }

  if(password.length() < 6){
   loginPassword.setError("Minimum password length is 6 characters!");
   loginPassword.requestFocus();
   return;
  }

  loginProgressBar.setVisibility(View.VISIBLE);


  mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
   @Override
   public void onComplete(@NonNull Task<AuthResult> task) {

    if(task.isSuccessful()){
     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

     if(user.isEmailVerified()){
      //redirect to user profile
      loginProgressBar.setVisibility(View.GONE);
      loginFragment.setVisibility(View.GONE);
      registerFragment.setVisibility(View.GONE);
      passwordFragment.setVisibility(View.GONE);

      //Retrieve the logged in user information from the Cloud FireStore
      currentUserID = mAuth.getCurrentUser().getUid();
      DocumentReference documentReference = db.collection("profile1").document(currentUserID);
      documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
       @Override
       public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
        userProfileFullName.setText(value.getString("name"));
        userProfileEmail.setText(value.getString("email"));
        userProfileBirthYear.setText(value.getString("birthYear"));
       }
      });
      //Data Retrieval Completed

      userProfile.setVisibility(View.VISIBLE);
     }else{
      user.sendEmailVerification();
      Toast.makeText(getActivity(), "Check your email to verify your account!", Toast.LENGTH_LONG).show();
     }


    }else{
     Toast.makeText(getActivity(), "Failed to login! Please check your credentials!", Toast.LENGTH_LONG).show();
     loginProgressBar.setVisibility(View.GONE);
    }
   }
  });

 }
 //userLogin() ends

 //registerUser() starts
 private void registerUser() {
  String email = registerEmail.getText().toString().trim();
  String password = registerPassword.getText().toString().trim();
  String fullName = registerFullName.getText().toString().trim();
  String birthYear = registerBirthYear.getText().toString().trim();

  if(fullName.isEmpty()){
   registerFullName.setError("Full name is required!");
   registerFullName.requestFocus();
   return;
  }
  if(birthYear.isEmpty()){
   registerBirthYear.setError("Age is required!");
   registerBirthYear.requestFocus();
   return;
  }
  if(fullName.isEmpty()){
   registerEmail.setError("Email is required!");
   registerEmail.requestFocus();
   return;
  }
  if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
   registerEmail.setError("Please provide valid email!");
   registerEmail.requestFocus();
   return;
  }
  if(password.isEmpty()){
   registerPassword.setError("Password is required!");
   registerPassword.requestFocus();
   return;
  }
  if(password.length() < 6){
   registerPassword.setError("Minimum password length should be 6 characters!");
   registerPassword.requestFocus();
   return;
  }

  registerProgressBar.setVisibility(View.VISIBLE);
  mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
   @Override
   public void onComplete(@NonNull Task<AuthResult> task) {
    registerProgressBar.setVisibility(View.VISIBLE);
    if(task.isSuccessful()){
     loginEmail.setText(temp);
     loginPassword.setText(temp);
     Toast.makeText(getActivity(), "User has been registered successfully!", Toast.LENGTH_LONG).show();
     //Get userID of current user
     currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
     //currentUserID = mAuth.getCurrentUser().getUid();
     //DocumentReference documentReference = db.collection("profile1").document(currentUserID);
     insertData();
     registerProgressBar.setVisibility(View.GONE);
     loginFragment.setVisibility(View.VISIBLE);
     registerFragment.setVisibility(View.GONE);
     passwordFragment.setVisibility(View.GONE);
     registerFullName.setText(temp);
     registerBirthYear.setText(temp);
     registerEmail.setText(temp);
     registerPassword.setText(temp);
    }else{
     Toast.makeText(getActivity(), "Failed to register! Try again!", Toast.LENGTH_LONG).show();
     registerProgressBar.setVisibility(View.GONE);
    }
   }
  });


 }
 //registerUser() ends

 //resetPassword() starts
 private void resetPassword() {
  String email = passwordEmail.getText().toString().trim();

  if(email.isEmpty()){
   passwordEmail.setError("Email is required!");
   passwordEmail.requestFocus();
   return;
  }

  if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
   passwordEmail.setError("Please provide a valid email!");
   passwordEmail.requestFocus();
   return;
  }

  passwordProgressBar.setVisibility(View.VISIBLE);
  mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
   @Override
   public void onComplete(@NonNull Task<Void> task) {

    if(task.isSuccessful()){
     Toast.makeText(getActivity(), "Check your email to reset the password!", Toast.LENGTH_LONG).show();
    }else{
     Toast.makeText(getActivity(), "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
    }
   }
  });

 }
 //resetPassword() ends

 //insertData() starts
 public void insertData()
 {
  Map<String, String> items = new HashMap<>();
  items.put("name", registerFullName.getText().toString().trim());
  items.put("email", registerEmail.getText().toString().trim());
  items.put("birthYear", registerBirthYear.getText().toString());

  db.collection("profile1").document(currentUserID)
          .set(items)
          .addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
            Log.d(TAG, "DocumentSnapshot for user successfully written!");
            registerProgressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
           }
          })
          .addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
            Log.w(TAG, "Error writing document for user", e);
            Toast.makeText(getActivity(), "Failed to register! Try again!", Toast.LENGTH_LONG).show();
            registerProgressBar.setVisibility(View.GONE);
           }
          });
 }
 //insertData() ends

}