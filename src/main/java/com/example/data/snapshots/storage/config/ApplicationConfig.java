package com.example.data.snapshots.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.PostConstruct;
import javax.validation.Validator;
import java.util.TimeZone;

@Configuration
@Import(WebConfig.class)
public class ApplicationConfig {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}
