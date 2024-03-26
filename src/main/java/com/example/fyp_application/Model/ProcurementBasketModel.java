package com.example.fyp_application.Model;

public class ProcurementBasketModel {

    private int basketID;

    private int catalogID;



    private int quantity;


    private int totalCost;



    public ProcurementBasketModel(int catalogID, int quantity, int totalCost) {
        this.catalogID = catalogID;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }



    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
    public int getBasketID() {
        return basketID;
    }

    public void setBasketID(int basketID) {
        this.basketID = basketID;
    }

    public int getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(int catalogID) {
        this.catalogID = catalogID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
