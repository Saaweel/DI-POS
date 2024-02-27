package com.saaweel.controller;

import com.saaweel.model.Bill;
import com.saaweel.model.Product;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProductController {
    public Label name;
    public Label description;
    public ImageView productImage;
    private Product product;
    private Bill bill;

    public void removeProduct() {
        bill.decraseProductCount(product.getName(), 1);
    }

    public void setData(Product item, Bill bill) {
        name.setText(item.getName());
        description.setText(item.getQuantity() + " x " + item.getPrice() + " € = " + item.getTotal() + " €");
        productImage.setImage(item.getImage());

        this.product = item;
        this.bill = bill;
    }
}
