package br.com.jonyfs.credit.card.api.resource;

import static java.util.Arrays.asList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Service;

import br.com.jonyfs.credit.card.api.controller.PaymentControllerV1;
import br.com.jonyfs.credit.card.api.controller.PaymentControllerV2;
import br.com.jonyfs.credit.card.api.controller.VersionController;

@Service
public class IndexResourceAssembler {
    private final RelProvider relProvider;
    private final EntityLinks entityLinks;

    @Autowired
    public IndexResourceAssembler(RelProvider relProvider, EntityLinks entityLinks) {
        this.relProvider = relProvider;
        this.entityLinks = entityLinks;
    }

    public IndexResource buildIndex() {

        final IndexResource resource = new IndexResource("credit-card-api", "Credit Card API", "Jony Santos", "https://www.linkedin.com/in/jonyfs","https://github.com/jonyfs/credit-card-api");

        resource.add(linkTo(PaymentControllerV1.class).withRel("payments"), linkTo(PaymentControllerV2.class).withRel("payments"), linkTo(VersionController.class).withRel("version"));
        // Note this is unfortunately hand-written. If you add a new entity,
        // have to manually add a new link
        final List<Link> links = asList(entityLinks.linkToCollectionResource(PaymentResource.class).withRel(relProvider.getCollectionResourceRelFor(PaymentResource.class)));
        resource.add(links);
        return resource;
    }
}