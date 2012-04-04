package com.joshuahou.githubandroid.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class Repository implements Comparable<Repository> {
    private String name;
    private boolean privateRepo;
    private String owner;

    public Repository(JsonObject json) {
        this.name = json.get("name").getAsString();
        this.privateRepo = json.get("private").getAsBoolean();
        this.owner = json.get("owner").getAsJsonObject().get("login").getAsString();
    }
    
    public static List<Repository> createRepositories(JsonElement json){
        JsonArray jsonArray= json.getAsJsonArray();

        List<Repository> repositories = Lists.newArrayList();
        for (JsonElement e : jsonArray) {
            repositories.add(new Repository(e.getAsJsonObject()));
        }

        return repositories;
    }

    public String getName() {
        return name;
    }

    public boolean isPrivate() {
        return privateRepo;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public int compareTo(Repository other) {
        return name.compareTo(other.getName());
    }
}
