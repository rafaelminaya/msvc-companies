package com.rminaya.reportms.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

// De esta forma configuramos y habilitamos la interfaz "WebClient" como el "web client" en este proyecto
// WebClient usa programación reactiva y es de la dependencia WebFlux
// Registrándolo en el "contenedor de spring" para poder inyectarlo en "CompaniesFallbackRepository"
// Este proceso es similar a lo utilizado con "RestTemplate"
@Configuration
public class WebClientBean {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
