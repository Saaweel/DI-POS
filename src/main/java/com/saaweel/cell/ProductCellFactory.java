package com.saaweel.cell;

import com.saaweel.controller.ProductController;
import com.saaweel.model.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class ProductCellFactory extends ListCell<Product> {
    @Override
    public void updateItem(Product item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null && !empty) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/saaweel/product_list_cell.fxml"));
                Parent root = loader.load();
                ProductController controller = loader.getController();
                controller.setData(item);
                setText(null);
                setGraphic(root);
                setBackground(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            setText(null);
            setGraphic(null);
            setBackground(null);
        }
    }
}