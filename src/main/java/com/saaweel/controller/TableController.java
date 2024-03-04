package com.saaweel.controller;

import com.saaweel.App;
import com.saaweel.cell.ProductCellFactory;
import com.saaweel.model.DataBase;
import com.saaweel.model.Product;
import com.saaweel.model.Table;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TableController {
    private Table table;
    private final Stage stage;
    public TabPane productsPane;
    public ListView<Product> tableBillListView;
    public Label totalText;


    public TableController(Table table, Stage stage) {
        this.table = table;
        this.stage = stage;
    }

    public void initialize() {
        // Array de productos que contenga el menu (Nombre, Precio, URL de la imagen, Categoría)
        String[][] products = {
                {"Coca Cola", "2.50", "https://i.imgur.com/XAFkgry.png", "Bebidas"},
                {"Patatas", "3.50", "https://i.imgur.com/F70Iuoq.png", "Entrantes"},
                {"Hamburguesa", "5.50", "https://i.imgur.com/OIuTFdq.png", "Comida rápida"},
                {"Pizza", "7.50", "https://i.imgur.com/dugo045.png", "Comida rápida"},
                {"Helado", "4.50", "https://i.imgur.com/Ue5HZ3N.png", "Postres"},
                {"Tarta", "5.50", "https://i.imgur.com/zoFpvmT.png", "Postres"},
                {"Cerveza", "3.50", "https://i.imgur.com/Y1lwEVJ.png", "Bebidas alcohólicas"},
                {"Vino", "5.50", "https://i.imgur.com/Sd8aFG6.png", "Bebidas alcohólicas"},
                {"Café", "2.50", "https://i.imgur.com/jZqBKjj.png", "Bebidas"},
                {"Té", "2.50", "https://i.imgur.com/GQC9OgF.png", "Bebidas"},
                {"Agua", "1.50", "https://i.imgur.com/jagDrg7.png", "Bebidas"},
                {"Aquarius", "2.50", "https://i.imgur.com/F03p1iT.png", "Bebidas"}
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
                if (!this.table.incraseProductCount(product[0], 1)) {
                    this.table.addProduct(new Product(product[0], productButton.getImage(), Float.parseFloat(product[1]), 1, this.table.getId()));
                }
            });

            categoryGrid.add(productButton, categoryGrid.getChildren().size() % categoryGrid.getColumnCount(), categoryGrid.getChildren().size() / categoryGrid.getColumnCount());

            categoryTab.setContent(categoryGrid);
        }

        tableBillListView.setItems(this.table.getProducts());
        tableBillListView.setCellFactory(listView -> new ProductCellFactory(this.table));
        Label placeholder = new Label("No hay productos en la mesa");
        placeholder.setStyle("-fx-text-fill: white;");
        tableBillListView.setPlaceholder(placeholder);

        this.table.getProducts().addListener((ListChangeListener<Product>) c -> totalText.setText("Total: " + table.getBilling() + " €"));

        totalText.setText("Total: " + table.getBilling() + " €");
    }

    public void clearTable() {
        this.table.getProducts().clear();

        DataBase.clearTable(this.table.getId());

        stage.close();
        stage.getOnCloseRequest().handle(null);
    }

    public void payBill() {
        this.printBill();

        this.table.getProducts().clear();

        DataBase.setPayedTable(this.table.getId(), true);

        this.table = new Table(this.table.getNumber());

        stage.close();
        stage.getOnCloseRequest().handle(null);
    }

    public void printBill() {
        System.out.println("Imprimiendo factura con id " + this.table.getId());

        try {
            InputStream reportFile = App.class.getResourceAsStream("bill.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("table_id", this.table.getId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DataBase.getConnection());

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
