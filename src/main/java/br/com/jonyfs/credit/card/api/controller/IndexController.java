package br.com.jonyfs.credit.card.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonyfs.credit.card.api.util.ResourcePaths;

@RestController
public class IndexController {

    @RequestMapping(value = ResourcePaths.ROOT_API, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResourceSupport index() {
        ResourceSupport index = new ResourceSupport();
        index.add(linkTo(PaymentControllerV1.class).withRel("payments"),
                linkTo(PaymentControllerV2.class).withRel("payments"),
                linkTo(PaymentControllerV3.class).withRel("payments"));
        return index;
    }
}
