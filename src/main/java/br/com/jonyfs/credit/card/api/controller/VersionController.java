package br.com.jonyfs.credit.card.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jonyfs.credit.card.api.util.ResourcePaths;

@PropertySource("classpath:/application.properties")
@RestController
@RequestMapping(
                value = ResourcePaths.Version.ROOT)
public class VersionController {

    @Value("${version}")
    private String projectVersion;

    @Value("${timestamp}")
    private String buildDate;

    @RequestMapping(
                    method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public HttpEntity<String> doPayment() {
        return new ResponseEntity<String>(projectVersion + " - " + buildDate, HttpStatus.OK);
    }
}
