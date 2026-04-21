package br.com.jonyfs.credit.card.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.DefaultCurieProvider;

@Configuration
public class Config {

    @Bean
    public CurieProvider curieProvider() {
        return new DefaultCurieProvider("ex", UriTemplate.of("https://creditcardapi.herokuapp.com/api/{rel}"));
    }
}
