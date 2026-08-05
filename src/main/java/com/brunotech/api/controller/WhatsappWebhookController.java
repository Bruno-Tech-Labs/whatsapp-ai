package com.brunotech.api.controller;

import com.brunotech.api.security.WhatsappSignatureValidator;
import com.brunotech.api.service.WhatsappEventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoint que a Meta usa para falar com a nossa aplicacao.
 *
 * Dois fluxos completamente diferentes no mesmo path "/webhook":
 *
 * GET  -> handshake de verificacao. Acontece uma unica vez, quando voce
 *         configura a URL do webhook no painel da Meta. A Meta manda um
 *         "hub.challenge" e espera receber esse mesmo valor de volta,
 *         desde que o "hub.verify_token" bata com o que voce configurou.
 *
 * POST -> eventos reais (mensagens recebidas, atualizacoes de status).
 *         Acontece continuamente, a cada mensagem. Precisamos validar a
 *         assinatura (X-Hub-Signature-256) e responder 200 rapido, antes
 *         de processar o conteudo de verdade.
 */
@RestController
@RequestMapping("/webhook")
public class WhatsappWebhookController {

    private static final Logger log = LoggerFactory.getLogger(WhatsappWebhookController.class);

    private final String verifyToken;
    private final WhatsappSignatureValidator signatureValidator;
    private final WhatsappEventProcessor eventProcessor;

    public WhatsappWebhookController(
            @Value("${whatsapp.webhook.verify-token}") String verifyToken,
            WhatsappSignatureValidator signatureValidator,
            WhatsappEventProcessor eventProcessor
    ) {
        this.verifyToken = verifyToken;
        this.signatureValidator = signatureValidator;
        this.eventProcessor = eventProcessor;
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> verify(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String receivedToken,
            @RequestParam("hub.challenge") String challenge
    ) {
        boolean modeValido = "subscribe".equals(mode);
        boolean tokenValido = verifyToken.equals(receivedToken);

        if (modeValido && tokenValido) {
            log.info("Webhook verificado com sucesso pela Meta.");
            return ResponseEntity.ok(challenge);
        }

        log.warn("Falha na verificacao do webhook. mode={}, tokenBateu={}", mode, tokenValido);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    public ResponseEntity<Void> receiveEvent(
            @RequestBody String rawPayload,
            @RequestHeader(value = "X-Hub-Signature-256", required = false) String signature
    ) {
        log.info(
            "X-Hub-Signature-256 recebido: {}",
            signature != null ? "SIM" : "NAO"
        );        
        if (!signatureValidator.isValid(rawPayload, signature)) {
            log.warn("Requisicao recebida no webhook com assinatura invalida ou ausente. Descartada.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Responde 200 imediatamente. O processamento de verdade acontece
        // em segundo plano (metodo @Async), para nao fazer a Meta esperar.
        eventProcessor.processEvent(rawPayload);

        return ResponseEntity.ok().build();
    }
}
