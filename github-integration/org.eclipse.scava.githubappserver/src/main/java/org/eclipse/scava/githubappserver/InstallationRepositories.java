package org.eclipse.scava.githubappserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstallationRepositories {
    private String action;
    private String repository_selection;
    private List<Repository> repositories_added;
    private List<Repository> repositories_removed;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRepository_selection() {
        return repository_selection;
    }

    public void setRepository_selection(String repository_selection) {
        this.repository_selection = repository_selection;
    }

    public List<Repository> getRepositories_added() {
        return repositories_added;
    }

    public void setRepositories_added(List<Repository> repositories_added) {
        this.repositories_added = repositories_added;
    }

    public List<Repository> getRepositories_removed() {
        return repositories_removed;
    }

    public void setRepositories_removed(List<Repository> repositories_removed) {
        this.repositories_removed = repositories_removed;
    }
}
