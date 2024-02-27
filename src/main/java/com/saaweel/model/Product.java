package com.saaweel.model;

import javafx.scene.image.Image;

public class Product {
    private final String name;
    private final Image image;
    private final float price;
    private int quantity;

    public Product(String name, Image image, float price, int quantity) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public void resetQuantity() {
        quantity = 0;
    }

    public float getTotal() {
        return price * quantity;
    }
}
