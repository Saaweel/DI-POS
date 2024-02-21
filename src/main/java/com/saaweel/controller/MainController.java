package com.saaweel.controller;

import com.saaweel.App;
import com.saaweel.model.Table;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainController {
    public GridPane tablesGrid;
    public List<Table> tables;

    public void initialize() {
        this.tables = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            Table table = new Table(i);
            this.addTable(table);
        }
    }

    public void addTable(Table table) {
        Label tableButton = new Label("Mesa " + table.getNumber() + "\n" + "Cuenta: " + table.getBilling() + " €");

        if (table.isOccupied()) {
            tableButton.getStyleClass().add("table-occupied");
        } else {
            tableButton.getStyleClass().add("table-available");
        }

        tableButton.getStyleClass().add("table-button");

        tableButton.setId("table-" + table.getNumber());

        tableButton.onMouseClickedProperty().set(event -> {
            try {
                this.openTable(table.getNumber());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        tablesGrid.add(tableButton, tablesGrid.getChildren().size() % tablesGrid.getColumnCount(), tablesGrid.getChildren().size() / tablesGrid.getColumnCount());

        tables.add(table);
    }

    public void updateTableButton(Table table) {
        Label tableButton = (Label) tablesGrid.lookup("#table-" + table.getNumber());
        tableButton.setText("Mesa " + table.getNumber() + "\n" + "Cuenta: " + table.getBilling() + " €");

        if (table.isOccupied()) {
            tableButton.getStyleClass().remove("table-available");
            tableButton.getStyleClass().add("table-occupied");
        } else {
            tableButton.getStyleClass().remove("table-occupied");
            tableButton.getStyleClass().add("table-available");
        }
    }

    public void openTable(int tableNumber) throws IOException {
        Table table = tables.stream().filter(tbl -> tbl.getNumber() == tableNumber).findFirst().orElse(null);

        if (table != null) {
            if (!table.isOccupied()) {
                table.setOccupied(true);
                this.updateTableButton(table);
            }

            Stage stage = new Stage();

            Image icon = new Image(Objects.requireNonNull(App.class.getResourceAsStream("appIcon.png")));

            stage.getIcons().add(icon);

            stage.setTitle("Mesa " + table.getNumber());

            FXMLLoader fxmlLoader = App.loadFXML("table");

            fxmlLoader.setController(new TableController(table));

            stage.setScene(new Scene(fxmlLoader.load(), 1080, 720));

            stage.show();

            stage.setOnCloseRequest(event -> {
                if (table.getProductCount() == 0) {
                    table.setOccupied(false);
                    this.updateTableButton(table);
                }
            });
        }
    }
}
