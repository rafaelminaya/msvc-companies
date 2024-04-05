package com.rminaya.companiescrud.controllers;

import com.rminaya.companiescrud.entities.Company;
import com.rminaya.companiescrud.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/company")
@Tag(name = "Companies resource") // Anotación de swagger
@AllArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "get a company given a company name")  // Anotación de swagger
    @GetMapping(path = "/{name}")
    public ResponseEntity<Company> get(@PathVariable String name) {
        // El log será importante para la trazabilidad que veremos más adelante
        log.info("GET: company {}", name);
        return ResponseEntity.ok(this.companyService.readByname(name));
    }

    @Operation(summary = "save in DB a company given a company from body")
    @PostMapping
    public ResponseEntity<Company> post(@RequestBody Company company) {
        log.info("POST: company {}", company.getName());
        /*
         * this.companyService.create(company).getName()
         * Retorna el identificador del recurso que se está creando
         * Debería retornarse el "id", pero devolvemos el "name" como alternativa ya que en BD se creó al campo como "unique"
         */
        return ResponseEntity.created(
                        URI.create(this.companyService.create(company).getName()))
                .build();
    }

    @Operation(summary = "update in DB a company given a company from body")
    @PutMapping(path = "/{name}")
    public ResponseEntity<Company> put(@RequestBody Company company,
                                       @PathVariable String name) {
        // El log será importante para la trazabilidad que veremos más adelante
        log.info("PUT: company {}", name);
        return ResponseEntity.ok(this.companyService.update(company, name));
    }

    @Operation(summary = "delete in DB a company given a company name")
    @DeleteMapping(path = "/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        log.info("DELETE: company {}", name);
        this.companyService.delete(name);
        return ResponseEntity.noContent().build();
    }
}
