package com.joshuahou.githubandroid.network;

import com.google.gson.JsonElement;

public abstract class NetworkRequestHandler {
    public abstract void onPostRequest(JsonElement response);
}
