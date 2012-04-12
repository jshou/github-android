package com.joshuahou.githubandroid.data;

import android.test.AndroidTestCase;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Assert;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class RepositoryTest extends AndroidTestCase {

    public void testRepository() {
        InputStream stream = getClass().getResourceAsStream("com/joshuahou/githubandroid/data/repositories.json");
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
