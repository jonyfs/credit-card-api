package br.com.jonyfs.credit.card.api.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }
}
