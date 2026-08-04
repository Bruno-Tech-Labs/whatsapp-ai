package com.brunotech.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Processa o conteudo de um evento de webhook do WhatsApp, DEPOIS que o
 * controller ja respondeu 200 para a Meta.
 *
 * Por enquanto so loga o payload bruto, para conseguirmos ver no log a
 * estrutura real que a Meta manda (entry -> changes -> value -> messages/statuses).
 * Os proximos passos (parsear o JSON, diferenciar mensagem de status,
 * chamar a IA, responder o cliente) entram aqui, dentro deste metodo.
 */
@Service
public class WhatsappEventProcessor {

    private static final Logger log = LoggerFactory.getLogger(WhatsappEventProcessor.class);

    @Async
    public void processEvent(String rawPayload) {
        log.info("Evento recebido do webhook do WhatsApp: {}", rawPayload);
        // TODO (proxima etapa): parsear o JSON em um DTO e diferenciar
        // "messages" (mensagem recebida) de "statuses" (sent/delivered/read/failed).
    }
}
