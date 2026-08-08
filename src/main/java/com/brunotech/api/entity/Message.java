package com.brunotech.api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.brunotech.api.entity.enums.MessageDirection;
import com.brunotech.api.entity.enums.MessageType;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "whatsapp_message_id", nullable = false, unique = true)
    private String whatsappMessageId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageDirection direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY) //Muitas mensagens pertencem a uma conversa
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    protected Message() {
    }

    protected Message(Conversation conversation,
                      String whatsappMessageId,
                      MessageDirection direction,
                      MessageType messageType,
                      String content) {
        this.conversation = conversation;
        this.whatsappMessageId = whatsappMessageId;
        this.direction = direction;
        this.messageType = messageType;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public static Message createInbound(
            Conversation conversation,
            String whatsappMessageId,
            MessageType messageType,
            String content
    ) {
        return new Message(conversation, whatsappMessageId, MessageDirection.INBOUND, messageType, content);
    }

    public UUID getId() {
        return id;
    }

    public String getWhatsappMessageId() {
        return whatsappMessageId;
    }

    public void setWhatsappMessageId(String whatsappMessageId) {
        this.whatsappMessageId = whatsappMessageId;
    }

    public MessageDirection getDirection() {
        return direction;
    }

    public void setDirection(MessageDirection direction) {
        this.direction = direction;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}