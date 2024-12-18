package com.cloudmicrostack.service_registry.config;

import java.util.Set;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ActuatorConfig {
    @Bean
    public WebEndpointProperties webEndpointProperties() {
        WebEndpointProperties props = new WebEndpointProperties();
        props.getExposure().setInclude(Set.of("health", "info", "metrics"));
        return props;
    }
}