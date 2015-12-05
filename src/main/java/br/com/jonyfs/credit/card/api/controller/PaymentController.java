package br.com.jonyfs.credit.card.api.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonyfs.credit.card.api.exceptions.InvalidRequestException;
import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.service.PaymentService;
import br.com.jonyfs.credit.card.api.util.ResourcePaths;

@RestController
@RequestMapping(value = ResourcePaths.Payment.ROOT)
public class PaymentController {

	@Resource
	PaymentService paymentService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Payment> doPayment(@RequestBody @Valid Payment payment, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestException("Invalid " + payment.getClass().getSimpleName(), bindingResult);
		}
		return new ResponseEntity<Payment>(paymentService.doPayment(payment), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = ResourcePaths.ID, method = RequestMethod.GET)
	public HttpEntity<Payment> getPayment(@PathParam(value = "id") String id) {
		return new ResponseEntity<Payment>(paymentService.getPayment(id), HttpStatus.OK);
	}
}
