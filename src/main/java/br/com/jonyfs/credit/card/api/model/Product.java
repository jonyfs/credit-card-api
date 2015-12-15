package br.com.jonyfs.credit.card.api.model;

import javax.validation.constraints.NotNull;

public class Product {

	@NotNull
	String name;

	@NotNull
	Double price;

	public Product(String name, Double price) {
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
