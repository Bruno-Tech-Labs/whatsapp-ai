package com.brunotech.api.service.handler;

import com.brunotech.api.dto.WhatsappWebhookPayload;
import com.brunotech.api.entity.enums.MessageType;
import com.brunotech.api.service.ConversationService;
import com.brunotech.api.service.MessageService;
import com.brunotech.api.service.WhatsappMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TextMessageHandler implements MessageHandler {

    private static final Logger log = LoggerFactory.getLogger(TextMessageHandler.class);
    private final WhatsappMessageService whatsappMessageService;
    private final ConversationService conversationService;
    private final MessageService messageService;

    public TextMessageHandler(
            WhatsappMessageService whatsappMessageService,
            ConversationService conversationService,
            MessageService messageService
    ) {
        this.whatsappMessageService = whatsappMessageService;
        this.conversationService = conversationService;
        this.messageService = messageService;
    }

    // Verifica se a mensagem é do tipo texto
    @Override
    public boolean supports(WhatsappWebhookPayload.Message message) {
        return message != null
                && message.getFrom() != null
                && message.getText() != null
                && message.getText().getBody() != null;
    }

    // Extrai o corpo da mensagem, persiste a conversa e a mensagem, e envia a resposta
    @Override
    public void handle(WhatsappWebhookPayload.Message message) {
        String from = message.getFrom();
        String body = message.getText().getBody();
        String messageId = message.getId();

        log.info("Mensagem recebida de {}: {}", from, body);

        var conversation = conversationService.finOrCreate(from);
        messageService.saveInboundMessage(conversation, messageId, MessageType.TEXT, body);

        String response = ConversationService.generateResponse(body);
        whatsappMessageService.sendMessage(from, response);
    }
}
