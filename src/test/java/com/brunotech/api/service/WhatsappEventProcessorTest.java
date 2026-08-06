package com.brunotech.api.service;

import com.brunotech.api.dto.WhatsappWebhookPayload;
import com.brunotech.api.service.handler.MessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WhatsappEventProcessorTest {

    @Test
    void shouldDelegateValidMessageToMatchingHandler() throws Exception {
        List<MessageHandler> handlers = new ArrayList<>();
        RecordingHandler recordingHandler = new RecordingHandler();
        handlers.add(recordingHandler);

        WhatsappEventProcessor processor = new WhatsappEventProcessor(new ObjectMapper(), handlers);

        String payload = """
                {
                  "entry": [
                    {
                      "changes": [
                        {
                          "value": {
                            "messages": [
                              {
                                "from": "55999999999",
                                "text": {
                                  "body": "ola"
                                }
                              }
                            ]
                          }
                        }
                      ]
                    }
                  ]
                }
                """;

        processor.processEvent(payload);

        assertEquals(1, recordingHandler.handledMessages.size());
        assertEquals("55999999999", recordingHandler.handledMessages.get(0).getFrom());
        assertEquals("ola", recordingHandler.handledMessages.get(0).getText().getBody());
    }

    private static class RecordingHandler implements MessageHandler {
        private final List<WhatsappWebhookPayload.Message> handledMessages = new ArrayList<>();

        @Override
        public boolean supports(WhatsappWebhookPayload.Message message) {
            return message != null && message.getText() != null && message.getText().getBody() != null;
        }

        @Override
        public void handle(WhatsappWebhookPayload.Message message) {
            handledMessages.add(message);
        }
    }
}
