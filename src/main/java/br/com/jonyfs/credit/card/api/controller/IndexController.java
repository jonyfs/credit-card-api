package br.com.jonyfs.credit.card.api.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonyfs.credit.card.api.resource.IndexResource;
import br.com.jonyfs.credit.card.api.resource.IndexResourceAssembler;
import br.com.jonyfs.credit.card.api.util.ResourcePaths;

@RestController
public class IndexController {

    @Resource
    private IndexResourceAssembler indexResourceAssembler;

    @RequestMapping(
                    method = RequestMethod.GET, value = ResourcePaths.ROOT_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndexResource> index() {
        return ResponseEntity.ok(indexResourceAssembler.buildIndex());
    }
}
