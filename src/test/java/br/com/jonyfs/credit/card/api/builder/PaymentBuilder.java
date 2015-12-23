package br.com.jonyfs.credit.card.api.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.jonyfs.credit.card.api.model.CardType;
import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.model.Product;
import br.com.jonyfs.credit.card.api.model.Store;

public class PaymentBuilder {

    private CardType      cardType;
    private String        cardNumber;
    private Date          expirationDate;
    private Store         store;
    private List<Product> products;

    public Payment build() {
        return new Payment(cardType, cardNumber, expirationDate, store, products);
    }

    public PaymentBuilder with(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public PaymentBuilder with(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public PaymentBuilder with(Store store) {
        this.store = store;
        return this;
    }

    public PaymentBuilder with(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public PaymentBuilder with(Product product) {
        if (products == null) {
            products = new ArrayList<Product>();
        }
        this.products.add(product);
        return this;
    }

}
