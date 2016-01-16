package br.com.jonyfs.credit.card.api.resource;

import org.springframework.hateoas.ResourceSupport;

public class IndexResource extends ResourceSupport {

    private final String name;
    private final String description;
    private final String createdBy;
    private final String linkedIn;
    private final String github;

    public IndexResource(String name, String description, String createdBy, String linkedIn, String github) {
        super();
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.linkedIn = linkedIn;
        this.github = github;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDescription() {
        return description;
    }

    public String getGithub() {
        return github;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public String getName() {
        return name;
    }
}