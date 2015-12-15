package br.com.jonyfs.credit.card.api.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Store {

    @NotNull
    private String name;

    @JsonCreator
    public Store(@JsonProperty("name") String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Store [name=%s]", name);
    }

}
