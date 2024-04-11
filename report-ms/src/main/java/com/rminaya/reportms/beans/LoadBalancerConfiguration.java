package com.rminaya.reportms.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

// Esta clase trabajará con la interfaz "CompaniesRepository" anotada con "@LoadBalancerClient"
@Slf4j
public class LoadBalancerConfiguration {
    /*
     * ServiceInstanceListSupplier
     * Interfaz propia de "Spring Cloud Load-Balancer"
     *
     * ConfigurableApplicationContext
     * Como parámetro recibirá toda el "contexto de spring"
     */
    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context) {
        log.info("Configuring load balancer");

        return ServiceInstanceListSupplier
                .builder()
                .withBlockingDiscoveryClient() // indica que usaremos bloqueo, es decir, que NO sera reactivo
                //.withSameInstancePreference() // indica que se va a trabajar con la misma instancia. Comentaremos para ejecutar 3 instancias de "companies-crud"
                .build(context); // El argumento de este método es de tipo "ConfigurableApplicationContext"
    }

}
