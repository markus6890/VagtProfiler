package com.gmail.markushygedombrowski.settings;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

public class GitHubService {
    private final GitHub gitHub;

    public GitHubService() throws IOException {
        gitHub = GitHub.connectAnonymously();
    }

    public boolean isRepoPrivate(String repoName) throws IOException {
        GHRepository repo = gitHub.getRepository(repoName);
        if(repo == null) {
            return true;
        }
        return repo.isPrivate();
    }


}
