package br.com.jonyfs.credit.card.api.model;

import java.util.Currency;

import javax.validation.constraints.NotNull;

public class Product extends BaseDocument<Long> {

	@NotNull
	String name;
	
	@NotNull
	Double price;
	
	@NotNull
	Currency currency;

	public Product(String name, Double price, Currency currency) {
		super();
		this.name = name;
		this.price = price;
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public Currency getCurrency() {
		return currency;
	}

}
