package br.com.jonyfs.credit.card.api.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

import br.com.jonyfs.credit.card.api.controller.PaymentControllerV1;
import br.com.jonyfs.credit.card.api.model.Payment;

@Service
public class PaymentResourceAssemblerV1 extends RepresentationModelAssemblerSupport<Payment, PaymentResource> {

    @Autowired
    EntityLinks entityLinks;

    public PaymentResourceAssemblerV1() {
        super(PaymentControllerV1.class, PaymentResource.class);
    }

    @Override
    public PaymentResource toModel(Payment entity) {
        return createModelWithId(entity.getId(), entity);
    }

    public List<PaymentResource> toPageResources(Page<Payment> page) {
        List<PaymentResource> resources = new ArrayList<>();
        for (Payment entity : page.getContent()) {
            resources.add(toModel(entity));
        }
        return resources;
    }

    @Override
    protected PaymentResource instantiateModel(Payment entity) {
        return new PaymentResource(entity);
    }

    public Link linkToSingleResource(Payment entity) {
        return entityLinks.linkToItemResource(PaymentResource.class, entity.getId());
    }
}
