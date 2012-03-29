package com.joshuahou.githubandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Network {
    private DefaultHttpClient client;
    private HttpHost host;
    private Context context;
    private String token;
    
    public Network(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.token = preferences.getString("token", "");

        this.client = new DefaultHttpClient();
        this.host = new HttpHost("api.github.com", 443, "https");
        this.context = context;
    }

    public JsonElement get(String path) {
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

    public JsonElement post(String path, String json) {
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
