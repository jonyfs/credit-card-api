package br.com.jonyfs.credit.card.api.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Resource
	PaymentRepository paymentRepository;

	@Override
	public Payment doPayment(Payment payment) {
		return paymentRepository.save(payment);

	}

	@Override
	public Payment getPayment(String id) {
		return paymentRepository.findOne(id);
	}
}
