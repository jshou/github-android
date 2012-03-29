package com.joshuahou.githubandroid.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.joshuahou.githubandroid.R;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Signin extends Activity {

    private static String TAG = "githubandroid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
    }

    public void signIn(View button) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        if (username.getText().length() == 0) {
            Toast.makeText(Signin.this, "Please enter your Github username!", Toast.LENGTH_SHORT).show();
        } else if (password.getText().length() == 0) {
            Toast.makeText(Signin.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
        } else {
            ProgressDialog dialog = ProgressDialog.show(this, "", "Loading...");
            new SigninTask(dialog).execute(new UsernamePasswordCredentials(username.getText().toString(), password.getText().toString()));
        }
    }

    private class SigninTask extends AsyncTask<UsernamePasswordCredentials, Float, String> {
        private ProgressDialog dialog;

        public SigninTask(ProgressDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        protected String doInBackground(final UsernamePasswordCredentials... credentialsArray) {
            try {
                DefaultHttpClient client = new DefaultHttpClient();

                HttpHost host = new HttpHost("api.github.com", 443, "https");
                HttpPost post = new HttpPost("/authorizations");
                StringEntity entity = new StringEntity("{\"scopes\": [\"repo\"], \"note\": \"android app\"}");
                post.setEntity(entity);
                post.addHeader("Content-Type", "application/json");
                post.addHeader(new BasicScheme().authenticate(credentialsArray[0], post));

                String response = client.execute(host, post, new BasicResponseHandler());

                Log.i("SIGNIN", response);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(response);
                JsonElement token = element.getAsJsonObject().get("token");
                return token.getAsString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return null;
            } finally {
                dialog.dismiss();
            }
        }

        @Override
        protected void onPostExecute(String token) {
            super.onPostExecute(token);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Signin.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", token);
            editor.commit();

            Intent intent = new Intent(Signin.this, Main.class);
            startActivity(intent);
        }
    }

}

