package br.com.jonyfs.credit.card.api.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    @NotNull
    String name;

    @NotNull
    Double price;

    @JsonCreator
    public Product(@JsonProperty("name") String name, @JsonProperty("price") Double price) {
        super();
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

}
