package com.huachuan.config;

import ca.uhn.fhir.context.FhirContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FhirContextConfiguration {

    @Bean
    public FhirContext getFhirContext() {
        return FhirContext.forR4();
    }
}