package com.Fabliha.garden2.ui.cart.model;


public class CartItem {

    String name;
    int price;
    String type;
    int quantity=0;

    public CartItem(){

    }
    public CartItem(String name, int price, String type, int quantity) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity=quantity;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increQuantity() {
        this.quantity ++;
    }

    public void decreQuantity() {
        if(this.quantity>0)
        {
            this.quantity --;
        }

    }

    @Override
    public String toString() {
        return "CartItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
