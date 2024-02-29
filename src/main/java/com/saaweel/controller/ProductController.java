package com.saaweel.controller;

import com.saaweel.model.Product;
import com.saaweel.model.Table;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProductController {
    public Label name;
    public Label description;
    public ImageView productImage;
    private Product product;
    private Table table;

    public void removeProduct() {
        table.decraseProductCount(product.getName(), 1);
    }

    public void setData(Product item, Table table) {
        name.setText(item.getName());
        description.setText(item.getAmount() + " x " + item.getPrice() + " € = " + item.getTotal() + " €");
        productImage.setImage(item.getImage());

        this.product = item;
        this.table = table;
    }
}
