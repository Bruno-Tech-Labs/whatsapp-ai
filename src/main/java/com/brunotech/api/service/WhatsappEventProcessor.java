package com.brunotech.api.service;

import com.brunotech.api.dto.WhatsappWebhookPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Processa o conteudo de um evento de webhook do WhatsApp, DEPOIS que o
 * controller ja respondeu 200 para a Meta.
 */
@Service
public class WhatsappEventProcessor {

    private static final Logger log = LoggerFactory.getLogger(WhatsappEventProcessor.class);

    private final ObjectMapper objectMapper;
    private final WhatsappMessageService whatsappMessageService;

    public WhatsappEventProcessor(ObjectMapper objectMapper, WhatsappMessageService whatsappMessageService) {
        this.objectMapper = objectMapper;
        this.whatsappMessageService = whatsappMessageService;
    }

    @Async
    public void processEvent(String rawPayload) {
        log.info("Evento recebido do webhook do WhatsApp: {}", rawPayload);

        WhatsappWebhookPayload payload;
        try {
            payload = objectMapper.readValue(rawPayload, WhatsappWebhookPayload.class);
        } catch (JsonProcessingException e) {
            log.error("Nao foi possivel parsear o payload do webhook", e);
            return;
        }

        WhatsappWebhookPayload.Message message = payload.getFirstMessage();
        if (message == null || message.getFrom() == null || message.getText() == null || message.getText().getBody() == null) {
            log.info("Nenhuma mensagem de texto valida encontrada no payload do webhook.");
            return;
        }

        String from = message.getFrom();
        String body = message.getText().getBody();
        log.info("Mensagem recebida de {}: {}", from, body);

        String response = "Olá! Recebi sua mensagem.";
        whatsappMessageService.sendMessage(from, response);
    }
}
