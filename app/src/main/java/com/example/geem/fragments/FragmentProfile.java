package com.example.geem.fragments;

import android.os.Bundle;

import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geem.R;
import com.example.geem.extra.Variables;

public class FragmentProfile extends Fragment
{
 ConstraintLayout loginFragment, registerFragment, passwordFragment;
 TextView loginRegister, loginForgotPassword;
 Button backToLoginFragment, password, registerGoBackToLogin, passwordGoBackToLogin;

 public final String TAG = "FragmentProfile";

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
 {
  View view = inflater.inflate(R.layout.fragment_profile, container, false);

  //Declare fragments
  loginFragment = view.findViewById(R.id.loginFragment);
  registerFragment = view.findViewById(R.id.registerFragment);
  passwordFragment = view.findViewById(R.id.passwordFragment);


  //Declare TextView
  loginRegister = view.findViewById(R.id.loginRegister);
  loginForgotPassword = view.findViewById(R.id.loginForgotPassword);

  //Declare Buttons
  registerGoBackToLogin = view.findViewById(R.id.registerGoBackToLogin);
  passwordGoBackToLogin = view.findViewById(R.id.passwordGoBackToLogin);


  //FragmentProfile fragmentProfile = new FragmentProfile();
  //FragmentTransaction transaction = getFragmentManager().beginTransaction();


  loginRegister.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    //RegisterFragment registerFragment = new RegisterFragment();
    //FragmentTransaction transaction = getFragmentManager().beginTransaction();
    //transaction.replace(R.id.register_fragment, new RegisterFragment());
    //transaction.commit();
    //Log.i(TAG, "Before Register");
    loginFragment.setVisibility(View.GONE);
    registerFragment.setVisibility(View.VISIBLE);
    passwordFragment.setVisibility(View.GONE);

    //fragmentProfile.addToBackStack();
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


  registerGoBackToLogin.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    loginFragment.setVisibility(View.VISIBLE);
    registerFragment.setVisibility(View.GONE);
    passwordFragment.setVisibility(View.GONE);
   }
  });

  passwordGoBackToLogin.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    loginFragment.setVisibility(View.VISIBLE);
    registerFragment.setVisibility(View.GONE);
    passwordFragment.setVisibility(View.GONE);
   }
  });


  //Toast.makeText(getContext(), getArguments().getString(Variables.GREETING_KEY), Toast.LENGTH_SHORT).show();
  return view;
 }
}