package com.joshuahou.githubandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import com.joshuahou.githubandroid.R;


public class Main extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (!PreferenceManager.getDefaultSharedPreferences(this).contains("token")) {
            startActivity(new Intent(this, Signin.class));
        }
    }

    public void lookAtRepos(View v) {
        startActivity(new Intent(this, Repositories.class));
    }
}