package com.brunotech.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhatsappWebhookPayload {

    private List<Entry> entry;

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public Message getFirstMessage() {
        if (entry == null) {
            return null;
        }

        for (Entry entryItem : entry) {
            if (entryItem == null || entryItem.getChanges() == null) {
                continue;
            }

            for (Change change : entryItem.getChanges()) {
                if (change == null || change.getValue() == null) {
                    continue;
                }

                Value value = change.getValue();
                List<Message> messages = value.getMessages();
                if (messages != null && !messages.isEmpty()) {
                    return messages.get(0);
                }
            }
        }

        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Entry {
        private List<Change> changes;

        public List<Change> getChanges() {
            return changes;
        }

        public void setChanges(List<Change> changes) {
            this.changes = changes;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Change {
        private Value value;

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Value {
        private List<Message> messages;

        public List<Message> getMessages() {
            return messages == null ? Collections.emptyList() : messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        private String from;
        private Text text;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public Text getText() {
            return text;
        }

        public void setText(Text text) {
            this.text = text;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Text {
        private String body;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
