package br.com.jonyfs.credit.card.api.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(
                collection = "payments")
public class Payment extends BaseDocument<String> {

    @NotNull
    CardType      cardType;

    @NotNull
    @CreditCardNumber
    String        cardNumber;

    @NotNull
    Date          expirationDate;

    @NotNull
    Store         store;

    @NotEmpty
    List<Product> products;

    @JsonCreator
    public Payment(@JsonProperty("cardType") CardType cardType, @JsonProperty("cardNumber") String cardNumber, @JsonProperty("expirationDate") Date expirationDate, @JsonProperty("store") Store store,
                    @JsonProperty("products") List<Product> products) {
        super();
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.store = store;
        this.products = products;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Store getStore() {
        return store;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return String.format("Payment [cardType=%s, cardNumber=%s, expirationDate=%s, store=%s, products=%s, id=%s]", cardType, cardNumber, expirationDate, store, products, id);
    }
}
