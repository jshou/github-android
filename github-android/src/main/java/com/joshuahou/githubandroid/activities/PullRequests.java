package com.joshuahou.githubandroid.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.gson.JsonElement;
import com.joshuahou.githubandroid.network.NetworkRequest;
import com.joshuahou.githubandroid.network.NetworkRequestHandler;
import com.joshuahou.githubandroid.network.NetworkRequestMethod;
import com.joshuahou.githubandroid.network.NetworkRequestParams;

public class PullRequests extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkRequestParams params = new NetworkRequestParams(NetworkRequestMethod.GET, "/issues");
        NetworkRequest request = new NetworkRequest(this, "Loading pull requests...", new PullRequestsRequestHandler());
        request.execute(params);
    }

    private class PullRequestsRequestHandler extends NetworkRequestHandler {
        @Override
        public void onPostRequest(JsonElement response) {
        }
    }

    private class PullRequestListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}