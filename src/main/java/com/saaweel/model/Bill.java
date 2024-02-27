package com.saaweel.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.InvocationTargetException;

public class Bill {
    private final ObservableList<Product> products;

    public Bill() {
        this.products = FXCollections.observableArrayList();
    }

    public float getTotal() {
        float total = 0;
        for (Product product : products) {
            total += product.getTotal();
        }
        return total;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void reset() {
        products.clear();
    }

    public int getProductCount() {
        return products.size();
    }

    public boolean incraseProductCount(String productName, int count) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                product.setQuantity(product.getQuantity() + count);
                products.set(products.indexOf(product), product);
                return true;
            }
        }
        return false;
    }

    public void decraseProductCount(String productName, int count) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                product.setQuantity(product.getQuantity() - count);
                if (product.getQuantity() == 0) {
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
}
