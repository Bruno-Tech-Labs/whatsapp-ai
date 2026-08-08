package com.brunotech.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.brunotech.api.entity.Conversation;
import com.brunotech.api.entity.Message;
import com.brunotech.api.entity.enums.MessageType;
import com.brunotech.api.repository.MessageRepository;

import jakarta.transaction.Transactional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Message saveInboundMessage(
            Conversation conversation, 
            String whatsappMessageId, 
            MessageType messageType, 
            String content
    ) {

        Optional<Message> existingMessage =
                messageRepository.findByWhatsappMessageId(whatsappMessageId);

        if (existingMessage.isPresent()) {
            return existingMessage.get();
        }

        Message message = Message.createInbound(
                conversation,
                whatsappMessageId,
                messageType,
                content
        );

        return messageRepository.save(message);
    }
    
}
