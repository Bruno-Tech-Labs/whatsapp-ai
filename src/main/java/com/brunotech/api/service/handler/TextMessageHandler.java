package com.brunotech.api.service.handler;

import com.brunotech.api.dto.WhatsappWebhookPayload;
import com.brunotech.api.service.WhatsappMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TextMessageHandler implements MessageHandler {

    private static final Logger log = LoggerFactory.getLogger(TextMessageHandler.class);
    private final WhatsappMessageService whatsappMessageService;

    public TextMessageHandler(WhatsappMessageService whatsappMessageService) {
        this.whatsappMessageService = whatsappMessageService;
    }

    // Verifica se a mensagem é do tipo texto
    @Override
    public boolean supports(WhatsappWebhookPayload.Message message) {
        return message != null
                && message.getFrom() != null
                && message.getText() != null
                && message.getText().getBody() != null;
    }

    //Extrai o corpo da mensagem e envia uma resposta de volta para o remetente
    @Override
    public void handle(WhatsappWebhookPayload.Message message) {
        String from = message.getFrom();
        String body = message.getText().getBody();

        log.info("Mensagem recebida de {}: {}", from, body);

        String response = "Olá! Recebi sua mensagem.";
        whatsappMessageService.sendMessage(from, response);
    }
}
