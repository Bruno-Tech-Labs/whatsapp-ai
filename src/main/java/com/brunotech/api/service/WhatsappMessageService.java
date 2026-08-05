package com.brunotech.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.brunotech.api.exception.WhatsappApiException;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsappMessageService {

    private static final Logger log = LoggerFactory.getLogger(WhatsappMessageService.class);

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String accessToken;

    public WhatsappMessageService(
            @Value("${whatsapp.cloud.access-token}") String accessToken,
            @Value("${whatsapp.cloud.phone-number-id}") String phoneNumberId
    ) {
        this.accessToken = accessToken != null ? accessToken.trim() : "";
        if (this.accessToken.isBlank()) {
            throw new IllegalStateException("Missing configuration: whatsapp.cloud.access-token");
        }
        if (phoneNumberId == null || phoneNumberId.isBlank()) {
            throw new IllegalStateException("Missing configuration: whatsapp.cloud.phone-number-id");
        }
        this.apiUrl = "https://graph.facebook.com/v16.0/" + phoneNumberId + "/messages";
        this.restTemplate = new RestTemplate();
    }

    public void sendMessage(String to, String bodyText) {
        if (to == null || to.isBlank()) {
            log.warn("Telefone de destino invalido. Mensagem nao enviada.");
            return;
        }

        Map<String, Object> message = new HashMap<>();
        message.put("messaging_product", "whatsapp");
        message.put("to", to);
        message.put("type", "text");

        Map<String, String> text = new HashMap<>();
        text.put("body", bodyText != null ? bodyText : "");
        message.put("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(message, headers);

        try {
            restTemplate.postForEntity(apiUrl, request, String.class);
            log.info("Mensagem enviada para {} com sucesso.", to);
        } catch (RestClientException e) {
            log.error("Falha ao enviar mensagem para {}: {}", to, e.getMessage(), e);
            throw new WhatsappApiException("Falha ao enviar mensagem para " + to, e);
        }
    }
}
