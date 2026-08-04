package com.brunotech.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Habilita o processamento assincrono (metodos anotados com @Async).
 *
 * Usado para separar duas responsabilidades no webhook do WhatsApp:
 * 1) responder rapido para a Meta (200 OK)
 * 2) processar o evento de verdade, sem fazer a Meta esperar por isso.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
}
