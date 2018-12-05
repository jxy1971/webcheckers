package com.webcheckers.model;

/**
 * Message object that includes a type and a string
 *
 */

import com.webcheckers.model.Board.Enums.Type;

public class Message {

    private String text;
    private MessageType type;

    /**
     * Constructor for Message class
     * @param text The text of the message
     * @param messageType The type of message
     */
    public Message(String text, MessageType messageType) {
        this.text = text;
        this.type = messageType;
    }

    /**
     * Get message text
     * @return Message text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Get message type
     * @return Message type
     */
    public MessageType getType() {
        return this.type;
    }

}
