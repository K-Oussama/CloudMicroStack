package com.cloudmicrostack.service_registry.config;

import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.eureka.EurekaServerConfig;

@Configuration
@EnableEurekaServer
public class EurekaConfig {
    @Bean
    public EurekaServerConfig eurekaServerConfig() {
        EurekaServerConfigBean config = new EurekaServerConfigBean();
        config.setWaitTimeInMsWhenSyncEmpty(0);
        config.setResponseCacheUpdateIntervalMs(5000);
        return config;
    }
}