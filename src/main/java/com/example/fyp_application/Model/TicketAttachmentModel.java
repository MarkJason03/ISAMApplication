package com.example.fyp_application.Model;

public class TicketAttachmentModel {


    private int attachmentID;

    private int ticketID;

    private String filePath;

    private String dateUploaded;


    public TicketAttachmentModel(String filePath, String dateUploaded) {
        this.filePath = filePath;
        this.dateUploaded = dateUploaded;
    }

    //getters and setters

    public int getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(int attachmentID) {
        this.attachmentID = attachmentID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }



}
