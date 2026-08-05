package com.brunotech.api.exception;

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

        @ExceptionHandler(WhatsappApiException.class)
        public ResponseEntity<ErrorResponse> handleWhatsappApiException(WhatsappApiException exception) {
                String traceId = getTraceId();
                log.error("WhatsappApiException (traceId={})", traceId, exception);
                ErrorResponse body = new ErrorResponse(HttpStatus.BAD_GATEWAY.value(), HttpStatus.BAD_GATEWAY.getReasonPhrase(), exception.getMessage(), traceId);
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException exception) {
                String traceId = getTraceId();
                log.warn("BadRequest (traceId={}) {}", traceId, exception.getMessage());
                ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage(), traceId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
                String traceId = getTraceId();
                log.error("Unhandled exception (traceId={})", traceId, exception);
                ErrorResponse body = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Ocorreu um erro interno.", traceId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
}