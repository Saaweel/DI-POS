package com.saaweel.model;

import javafx.scene.image.Image;

import java.sql.SQLException;

public class Product {
    private int id;
    private final String name;
    private final Image image;
    private final float price;
    private int amount;

    public Product(int id, String name, Image image, float price, int amount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.amount = amount;
    }

    public Product(String name, Image image, float price, int amount, int tableId) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.amount = amount;

        try {
            this.id = DataBase.createProduct(this, tableId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;

        if (this.amount == 0) {
            DataBase.deleteProduct(this.id);
        } else {
            DataBase.setProductAmount(this.id, this.amount);
        }
    }

    public float getTotal() {
        return price * amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
