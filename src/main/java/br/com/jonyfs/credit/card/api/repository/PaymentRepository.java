package br.com.jonyfs.credit.card.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.jonyfs.credit.card.api.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {

}
