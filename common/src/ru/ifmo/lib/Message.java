package ru.ifmo.lib;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Message implements Serializable {
    private final String sender;
    private final String text;
    private LocalDateTime dateTime;

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

/*    public LocalDateTime getDateTime() {
        return dateTime;
    }*/

    public void setDateTime() {
        this.dateTime = LocalDateTime.now();
    }

    public static Message getInstance(String sender, String text) {

        return new Message(sender, text);
    }

    @Override
    public String toString() {

        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM HH.mm.ss"))+ ": " + sender + ": " + text;
    }
}