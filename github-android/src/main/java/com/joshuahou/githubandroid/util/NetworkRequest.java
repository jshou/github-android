package com.joshuahou.githubandroid.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NetworkRequest extends AsyncTask<NetworkRequestParams, Float, JsonElement> {
    private DefaultHttpClient client;
    private HttpHost host;
    private String token;
    private NetworkRequestHandler handler;
    private Context context;
    private ProgressDialog dialog;
    private String loadingMessage;

    public NetworkRequest(Context context, String loadingMessage, NetworkRequestHandler handler) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.token = preferences.getString("token", "");

        this.client = new DefaultHttpClient();
        this.host = new HttpHost("api.github.com", 443, "https");
        this.handler = handler;
        this.context = context;
        this.loadingMessage = loadingMessage;
    }

    public void kill() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "", loadingMessage);
    }

    @Override
    protected JsonElement doInBackground(NetworkRequestParams... paramsArray) {
        NetworkRequestParams params = paramsArray[0];
        switch (params.getMethod()) {
            case GET:
                return get(params.getPath());
            case POST:
                return post(params.getPath(), params.getSubmission());
            case AUTHENTICATE:
                return authenticate(params.getCredentials());
            default:
                return null;
        }
    }

    @Override
    protected void onPostExecute(JsonElement response) {
        super.onPostExecute(response);
        dialog.dismiss();
        handler.onPostRequest(response);
    }

    private JsonElement authenticate(UsernamePasswordCredentials credentials) {
        try {
            HttpPost post = new HttpPost("/authorizations");
            StringEntity entity = new StringEntity("{\"scopes\": [\"repo\"], \"note\": \"android app\"}");
            post.setEntity(entity);
            post.addHeader("Content-Type", "application/json");
            post.addHeader(new BasicScheme().authenticate(credentials, post));
            String response = client.execute(host, post, new BasicResponseHandler());

            JsonParser parser = new JsonParser();
            return parser.parse(response);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private JsonElement get(String path) {
        try {
            HttpGet get = new HttpGet(path);
            get.addHeader("Content-Type", "application/json");
            get.setHeader("Authorization", "token " + token);

            String response = client.execute(host, get, new BasicResponseHandler());

            JsonParser parser = new JsonParser();
            return parser.parse(response);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private JsonElement post(String path, String json) {
        try {
            HttpPost post = new HttpPost(path);
            post.setEntity(new StringEntity(json));

            post.addHeader("Content-Type", "application/json");
            post.setHeader("Authorization", "token " + token);

            String response = client.execute(host, post, new BasicResponseHandler());

            JsonParser parser = new JsonParser();
            return parser.parse(response);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
