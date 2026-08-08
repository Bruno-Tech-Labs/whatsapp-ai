package com.brunotech.api.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "company")
public class Company {
    
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "phone_number_id", nullable = false, unique = true, length = 100)
    private String phoneNumberId;
    
    @Column(name = "business_account_id", nullable = false, unique = true, length = 100)
    private String businessAccountId;

    @Column(name = "access_token", unique = true, nullable = false)
    private String accessToken;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected Company() {}

    //getters and setters
}
