package com.joshuahou.githubandroid.util;

import org.apache.http.auth.UsernamePasswordCredentials;

public class NetworkRequestParams {
    private NetworkRequestMethod method;
    private String path;
    private String submission;

    private UsernamePasswordCredentials credentials;

    public NetworkRequestParams(NetworkRequestMethod method, UsernamePasswordCredentials credentials) {
        this.method = method;
        this.credentials = credentials;
    }

    public NetworkRequestParams(NetworkRequestMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    public NetworkRequestParams(NetworkRequestMethod method, String path, String jsonSubmission) {
        this(method, path);
        this.submission = jsonSubmission;
    }

    public NetworkRequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getSubmission() {
        return submission;
    }

    public UsernamePasswordCredentials getCredentials() {
        return credentials;
    }
}
