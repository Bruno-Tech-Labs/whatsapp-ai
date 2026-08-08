package com.brunotech.api.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.brunotech.api.entity.Company;
import com.brunotech.api.repository.CompanyRepository;

@Service
public class CompanyService {
    
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company findById(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow();
    }
}
