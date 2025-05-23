package com.egov.profileservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class AppConfig
{
    @Autowired
    EurekaDiscoveryClient discoveryClient; // Spring is going to inject the Service Registry Information here

    @Bean
    public WebClient authValidateWebClient(WebClient.Builder webClientBuilder)
    {
        return webClientBuilder
                .baseUrl("http://auth-service/api/v1/validate")
                .filter(new LoggingWebClientFilter())
                .build();
    }

    @Bean
    @Scope(value = "prototype")
    public WebClient authValidateWebClientEurekaDiscovered(WebClient.Builder webClientBuilder)
    {
        List<ServiceInstance>  instances =   discoveryClient.getInstances("auth-service");

        if(instances.isEmpty())
        {
            throw new RuntimeException("No instances found for auth-service");
        }

        // Assuming you want to use the first instance and can be replaced by a load balancing strategy
        String hostname = instances.get(0).getHost();
        String port = String.valueOf(instances.get(0).getPort());

        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/api/v1/validate", hostname, port))
                .filter(new LoggingWebClientFilter())
                .build();
    }

}
