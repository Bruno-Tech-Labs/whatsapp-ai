package com.brunotech.api.service;

import org.springframework.stereotype.Service;

import com.brunotech.api.entity.Conversation;
import com.brunotech.api.repository.ConversationRepository;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class ConversationService {


    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    //Verifica se a conversa existe, caso não exista cria uma nova
    @Transactional // Adiciona a anotação @Transactional para garantir que a operação esteja dentro de uma transação
    public Conversation finOrCreate(String phoneNumber) {
        return conversationRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> createConversation(phoneNumber));
    }

    private Conversation createConversation(String phoneNumber) {
        Conversation conversation = Conversation.create(phoneNumber);
        return conversationRepository.save(conversation);
    }

    public static String generateResponse(String message) {
        return "Olá, mensagem recebida!, voce disse: " + message;
    }
}
