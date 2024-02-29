package com.saaweel.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class Table {
    private int id;
    private final int number;
    private final ObservableList<Product> products;
    private boolean occupied;

    public Table(int number) {
        this.number = number;
        this.products = FXCollections.observableArrayList();
        this.occupied = false;

        try {
            int id = DataBase.getTableByNumber(number);

            if (id != -1) {
                this.id = id;

                ArrayList<Product> products = DataBase.getProductsByTableId(id);

                this.products.addAll(products);

                if (!this.products.isEmpty()) {
                    this.occupied = true;
                }
            } else {
                this.id = DataBase.createTable(number);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumber() {
        return number;
    }

    public float getBilling() {
        float total = 0;
        for (Product product : products) {
            total += product.getTotal();
        }
        return total;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getProductCount() {
        return products.size();
    }

    public boolean incraseProductCount(String productName, int count) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                product.setAmount(product.getAmount() + count);
                products.set(products.indexOf(product), product);
                return true;
            }
        }
        return false;
    }

    public void decraseProductCount(String productName, int count) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                product.setAmount(product.getAmount() - count);
                if (product.getAmount() == 0) {
                    products.remove(product);
                } else {
                    products.set(products.indexOf(product), product);
                }
                return;
            }
        }
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
