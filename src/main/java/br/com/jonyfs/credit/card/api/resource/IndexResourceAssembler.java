package br.com.jonyfs.credit.card.api.resource;

import static java.util.Arrays.asList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.stereotype.Service;

import br.com.jonyfs.credit.card.api.controller.PaymentControllerV1;
import br.com.jonyfs.credit.card.api.controller.PaymentControllerV2;
import br.com.jonyfs.credit.card.api.controller.VersionController;

@Service
public class IndexResourceAssembler {
    private final LinkRelationProvider linkRelationProvider;
    private final EntityLinks entityLinks;

    @Autowired
    public IndexResourceAssembler(LinkRelationProvider linkRelationProvider, EntityLinks entityLinks) {
        this.linkRelationProvider = linkRelationProvider;
        this.entityLinks = entityLinks;
    }

    public IndexResource buildIndex() {

        final IndexResource resource = new IndexResource("credit-card-api", "Credit Card API", "Jony Santos",
                "https://www.linkedin.com/in/jonyfs", "https://github.com/jonyfs/credit-card-api");

        resource.add(linkTo(PaymentControllerV1.class).withRel("payments"),
                linkTo(PaymentControllerV2.class).withRel("payments"),
                linkTo(VersionController.class).withRel("version"));

        final List<Link> links = asList(entityLinks.linkToCollectionResource(PaymentResource.class)
                .withRel(linkRelationProvider.getCollectionResourceRelFor(PaymentResource.class)));
        resource.add(links);
        return resource;
    }
}
