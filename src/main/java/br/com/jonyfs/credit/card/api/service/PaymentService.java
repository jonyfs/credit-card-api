package br.com.jonyfs.credit.card.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jonyfs.credit.card.api.model.Payment;

public interface PaymentService {

    public Payment doPayment(Payment payment);

    public Payment getPayment(String id);

    public Page<Payment> findAll(Pageable pageable);

}
