package br.com.jonyfs.credit.card.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

import br.com.jonyfs.credit.card.api.controller.PaymentControllerV3;
import br.com.jonyfs.credit.card.api.model.Payment;

@Service
public class PaymentResourceAssemblerV3 extends RepresentationModelAssemblerSupport<Payment, PaymentResource> {

    @Autowired
    EntityLinks entityLinks;

    public PaymentResourceAssemblerV3() {
        super(PaymentControllerV3.class, PaymentResource.class);
    }

    @Override
    public PaymentResource toModel(Payment entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    protected PaymentResource instantiateModel(Payment entity) {
        return new PaymentResource(entity);
    }

    public Link linkToSingleResource(Payment entity) {
        return entityLinks.linkToItemResource(PaymentResource.class, entity.getId());
    }
}
