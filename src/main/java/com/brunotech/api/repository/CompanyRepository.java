package com.brunotech.api.repository;

import com.brunotech.api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;   

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    
}
