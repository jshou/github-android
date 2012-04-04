package com.joshuahou.githubandroid.test.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.joshuahou.githubandroid.data.Repository;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class RepositoryTest {

    @Test
    public void repositoryTest() {
        InputStream stream = getClass().getResourceAsStream("repositories.json");
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(new InputStreamReader(stream));
        List<Repository> repositories = Repository.createRepositories(element);

        Assert.assertEquals(repositories.size(), 1);

        Repository repo = repositories.get(0);
        Assert.assertEquals(repo.getName(), "octocat");
        Assert.assertEquals(repo.getOwner(), "Hello-World");
        Assert.assertEquals(repo.isPrivate(), false);
    }
}
