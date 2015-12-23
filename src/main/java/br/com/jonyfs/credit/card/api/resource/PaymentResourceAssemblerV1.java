package br.com.jonyfs.credit.card.api.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import br.com.jonyfs.credit.card.api.controller.PaymentControllerV1;
import br.com.jonyfs.credit.card.api.model.Payment;

@Service
public class PaymentResourceAssemblerV1 extends ResourceAssemblerSupport<Payment, PaymentResource> {

    @Autowired
    EntityLinks entityLinks;

    public PaymentResourceAssemblerV1() {
        super(PaymentControllerV1.class, PaymentResource.class);
    }

    @Override
    public PaymentResource toResource(Payment entity) {
        PaymentResource resource = createResourceWithId(entity.getId(), entity);
        return resource;
    }

    public List<PaymentResource> toPageResources(Page<Payment> page) {
        List<PaymentResource> resources = new ArrayList<>();
        for (Payment entity : page.getContent()) {
            resources.add(toResource(entity));
        }
        // todo implementar a parte da página
        return resources;
    }

    @Override
    protected PaymentResource instantiateResource(Payment entity) {
        return new PaymentResource(entity);
    }

    public Link linkToSingleResource(Payment entity) {
        return entityLinks.linkToSingleResource(PaymentResource.class, entity.getId());
    }
}
