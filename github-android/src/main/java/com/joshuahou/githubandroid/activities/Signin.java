package com.joshuahou.githubandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.JsonElement;
import com.joshuahou.githubandroid.R;
import com.joshuahou.githubandroid.network.NetworkRequest;
import com.joshuahou.githubandroid.network.NetworkRequestHandler;
import com.joshuahou.githubandroid.network.NetworkRequestMethod;
import com.joshuahou.githubandroid.network.NetworkRequestParams;
import org.apache.http.auth.UsernamePasswordCredentials;

public class Signin extends Activity {
    private NetworkRequest authenticateRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (authenticateRequest != null) {
            authenticateRequest.kill();
        }
    }

    public void signIn(View button) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        if (username.getText().length() == 0) {
            Toast.makeText(Signin.this, "Please enter your Github username!", Toast.LENGTH_SHORT).show();
        } else if (password.getText().length() == 0) {
            Toast.makeText(Signin.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
        } else {
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username.getText().toString(), password.getText().toString());
            NetworkRequestParams params = new NetworkRequestParams(NetworkRequestMethod.AUTHENTICATE, credentials);
            authenticateRequest = new NetworkRequest(this, "Signing in...", new SigninRequestHandler());
            authenticateRequest.execute(params);
        }
    }

    private class SigninRequestHandler extends NetworkRequestHandler {
        @Override
        public void onPostRequest(JsonElement response) {
            String token = response.getAsJsonObject().get("token").getAsString();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Signin.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", token);
            editor.commit();

            startActivity(new Intent(Signin.this, Main.class));
            finish();
        }
    }
}

