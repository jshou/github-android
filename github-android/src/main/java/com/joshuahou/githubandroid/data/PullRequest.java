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
    private String diffUrl;
    private String repo;

    public PullRequest(JsonObject json) {
        this.title = json.get("title").getAsString();
        this.body = json.get("body").getAsString();
        this.assigner = json.get("user").getAsJsonObject().get("login").getAsString();
        this.assignee = json.get("assignee").getAsJsonObject().get("login").getAsString();

        this.diffUrl = json.get("pull_request").getAsJsonObject().get("diff_url").getAsString();
        this.repo = diffUrl.replaceAll("/([A-Za-z0-9]+)/issues", "$1");
    }

    public static List<PullRequest> createPullRequests(JsonElement json) {
        JsonArray jsonArray = json.getAsJsonArray();

        List<PullRequest> pullRequests = Lists.newArrayList();
        for (JsonElement e : jsonArray) {
            pullRequests.add(new PullRequest(e.getAsJsonObject()));
        }

        return pullRequests;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAssigner() {
        return assigner;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public String getRepo() {
        return repo;
    }
}
