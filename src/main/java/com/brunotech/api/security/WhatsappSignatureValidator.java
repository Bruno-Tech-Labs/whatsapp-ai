package com.brunotech.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Locale;

/**
 * Valida que uma requisicao de webhook realmente veio da Meta.
 *
 * A Meta assina o corpo de toda requisicao POST com HMAC-SHA256, usando o
 * App Secret do app (nao confundir com o Access Token). O hash calculado
 * chega no header "X-Hub-Signature-256", no formato "sha256=<hex>".
 *
 * Recalculamos o mesmo hash aqui e comparamos os dois em tempo constante
 * (MessageDigest.isEqual), para nao dar brecha a timing attacks.
 */
@Component
public class WhatsappSignatureValidator {

    private static final Logger log = LoggerFactory.getLogger(WhatsappSignatureValidator.class);
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String SIGNATURE_PREFIX = "sha256=";

    private final String appSecret;

    public WhatsappSignatureValidator(@Value("${whatsapp.app-secret}") String appSecret) {
        this.appSecret = appSecret;
    }

    /**
     * @param rawPayload      corpo bruto da requisicao, exatamente como recebido (String)
     * @param signatureHeader valor do header X-Hub-Signature-256, pode ser null
     * @return true se a assinatura for valida
     */

    public boolean isValid(byte[] rawPayload, String signatureHeader) {

        if (signatureHeader == null) {
            log.warn("[Signature Debug] header ausente");
            return false;
        }

        String header = signatureHeader.trim();
        if (!header.toLowerCase(Locale.ROOT).startsWith(SIGNATURE_PREFIX)) {
            log.warn("[Signature Debug] prefixo inválido: {}", header);
            return false;
        }

        String hex = header.substring(SIGNATURE_PREFIX.length()).trim();
        if (hex.length() != 64) {
            log.warn("[Signature Debug] comprimento inválido do hash: {}", hex.length());
            return false;
        }

        byte[] receivedSignature;
        try {
            receivedSignature = HexFormat.of().parseHex(hex);
        } catch (IllegalArgumentException e) {
            log.warn("[Signature Debug] hex inválido: {}", hex);
            return false;
        }

        byte[] expectedSignature = calculateHmac(rawPayload);

        String expectedHex = HexFormat.of().formatHex(expectedSignature);
        log.info("[Signature Debug] receivedSignature={} expectedSignature={}", hex, expectedHex);

        return MessageDigest.isEqual(receivedSignature, expectedSignature);
    }

    private byte[] calculateHmac(byte[] payload) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(appSecret.trim().getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
            return mac.doFinal(payload);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("Erro ao calcular a assinatura do webhook", e);
        }
    }
}
