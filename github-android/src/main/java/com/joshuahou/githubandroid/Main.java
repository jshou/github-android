package com.joshuahou.githubandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Main extends Activity {

    private static String TAG = "githubandroid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        signIn(null);
    }

    public void signIn(View button) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        username.setText("jshou");
        password.setText("uT0#lDmC^XVEcQF!TSa9L@2");

        if (username.getText().length() == 0) {
            Toast.makeText(Main.this, "Please enter your Github username!", Toast.LENGTH_SHORT).show();
        } else if (password.getText().length() == 0) {
            Toast.makeText(Main.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
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
                StringEntity entity = new StringEntity("{}");
                post.setEntity(entity);
                post.addHeader("Content-Type", "application/json");
                post.addHeader(new BasicScheme().authenticate(credentialsArray[0], post));

                String response = client.execute(host, post, new BasicResponseHandler());

                Log.i("SIGNIN", response);
                return "";
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

        private AndroidHttpClient addSsl(AndroidHttpClient client) {
            try {
                InputStream inputStream = getResources().openRawResource(R.raw.github_keystore);
                KeyStore keystore = KeyStore.getInstance("BKS");
                keystore.load(inputStream, "jammer".toCharArray());
                inputStream.close();

                SSLSocketFactory sf = new SSLSocketFactory(keystore);
                sf.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

                client.getConnectionManager().getSchemeRegistry().register(new Scheme("https", sf, 443));
                return client;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            return client;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}

