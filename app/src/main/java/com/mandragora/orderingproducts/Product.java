package com.mandragora.orderingproducts;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private float quantity;
    private boolean toOrder;

    public Product(int id, String name, float quantity, boolean toOrder) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.toOrder = toOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public boolean isToOrder() {
        return toOrder;
    }

    public void setToOrder(boolean toOrder) {
        this.toOrder = toOrder;
    }
}
