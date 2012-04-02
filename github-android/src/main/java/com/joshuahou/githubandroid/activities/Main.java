package com.joshuahou.githubandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import com.joshuahou.githubandroid.R;


public class Main extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (!PreferenceManager.getDefaultSharedPreferences(this).contains("token")) {
            startActivity(logoutIntent());
        }
    }

    public void lookAtRepos(View v) {
        startActivity(new Intent(this, Repositories.class));
    }
    
    public void lookAtPullRequests(View v) {
        startActivity(new Intent(this, PullRequests.class));
    }
    
    public void logout(View v) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.remove("token");
        editor.commit();

        startActivity(logoutIntent());
    }
    
    private Intent logoutIntent() {
        Intent intent = new Intent(this, Signin.class);
        finish();
        return intent;
    }
}