package com.brunotech.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.brunotech.api.entity.Conversation;

import java.util.UUID;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    Optional<Conversation> findByPhoneNumber(String phoneNumber);
    
}
