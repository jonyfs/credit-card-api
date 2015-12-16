package br.com.jonyfs.credit.card.api;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import com.planetj.servlet.filter.compression.CompressingFilter;

public class GzipConfig {

    public static final Logger LOGGER = LoggerFactory.getLogger(GzipConfig.class);

    @PostConstruct
    public void init() {
        LOGGER.info("STARTED");
    }

    @Bean
    public Filter compressingFilter() {
        CompressingFilter compressingFilter = new CompressingFilter();
        return compressingFilter;
    }

}
