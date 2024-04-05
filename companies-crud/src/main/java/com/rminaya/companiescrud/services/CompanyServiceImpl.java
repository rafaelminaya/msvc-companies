package com.rminaya.companiescrud.services;

import com.rminaya.companiescrud.entities.Category;
import com.rminaya.companiescrud.entities.Company;
import com.rminaya.companiescrud.repositories.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public Company create(Company company) {
        // validación para asignar una "category" de "NONE" en caso no se le reciba algún valor
        company.getWebSites().forEach(webSite -> {
            if (Objects.isNull(webSite.getCategory())) {
                webSite.setCategory(Category.NONE);
            }
        });
        return this.companyRepository.save(company);
    }

    @Override
    public Company readByname(String name) {
        return this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));
    }

    @Override
    public Company update(Company company, String name) {
        // La búsqueda será por el nombre, aprovechando el "unique" de este campo en la definición de las tablas
        Company companytoUpdate = this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));

        companytoUpdate.setFounder(company.getFounder());
        companytoUpdate.setLogo(company.getLogo());
        return this.companyRepository.save(companytoUpdate);
    }

    @Override
    public void delete(String name) {
        Company companytoUpdate = this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));
        this.companyRepository.delete(companytoUpdate);
    }
}
