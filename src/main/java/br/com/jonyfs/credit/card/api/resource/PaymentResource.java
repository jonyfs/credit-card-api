package br.com.jonyfs.credit.card.api.resource;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.jonyfs.credit.card.api.model.CardType;
import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.model.Product;
import br.com.jonyfs.credit.card.api.model.Store;

@Relation(value = "payment", collectionRelation = "payments")
public class PaymentResource extends ResourceSupport {

    private Payment payment;

    @JsonCreator
    public PaymentResource(@JsonProperty("cardType") CardType cardType, @JsonProperty("cardNumber") String cardNumber, @JsonProperty("expirationDate") Date expirationDate,
                    @JsonProperty("store") Store store, @JsonProperty("products") List<Product> products) {
        super();
        payment = new Payment(cardType, cardNumber, expirationDate, store, products);
    }

    public PaymentResource(Payment payment) {
        super();
        this.payment = payment;
    }

    @JsonProperty("id")
    public String getPaymentId() {
        return payment.getId();
    }

    public CardType getCardType() {
        return payment.getCardType();
    }

    public String getCardNumber() {
        return payment.getCardNumber();
    }

    public Date getExpirationDate() {
        return payment.getExpirationDate();
    }

    public Store getStore() {
        return payment.getStore();
    }

    public List<Product> getProducts() {
        return payment.getProducts();
    }

    @JsonIgnore
    public Payment getPayment() {
        return payment;
    }

}
