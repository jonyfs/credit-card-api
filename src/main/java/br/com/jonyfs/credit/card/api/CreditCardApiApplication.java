package br.com.jonyfs.credit.card.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import br.com.jonyfs.credit.card.api.model.CardType;
import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.model.Product;
import br.com.jonyfs.credit.card.api.model.Store;
import br.com.jonyfs.credit.card.api.repository.PaymentRepository;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.jonyfs")
public class CreditCardApiApplication {

	@Resource
	PaymentRepository paymentRepository;

	public static void main(String[] args) {
		SpringApplication.run(CreditCardApiApplication.class, args);
	}

	@PostConstruct
	public void init() {
		java.util.List<Product> products = new ArrayList<>();
		products.add(new Product("Bike", 115.50D, Currency.getInstance("USD")));
		products.add(new Product("iPhone 6S", 199.80D, Currency.getInstance("USD")));
		Date expirationDate = Calendar.getInstance().getTime();
		Payment payment = new Payment(CardType.AMERICAN_EXPRESS, "4485317326500091", expirationDate,
				new Store("Amazon"), products);
		//paymentRepository.save(payment);
	}
}
