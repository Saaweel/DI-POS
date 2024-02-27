package com.saaweel.controller;

import com.saaweel.model.Product;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProductController {
    public Label name;
    public Label description;
    public ImageView productImage;

    public void setData(Product item) {
        name.setText(item.getName());
        description.setText(item.getQuantity() + " x " + item.getPrice() + " € = " + item.getTotal() + " €");
        productImage.setImage(item.getImage());
    }
}
