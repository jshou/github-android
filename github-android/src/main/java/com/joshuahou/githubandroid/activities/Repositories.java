package com.joshuahou.githubandroid.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.JsonElement;
import com.joshuahou.githubandroid.R;
import com.joshuahou.githubandroid.data.Repository;
import com.joshuahou.githubandroid.util.NetworkRequest;
import com.joshuahou.githubandroid.util.NetworkRequestHandler;
import com.joshuahou.githubandroid.util.NetworkRequestMethod;
import com.joshuahou.githubandroid.util.NetworkRequestParams;

import java.util.Collections;
import java.util.List;


public class Repositories extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repositories);

        NetworkRequestParams params = new NetworkRequestParams(NetworkRequestMethod.GET, "/user/repos");
        new NetworkRequest(this, "Loading repositories...", new RepositoriesRequestHandler()).execute(params);
        new RepositoriesRequestHandler();
    }

    private class RepositoriesRequestHandler extends NetworkRequestHandler {
        @Override
        public void onPostRequest(JsonElement response) {
            List<Repository> repositories = Repository.createRepositories(response);
            Collections.sort(repositories);

            ListView listView = (ListView) findViewById(R.id.repository_list);
            listView.setAdapter(new RepositoriesListAdapter(Repositories.this, repositories));
        }
    }

    private class RepositoriesListAdapter extends BaseAdapter implements ListAdapter {

        private Context context;
        private List<Repository> repositories;

        public RepositoriesListAdapter(Context context, List<Repository> repositories) {
            this.context = context;
            this.repositories = repositories;
        }
        @Override
        public int getCount() {
            return repositories.size();
        }

        @Override
        public Object getItem(int i) {
            return repositories.get(i);
        }

        @Override
        public long getItemId(int i) {
            return repositories.get(i).hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            if (view == null) {
                textView = new TextView(context);
            } else {
                textView = (TextView) view;
            }

            textView.setText(repositories.get(i).getName());
            return textView;
        }
    }

}