package com.shoppingapp.abhis.demo_app;

import java.io.Serializable;

public class Model implements Serializable {
    String itemName;
    int itemQty;
    String itemPrice;
    String itemURL;
    int itemRatings;



    public Model() {
    }

    public Model(String itemName,String itemURL, int itemQty, String itemPrice, int itemRatings) {
        this.itemName = itemName;
        this.itemURL=itemURL;
        this.itemQty = itemQty;
        this.itemPrice = itemPrice;
        this.itemRatings = itemRatings;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }
    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemRatings() {
        return itemRatings;
    }

    public void setItemRatings(int itemRatings) {
        this.itemRatings = itemRatings;
    }


}
