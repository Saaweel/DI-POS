package com.saaweel.model;

import javafx.scene.image.Image;

public class Product {
    private final String name;
    private final Image image;
    private final int price;
    private int quantity;

    public Product(String name, Image image, int price, int quantity) {
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

    public int getPrice() {
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

    public int getTotal() {
        return price * quantity;
    }
}
