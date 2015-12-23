package br.com.jonyfs.credit.card.api.resource;

import org.springframework.hateoas.ResourceSupport;

public class IndexResource extends ResourceSupport {

    private final String name;
    private final String description;
    private final String createdBy;
    private final String linkedIn;

    public IndexResource(String name, String description, String createdBy, String linkedIn) {
        super();
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.linkedIn = linkedIn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}