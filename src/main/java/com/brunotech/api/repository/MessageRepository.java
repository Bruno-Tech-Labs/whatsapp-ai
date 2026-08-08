package com.brunotech.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.brunotech.api.entity.Message;    

import java.util.UUID;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    
    Optional<Message> findByWhatsappMessageId(String whatsappMessageId);
}
