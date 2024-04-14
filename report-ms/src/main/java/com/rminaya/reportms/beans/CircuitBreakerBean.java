package com.rminaya.reportms.beans;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerBean {

    // Método para configurar los valores por defecto de cada "circuit breaker" implementado en el proyecto.
    // Customizer : Interfaz propia de circuit breaker
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> gobalCustomCircuitBreaker() {
        // Creamos una configuración personalizada del Circuit Breaker
        var circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .slidingWindowSize(10) // Cantidad de peticiones para evaluar fallos durante el estado "cerrado". Si se pasa entra al estado "abierto".
                .failureRateThreshold(50) // Umbral de fallo, por defecto es 50, referente al 50%
                .waitDurationInOpenState(Duration.ofSeconds(10L)) // Tiempo de duración en el estado "abierto"
                .permittedNumberOfCallsInHalfOpenState(5) // cantidad de llamadas en el estado "semi-abierto". Por defecto son 10.
                .slowCallRateThreshold(50) // Umbral de fallo, pero para llamadas lentas, por defecto es 100, referente al 100%
                .slowCallDurationThreshold(Duration.ofSeconds(2L)) // Tiempo maximo de espera para considerar al request como una llamada lenta. Por defecto es 1 segundo. Se apoya con el "slidingWindowSize()" para evaluar la cantidad de peticiones a evaluar y según el porcentae indicado en "slowCallRateThreshold()"
                .build();
        // Configuracion del TimeLimiter
        var timeLimiterConfig = TimeLimiterConfig
                .custom()
                // Tiempo máximo de espera a 6 segundos. Por defecto es 1 segundo.
                // Indicamos 6 segundos, puesto que en el "CompanyController" indicamos un sleep de 5 segundos.
                // De ser menor el tiempo, arrojara un error de "TimeLimiter" lo cual también es aceptable si se quiere esperar este error.
                // Este tiene proridad por sobre la llamada lenta configurado con el metodo "slowCallDurationThreshold()"
                .timeoutDuration(Duration.ofSeconds(6L)) //Tiempo máximo de espera a 6 segundos. Por defecto es 1 segundo. El postman estará esperando toda este tiempo hasta recibir el "response"
                .build();
        // Asignación de estas 2 configuraciones al "factory" del "circuit breaker" con "Resilience4j"
        // Lo retornamos como una "expresión lambda", puesto que "Customizer" es una interfaz funcional
        return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory
                .configureDefault(id -> new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(circuitBreakerConfig)
                        .timeLimiterConfig(timeLimiterConfig)
                        .build());
    }
}
