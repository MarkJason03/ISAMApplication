package com.example.fyp_application.Model;

public class MessageHistoryModel {

    private int messageID;

    private int ticketID;


    private String ticketTitle;
    private String senderName;

    private String agentName;
    private String messageBody;

    private String dateSent;



    //Loading the message history for admin/ user, so we can use their name to concatenate at the end
    public MessageHistoryModel(
            int messageID,
            int ticketID,
            String sender,
            String agent,
            String messageBody,
            String dateSent

    ){

        this.messageID = messageID;
        this.ticketID = ticketID;
        this.senderName = sender;
        this.agentName = agent;
        this.messageBody = messageBody;
        this.dateSent = dateSent;
    }


    //Loading individual message piece
    public MessageHistoryModel(
            int messageID,
            String ticketTitle,
            String messageBody,
            String dateSent
    ){
        this.messageID = messageID;
        this.ticketTitle = ticketTitle;
        this.messageBody = messageBody;
        this.dateSent = dateSent;
    }



    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setAgentName(String agentName){this.agentName = agentName;}

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public void setTicketTitle(String ticketTitle){this.ticketTitle = ticketTitle;}
    public String getMessageBody() {
        return messageBody;
    }

    public String getDateSent() {
        return dateSent;
    }
    public String getSenderName() {
        return senderName;
    }

    public String getAgentName(){return agentName;}

    public int getTicketID() {
        return ticketID;
    }

    public int getMessageID() {
        return messageID;
    }

    public String getTicketTitle(){
        return ticketTitle;
    }
}
