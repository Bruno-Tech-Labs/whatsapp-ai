package com.brunotech.api.service.handler;

import com.brunotech.api.dto.WhatsappWebhookPayload;

public interface MessageHandler {

    boolean supports(WhatsappWebhookPayload.Message message);

    void handle(WhatsappWebhookPayload.Message message);
}
