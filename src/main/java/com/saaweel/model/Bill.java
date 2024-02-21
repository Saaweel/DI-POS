package com.saaweel.model;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private final List<Product> products;

    public Bill() {
        this.products = new ArrayList<>();
    }

    public int getTotal() {
        int total = 0;
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
}
