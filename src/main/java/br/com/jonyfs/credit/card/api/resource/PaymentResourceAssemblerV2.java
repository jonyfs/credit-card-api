package br.com.jonyfs.credit.card.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import br.com.jonyfs.credit.card.api.controller.PaymentControllerV2;
import br.com.jonyfs.credit.card.api.model.Payment;

@Service
public class PaymentResourceAssemblerV2 extends ResourceAssemblerSupport<Payment, PaymentResource> {

    @Autowired
    EntityLinks entityLinks;

    public PaymentResourceAssemblerV2() {
        super(PaymentControllerV2.class, PaymentResource.class);
    }

    @Override
    public PaymentResource toResource(Payment entity) {
        PaymentResource resource = createResourceWithId(entity.getId(), entity);
        return resource;
    }

    @Override
    protected PaymentResource instantiateResource(Payment entity) {
        return new PaymentResource(entity);
    }

    public Link linkToSingleResource(Payment entity) {
        return entityLinks.linkToSingleResource(PaymentResource.class, entity.getId());
    }
}
