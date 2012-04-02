package com.joshuahou.githubandroid.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class PullRequest {
    private String title;
    private String body;
    private String assigner;
    private String assignee;
    private String diff;
    private String repo;

    public PullRequest(JsonObject json) {
//        this.name = json.get("name").getAsString();
//        this.private_repo = json.get("private").getAsBoolean();
//        this.owner = json.get("owner").getAsJsonObject().get("login").getAsString();
    }

    public static List<PullRequest> createRepositories(JsonElement json){
        JsonArray jsonArray= json.getAsJsonArray();

        List<PullRequest> pullRequests = Lists.newArrayList();
        for (JsonElement e : jsonArray) {
            pullRequests.add(new PullRequest(e.getAsJsonObject()));
        }

        return pullRequests;
    }
}
