package com.rminaya.reportms.repositories;

import com.rminaya.reportms.beans.LoadBalancerConfiguration;
import com.rminaya.reportms.models.Company;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
 * @LoadBalancerClient
 * Configura el balanceador de carga.
 * Esta configuración será complementada con nuestra clase personalizada "LoadBalancerConfiguration"
 *
 * name = "companies-crud"
 * Es el nombre que daremos a este "load balancer" / "balanceador de carga".
 * Es coincidencia que sea igual al "feign client"
 */
@FeignClient(name = "companies-crud")
@LoadBalancerClient(name = "companies-crud", configuration = LoadBalancerConfiguration.class)
public interface CompaniesRepository {
    @GetMapping(path = "/companies-crud/company/{name}")
    Optional<Company> getByName(@PathVariable String name);

    @PostMapping(path = "/companies-crud/company")
    Optional<Company> postByName(@RequestBody Company company);

    @DeleteMapping(path = "/companies-crud/company/{name}")
    void deleteByName(@PathVariable String name);
}
