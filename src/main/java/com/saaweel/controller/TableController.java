package com.saaweel.controller;

import com.saaweel.cell.ProductCellFactory;
import com.saaweel.model.Product;
import com.saaweel.model.Table;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.concurrent.atomic.AtomicBoolean;

public class TableController {
    private final Table table;
    public TabPane productsPane;
    public ListView<Product> tableBillListView;
    public Label totalText;


    public TableController(Table table) {
        this.table = table;
    }

    public void initialize() {
        // Temporary list of products
        // Array de productos que contenga el menu (Nombre, Precio, URL de la imagen, Categoría)
        String[][] products = {
                {"Coca Cola", "2.50", "https://i.imgur.com/UI9K44h.jpeg", "Bebidas"},
                {"Patatas", "3.50", "https://i.imgur.com/UI9K44h.jpeg", "Entrantes"},
                {"Hamburguesa", "5.50", "https://i.imgur.com/UI9K44h.jpeg", "Comida rápida"},
                {"Pizza", "7.50", "https://i.imgur.com/UI9K44h.jpeg", "Comida rápida"},
                {"Helado", "4.50", "https://i.imgur.com/UI9K44h.jpeg", "Postres"},
                {"Tarta", "5.50", "https://i.imgur.com/UI9K44h.jpeg", "Postres"},
                {"Cerveza", "3.50", "https://i.imgur.com/UI9K44h.jpeg", "Bebidas alcohólicas"},
                {"Vino", "5.50", "https://i.imgur.com/UI9K44h.jpeg", "Bebidas alcohólicas"},
                {"Café", "2.50", "https://i.imgur.com/UI9K44h.jpeg", "Bebidas"},
                {"Té", "2.50", "https://i.imgur.com/UI9K44h.jpeg", "Bebidas"},
                {"Agua", "1.50", "https://i.imgur.com/UI9K44h.jpeg", "Bebidas"}
        };

        for (String[] product : products) {
            String imageURL = product[2];
            String category = product[3];

            Tab categoryTab = this.productsPane.getTabs().stream().filter(tab -> tab.getText().equals(category)).findFirst().orElse(null);

            if (categoryTab == null) {
                categoryTab = new Tab(category);
                categoryTab.setClosable(false);
                this.productsPane.getTabs().add(categoryTab);
            }

            GridPane categoryGrid = (GridPane) categoryTab.getContent();

            if (categoryGrid == null) {
                categoryGrid = new GridPane();
                categoryGrid.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints());
            }

            ImageView productButton = new ImageView(imageURL);
            productButton.setFitWidth(100);
            productButton.setFitHeight(100);

            productButton.onMouseClickedProperty().set(event -> {
                if (!this.table.getBill().incraseProductCount(product[0], 1)) {
                    this.table.getBill().addProduct(new Product(product[0], productButton.getImage(), Float.parseFloat(product[1]), 1));
                }
            });

            categoryGrid.add(productButton, categoryGrid.getChildren().size() % categoryGrid.getColumnCount(), categoryGrid.getChildren().size() / categoryGrid.getColumnCount());

            categoryTab.setContent(categoryGrid);
        }

        tableBillListView.setItems(this.table.getBill().getProducts());
        tableBillListView.setCellFactory(listView -> new ProductCellFactory());
        tableBillListView.setPlaceholder(new Label("No hay productos en la mesa"));

        this.table.getBill().getProducts().addListener((ListChangeListener<Product>) c -> totalText.setText("Total: " + table.getBilling() + " €"));

        totalText.setText("Total: " + table.getBilling() + " €");
    }
}
