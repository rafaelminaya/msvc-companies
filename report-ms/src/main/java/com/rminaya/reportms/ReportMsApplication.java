package com.rminaya.reportms;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ReportMsApplication implements CommandLineRunner {

    /*
     * EurekaClient
     * Inyectamos esta interfaz que es "bean" que viene por defecto al tener la dependencia de "eureka client"
     * Sirve para realizar el balanceo de carga y OpenFeign se apoya en este
     * Prácticamente muesta la misma información que la plataforma web de eureka client pero en el terminal
     */
    @Autowired
    private EurekaClient eurekaClient;

    public static void main(String[] args) {
        SpringApplication.run(ReportMsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.eurekaClient.getAllKnownRegions().forEach(System.out::println);
        System.out.println(this.eurekaClient.getApplications("companies-crud"));
    }
}
