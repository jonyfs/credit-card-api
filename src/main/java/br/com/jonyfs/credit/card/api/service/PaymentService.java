package br.com.jonyfs.credit.card.api.service;

import br.com.jonyfs.credit.card.api.model.Payment;

public interface PaymentService {

	public Payment doPayment(Payment payment);

	public Payment getPayment(String id);

}
