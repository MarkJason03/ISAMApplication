package com.example.fyp_application.Model;

public class KnowledgeBaseModel {


    private int knowledgeBaseID;
    private String knowledgeBaseInfo;

    public KnowledgeBaseModel(int knowledgeBaseID, String knowledgeBaseInfo) {
        this.knowledgeBaseID = knowledgeBaseID;
        this.knowledgeBaseInfo = knowledgeBaseInfo;
    }

    public int getKnowledgeBaseID() {
        return knowledgeBaseID;
    }


    public String getKnowledgeBaseInfo() {
        return knowledgeBaseInfo;
    }

    public void setKnowledgeBaseID(int knowledgeBaseID) {
        this.knowledgeBaseID = knowledgeBaseID;
    }

    public void setKnowledgeBaseInfo(String knowledgeBaseInfo) {
        this.knowledgeBaseInfo = knowledgeBaseInfo;
    }


    @Override
    public String toString() {
        return knowledgeBaseInfo;
    }
}
