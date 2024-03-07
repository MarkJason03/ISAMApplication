package com.example.fyp_application.Model;

public class MessageHistoryModel {

    private int messageID;

    private int ticketID;

    private String senderName;

    private String messageBody;

    private String dateSent;



    public MessageHistoryModel(
            int messageID,
            int ticketID,
            String sender,
            String messageBody,
            String dateSent

    ){

        this.messageID = messageID;
        this.ticketID = ticketID;
        this.senderName = sender;
        this.messageBody = messageBody;
        this.dateSent = dateSent;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }
}
