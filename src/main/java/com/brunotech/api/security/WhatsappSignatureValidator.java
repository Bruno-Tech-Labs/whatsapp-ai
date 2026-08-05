package com.brunotech.api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

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
    public boolean isValid(String rawPayload, String signatureHeader) {
        if (signatureHeader == null || !signatureHeader.startsWith(SIGNATURE_PREFIX)) {
            System.out.println("ASSINATURA: HEADER AUSENTE OU INVALIDO");
            return false;
        }

        String receivedSignature = signatureHeader.substring(SIGNATURE_PREFIX.length());
        String expectedSignature = calculateHmac(rawPayload);

        //start of the debug block
        System.out.println("===== DEBUG WHATSAPP =====");
        System.out.println("App Secret configurado: " + appSecret);
        System.out.println("Payload length: " + rawPayload.length());
        System.out.println("Signature recebida: " + receivedSignature);
        System.out.println("Signature calculada: " + expectedSignature);
        System.out.println("==========================");        
        //end of the debug block

        return MessageDigest.isEqual(
                receivedSignature.getBytes(StandardCharsets.UTF_8),
                expectedSignature.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String calculateHmac(String payload) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
            byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            // HmacSHA256 sempre existe na JVM e a chave sempre e valida aqui;
            // se isso disparar, e erro de configuracao grave, nao de payload.
            throw new IllegalStateException("Erro ao calcular a assinatura do webhook", e);
        }
    }
}
