package com.ecommerce.paymentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.reactive.CorsWebFilter;

public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {

        org.springframework.web.cors.CorsConfiguration config =
                new org.springframework.web.cors.CorsConfiguration();

        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return new org.springframework.web.cors.reactive.CorsWebFilter(source);
    }
}