package com.brunotech.api.service;

import com.brunotech.api.dto.WhatsappWebhookPayload;
import com.brunotech.api.service.handler.MessageHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Orquestra o processamento de eventos de webhook do WhatsApp.
 *
 * Ele apenas faz o parse do payload e delega o trabalho para handlers
 * especializados, permitindo evoluir para novos tipos de mensagem.
 */
@Service
public class WhatsappEventProcessor {

    private static final Logger log = LoggerFactory.getLogger(WhatsappEventProcessor.class);

    private final ObjectMapper objectMapper;
    private final List<MessageHandler> handlers;

    public WhatsappEventProcessor(ObjectMapper objectMapper, List<MessageHandler> handlers) {
        this.objectMapper = objectMapper;
        this.handlers = handlers;
    }

    @Async
    public void processEvent(String rawPayload) {
        log.info("Evento recebido do webhook do WhatsApp");

        //Extrai o payload do webhook e faz o parse para o objeto WhatsappWebhookPayload
        WhatsappWebhookPayload payload;
        try {
            payload = objectMapper.readValue(rawPayload, WhatsappWebhookPayload.class);
        } catch (JsonProcessingException e) {
            log.error("Nao foi possivel parsear o payload do webhook", e);
            return;
        }

        //Valida se o payload contém uma mensagem válida
        WhatsappWebhookPayload.Message message = payload.getFirstMessage();
        if (message == null) {
            log.info("Nenhuma mensagem valida encontrada no payload do webhook.");
            return;
        }

        //Verifica o tipo de mensagem e delega para o handler apropriado
        for (MessageHandler handler : handlers) {
            if (handler.supports(message)) {
                handler.handle(message);
                return;
            }
        }

        log.info("Nenhum handler suportou a mensagem recebida.");
    }
}
