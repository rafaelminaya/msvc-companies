package com.rminaya.companiescrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * EnableDiscoveryClient
 * Habita el registro y descubrimiento de este microservicio en el "Eureka Server"
 * Usando  la configuración por defecto apuntando hacia el ip y puerto localhost:8761
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CompaniesCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompaniesCrudApplication.class, args);
	}

}
