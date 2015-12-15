package br.com.jonyfs.credit.card.api;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.planetj.servlet.filter.compression.CompressingFilter;

@Configuration
public class GzipConfig {

    @Bean
    public Filter compressingFilter() {
        CompressingFilter compressingFilter = new CompressingFilter();
        return compressingFilter;
    }

}
