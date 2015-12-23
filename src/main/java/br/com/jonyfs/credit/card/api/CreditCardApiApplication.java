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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import br.com.jonyfs.credit.card.api.model.CardType;
import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.model.Product;
import br.com.jonyfs.credit.card.api.model.Store;
import br.com.jonyfs.credit.card.api.repository.PaymentRepository;

@SpringBootApplication
@PropertySource(
                value = "classpath:application.properties")
public class CreditCardApiApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(CreditCardApiApplication.class);

    @Autowired
    PaymentRepository          paymentRepository;

    // To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args) {
        SpringApplication.run(CreditCardApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        java.util.List<Product> list1 = new ArrayList<>();
        list1.add(new Product("Bike", 115.50D));
        list1.add(new Product("iPhone 6S", 199.80D));

        java.util.List<Product> list2 = new ArrayList<>();
        list2.add(new Product("Nexus 9", 327.50D));
        list2.add(new Product("Motorola Moto X 2014", 112.90D));
        list2.add(new Product("iPhone 6S", 199.80D));

        java.util.List<Product> list3 = new ArrayList<>();
        list3.add(new Product("Nexus 9", 327.50D));
        list3.add(new Product("Nexus 9", 327.50D));
        list3.add(new Product("Motorola Moto X 2014", 199.80D));
        list3.add(new Product("iPhone 6S", 199.80D));

        Date expirationDate = Calendar.getInstance().getTime();
        Payment payment = new Payment(CardType.AMERICAN_EXPRESS, "4485317326500091", expirationDate, new Store("Walmart"), list1);

        paymentRepository.save(payment);

        payment = new Payment(CardType.VISA, "4485317326500090", expirationDate, new Store("BestBuy"), list2);
        paymentRepository.save(payment);

        payment = new Payment(CardType.MASTERCARD, "4485317326500092", expirationDate, new Store("Amazon"), list3);
        paymentRepository.save(payment);

        LOGGER.info("STARTED");
    }

}
