package com.cc.demo.otto;

/*
Builder pattern
Usage: new Message.MessageBuilder().text("some text").build()
 */
public class Message {
    // required
    private String text;
    // optional
    private String sender;

    private Message(MessageBuilder builder) {
        this.text = builder.text;
        this.sender = builder.sender;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public static class MessageBuilder {
        private String text = null;
        private String sender = null;

        public MessageBuilder(String text) {
            this.text = text;
        }

        public MessageBuilder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
