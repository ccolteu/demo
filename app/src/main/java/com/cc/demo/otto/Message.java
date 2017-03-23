package com.cc.demo.otto;

/*
Usage: new Message.MessageBuilder("message text").sender("sender id").build()
 */
public class Message {
    // required
    private String mText;
    // optional
    private String mSender;

    private Message(MessageBuilder builder) {
        this.mText = builder.mText;
        this.mSender = builder.mSender;
    }

    public String getText() {
        return mText;
    }

    public String getSender() {
        return mSender;
    }

    public static class MessageBuilder {
        private String mText = null;
        private String mSender = null;

        /*
        required params
         */
        public MessageBuilder(String text) {
            this.mText = text;
        }

        /*
        optional params
         */
        public MessageBuilder sender(String sender) {
            this.mSender = sender;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
