package br.com.jonyfs.credit.card.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonyfs.credit.card.api.model.Payment;
import br.com.jonyfs.credit.card.api.util.ResourcePaths;

@RestController
@RequestMapping(value = ResourcePaths.ROOT_API)
public class IndexController {

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<org.springframework.hateoas.Resource<Page<Payment>>> query() {
		return null;
	}
}
