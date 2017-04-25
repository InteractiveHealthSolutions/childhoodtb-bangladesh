package com.ihsinformatics.childhoodtb_mobile.ChildhoodTbActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.ihsinformatics.childhoodtb_mobile.LoginActivity;
import com.ihsinformatics.childhoodtb_mobile.MainMenuActivity;

/**
 * Created by Shujaat on 4/21/2017.
 */
public class LoginSessionManager {

    private static LoginSessionManager instance;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    // Shared preference file name
    private static final String PREF_NAME = "UserAuthentication";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // User name
    public static final String KEY_NAME = "userName";
    // User Password
    public static final String KEY_PASS = "userPassword";

    ///Class constructor
    public LoginSessionManager(Context context) {
        this.context = context;
        this.pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //singleton constructor ...
    public static LoginSessionManager getInstance(Context context) {

        if (instance == null)
            instance = new LoginSessionManager(context);

        return instance;
    }

    // Create login session
    public void createLoginSession(String name, String password) {

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        // Storing email in pref
        editor.putString(KEY_PASS, password);
        // commit changes
        editor.commit();
    }

    //this method check whether the user logged in or not ....
    public void checkLogin() {

        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent intentLoggin = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            intentLoggin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            intentLoggin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            context.startActivity(intentLoggin);
        } else {

            Intent mainMenuActivity = new Intent(context, MainMenuActivity.class);
            mainMenuActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            mainMenuActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainMenuActivity);

        }

    }

    // Get Login State
    public boolean isLoggedIn() {

        return pref.getBoolean(IS_LOGIN, false);
    }

    // Clear session detail/user logout.
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.apply();
        // After logout redirect user to Login Activity
        Intent intetnLoggin = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        intetnLoggin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intetnLoggin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        context.startActivity(intetnLoggin);
    }

}
