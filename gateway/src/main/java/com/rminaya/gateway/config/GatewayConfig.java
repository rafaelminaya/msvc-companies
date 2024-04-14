package com.rminaya.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.util.Set;

@Configuration
public class GatewayConfig {

    // Configuración de las rutas del API Gateway con las rutas estáticas de cada microservicio.
    // El "profile" para esta configuración será "eureka-off" y será modificada usada en el "application.yml"
    @Bean
    @Profile(value = "eureka-off")
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder builder) {
        // Las rutas con un asterisco "/*" solo permite el funcionamiento de los métodos GET
        return builder
                .routes()
                .route(route -> route
                        .path("/companies-crud/company/*") // Ruta del API Gateway
                        .uri("http://localhost:8081") // Por detras tomara esta ruta
                )
                .route(route -> route
                        .path("/report-ms/report/*")
                        .uri("http://localhost:7070")
                )
                .build();
    }

    // Configuración de las rutas del API Gateway con las rutas dinámicas de cada microservicio.
    // El "profile" para esta configuración será "eureka-on" y será modificada usada en el "application.yml"

    @Bean
    @Profile(value = "eureka-on")
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder builder) {

        // Las rutas con el doble asterisco "/**" permite el funcionamiento de los métodos POST, PUT, DELETE
        // lb : Palabra reservada que indica que se accedera a este recurso mediante balanceo de carga
        return builder
                .routes()
                .route(route -> route
                        .path("/companies-crud/company/**") // Ruta del API Gateway.
                        .uri("lb://companies-crud") // Nombre del microservicio, indicando que este trabajará con balanceo de caga
                )
                .route(route -> route
                        .path("/report-ms/report/**")
                        .uri("lb://report-ms")
                )
                .build();
    }

    @Bean
    @Profile(value = "eureka-cb")
    public RouteLocator routeLocatorEurekaOnCB(RouteLocatorBuilder builder) {

        // Las rutas con el doble asterisco "/**" permite el funcionamiento de los métodos POST, PUT, DELETE
        // lb : Palabra reservada que indica que se accedera a este recurso mediante balanceo de carga
        return builder
                .routes()
                .route(route -> route
                        .path("/companies-crud/company/**") // Ruta del API Gateway.
                        // Registro de circuit breaker como un filtro, ya que el CB es un filtro
                        .filters(filter -> {
                            // circuitBreaker() : Este es una palabra reservada de spring cloud gateway, para referirse una configuración del circuit breaker
                            filter.circuitBreaker(config -> config
                                    .setName("gateway-cirbuit-breaker") // asignamos un nombre a este circuit breaker
                                    .setStatusCodes(Set.of("500", "400")) // Indicamos los códigos de estado esperados para el circuit breaker
                                    //.setStatusCodes(Set.of(HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.BAD_REQUEST.name())) // Indicamos los códigos de estado esperados para el circuit breaker
                                    .setFallbackUri("forward:/companies-crud-fallback/company/*")); // configuracion de ruta alternativa para cuando ocurra el "circuit breaker"
                            return filter;
                        })

                        .uri("lb://companies-crud") // Nombre del microservicio, indicando que este trabajará con balanceo de caga
                )
                .route(route -> route
                        .path("/report-ms/report/**")
                        .uri("lb://report-ms")
                )
                .route(route -> route
                        .path("/companies-crud-fallback/company/**")
                        .uri("lb://companies-crud-fallback")
                )
                .build();
    }


}
