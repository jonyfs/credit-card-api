package br.com.jonyfs.credit.card.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.jonyfs.credit.card.api.model.CardType;
import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.model.Product;
import br.com.jonyfs.credit.card.api.model.Store;
import br.com.jonyfs.credit.card.api.repository.PaymentRepository;

@SpringBootApplication
public class CreditCardApiApplication {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(CreditCardApiApplication.class);


    @Autowired
    PaymentRepository paymentRepository;

    public static void main(String[] args) {
        SpringApplication.run(CreditCardApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        java.util.List<Product> products = new ArrayList<>();
        products.add(new Product("Bike", 115.50D));
        products.add(new Product("iPhone 6S", 199.80D));
        Date expirationDate = Calendar.getInstance().getTime();
        Payment payment = new Payment(CardType.AMERICAN_EXPRESS, "4485317326500091", expirationDate,
                new Store("Amazon"), products);

        paymentRepository.save(payment);

        payment = new Payment(CardType.VISA, "4485317326500090", expirationDate,
                new Store("BestBuy"), products);
        paymentRepository.save(payment);

        payment = new Payment(CardType.MASTERCARD, "4485317326500092", expirationDate,
                new Store("Amazon"), products);
        paymentRepository.save(payment);
        
        LOGGER.info("STARTED");
    }

}
