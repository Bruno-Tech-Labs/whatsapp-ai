package com.brunotech.api.exception;

import com.brunotech.api.response.ApiError;
import com.brunotech.api.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        private String getTraceId() {
        String trace = MDC.get("correlationId");
        if (trace == null || trace.isBlank()) {
            trace = UUID.randomUUID().toString();
        }
        return trace;
    }

    private boolean isWebhook(HttpServletRequest request) {
        return request != null && request.getRequestURI() != null && request.getRequestURI().startsWith("/webhook");
    }

    @ExceptionHandler(WhatsappApiException.class)
    public ResponseEntity<Object> handleWhatsappApiException(WhatsappApiException exception, HttpServletRequest request) {
        String traceId = getTraceId();
        log.error("WhatsappApiException (traceId={})", traceId, exception);
        if (isWebhook(request)) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ApiResponse<>(new ApiError("WHATSAPP_API_ERROR", exception.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleBadRequest(IllegalArgumentException exception, HttpServletRequest request) {
        String traceId = getTraceId();
        log.warn("BadRequest (traceId={}) {}", traceId, exception.getMessage());
        if (isWebhook(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(new ApiError("BAD_REQUEST", exception.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception, HttpServletRequest request) {
        String traceId = getTraceId();
        log.error("Unhandled exception (traceId={})", traceId, exception);
        if (isWebhook(request)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(new ApiError("INTERNAL_SERVER_ERROR", "Ocorreu um erro interno.")));
    }
}