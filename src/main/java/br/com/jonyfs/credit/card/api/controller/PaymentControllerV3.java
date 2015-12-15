package br.com.jonyfs.credit.card.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonyfs.credit.card.api.exceptions.EntityNotFoundException;
import br.com.jonyfs.credit.card.api.exceptions.InvalidRequestException;
import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.service.PaymentService;
import br.com.jonyfs.credit.card.api.util.ResourcePaths;

@RestController
@RequestMapping(value = ResourcePaths.Payment.V3.ROOT)
public class PaymentControllerV3 {

    @Resource
    PaymentService paymentService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public HttpEntity<String> doPayment(@RequestBody @Valid Payment payment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid " + payment.getClass().getSimpleName(), bindingResult);
        }
        return new ResponseEntity<String>(paymentService.doPayment(payment).getId(), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = ResourcePaths.ID, method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public HttpEntity<org.springframework.hateoas.Resource<Payment>> getPayment(@PathVariable(value = "id") String id) {
        Payment entity = paymentService.getPayment(id);
        if (entity == null) {
            throw new EntityNotFoundException(String.valueOf(id));
        }
        org.springframework.hateoas.Resource<Payment> resource = new org.springframework.hateoas.Resource<Payment>(
                entity);

        resource.add(buildLink(ResourcePaths.ID, "get", id));

        return new ResponseEntity<org.springframework.hateoas.Resource<Payment>>(resource, HttpStatus.OK);
    }

    private Link buildLink(String path, String rel, String id) {
        return new Link(linkTo(getClass()).toUriComponentsBuilder().path(path).buildAndExpand(id).toUriString())
                .withRel(rel);
    }

}
