package com.example.data.snapshots.storage.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@Import({WebConfig.class, ServiceConfig.class})
public class ApplicationConfig {

  @Autowired
  private MessageSource messageSource;

  @Value("${data.snapshot.storage.date.time.format:yyyy-MM-dd'T'HH:mm:ss.SSS}")
  private String dateTimeFormat;

  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  @Bean
  public Validator validator() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.setValidationMessageSource(messageSource);
    return validator;
  }

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    return Jackson2ObjectMapperBuilder.json()
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .modulesToInstall(new JavaTimeModule())
        .dateFormat(new SimpleDateFormat(dateTimeFormat))
        .build();
  }

  @Bean
  @Qualifier("csvMapper")
  public CsvMapper csvMapper() {
    CsvMapper csvMapper = new CsvMapper();
    csvMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    csvMapper.registerModule(new JavaTimeModule());
    csvMapper.setDateFormat(new SimpleDateFormat(dateTimeFormat));
    return csvMapper;
  }

}
