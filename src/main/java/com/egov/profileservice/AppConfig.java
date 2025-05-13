package com.egov.profileservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig
{
    @Bean
    public WebClient authValidateWebClient(WebClient.Builder webClientBuilder)
    {
        return webClientBuilder
                .baseUrl("http://localhost:8091/api/v1/validate")
                .filter(new LoggingWebClientFilter())
                .build();
    }

    @Bean
    public WebClient authLogoutWebClient(WebClient.Builder webClientBuilder)
    {
        return webClientBuilder
                .baseUrl("http://localhost:8091/api/v1/logout")
                .filter(new LoggingWebClientFilter())
                .build();
    }
}
