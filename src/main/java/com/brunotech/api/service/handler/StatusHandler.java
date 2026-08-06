package com.brunotech.api.service.handler;

import com.brunotech.api.dto.WhatsappWebhookPayload;

public class StatusHandler implements MessageHandler {

    @Override
    public boolean supports(WhatsappWebhookPayload.Message message) {
        return false;
    }

    @Override
    public void handle(WhatsappWebhookPayload.Message message) {
        // Futuramente será implementado para tratar updates de status.
    }
}
