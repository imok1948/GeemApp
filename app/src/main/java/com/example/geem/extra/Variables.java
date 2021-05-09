package com.example.geem.extra;

import com.example.geem.R;
import com.google.firebase.auth.FirebaseAuth;

public class Variables
{
 //To show error globally, will be used very rarely
 public static final String ERROR_MSG = "Error Occured";
 
 //Greeting msg to pass via bundles
 public static final String GREETING_KEY = "KEY_GREETING";
 
 //Tab names of fragments
 public static final String TAB_NAMES[] = {"Profile", "Notifications", "Messages", "Feds", "Add Items", "History"};
 
 //Tab icons of fragments
 public static final int TAB_ICONS[] = {R.drawable.ic_tab_messages, R.drawable.ic_tab_notification, R.drawable.ic_tab_feeds, R.drawable.ic_tab_additem, R.drawable.ic_tab_history};
 //R.drawable.ic_tab_profile, ,
 
 
 public static final String ITEM_ID = "ITEM_ID";
 public static final String OTHER_ID = "OTHER_ID";
 public static final String OWNER_ID = "OWNER_ID";
 
 
 public static final String MESSAGE_COLLECTION_NAME = "messages";
 public static final String PROFILE_COLLECTION_NAME = "dummy_profiles_do_not_delete";
 public static final String FEEDS_COLLECTION_NAME = "fetch_items_final";
 public static final String NOTIFICATIONS_COLLECTION_NAME = "notifications";
 public static final String NOTIFICATION_TYPE_REQUEST = "request";
 public static final String NOTIFICATION_TYPE_RESPONSE = "response";
 public static final String NEW_FEEDS_COLLECTION_NAME = "fetch_items_final";
 public static final String REVIEW_COLLECTION_NAME = "reviews";
 public static final String NOTIFICATION_RATING_REQUEST = "rating";
 
 public static final String NOTIFICATION_TYPE_FOR_PASSING_ACTIVITY = "NOTIFICATION_TYPE";
 public static final String NOTIFICATION_ID_FOR_PASSING_ACTIVITY = "NOTIFICATION_ID";
 
 
 public static final String getMyId() throws Exception
 {
  return FirebaseAuth.getInstance().getCurrentUser().getUid();
 }
 
 public static boolean isLoggedIn()
 {
  return FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().getUid() != null;
 }
}
