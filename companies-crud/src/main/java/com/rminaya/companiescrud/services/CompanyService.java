package com.rminaya.companiescrud.services;

import com.rminaya.companiescrud.entities.Company;

public interface CompanyService {

    Company create(Company company);
    Company readByname(String name);
    Company update(Company company, String name);
    void delete(String name);
}
