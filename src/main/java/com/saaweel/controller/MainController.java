package com.saaweel.controller;

import com.saaweel.App;
import com.saaweel.model.Table;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainController {
    public AnchorPane root ;
    private List<Table> tables;

    public void initialize() {
        this.tables = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Table table = new Table(i + 1);
            this.addTable(table);
        }
    }

    public void addTable(Table table) {
        ImageView tableButton = (ImageView) root.lookup("#table" + table.getNumber());

        String tableType = tableButton.getImage().getUrl().contains("large") ? "table-large" : "table";

        if (table.isOccupied()) {
            tableButton.setImage(new Image(Objects.requireNonNull(App.class.getResourceAsStream(tableType + "-occupied.png"))));
        } else {
            tableButton.setImage(new Image(Objects.requireNonNull(App.class.getResourceAsStream(tableType + ".png"))));
        }

        tableButton.onMouseClickedProperty().set(event -> {
            try {
                this.openTable(table.getNumber());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Label tableLabel = (Label) root.lookup("#tableLabel" + table.getNumber());

        if (tableLabel != null) {
            tableLabel.setText("Mesa " + table.getNumber() + "\n" + table.getBilling() + " €");
        }

        tables.add(table);
    }

    public void updateTableButton(Table table) {
        ImageView tableButton = (ImageView) root.lookup("#table" + table.getNumber());

        String tableType = tableButton.getStyleClass().contains("large") ? "table-large" : "table";

        if (table.isOccupied()) {
            tableButton.setImage(new Image(Objects.requireNonNull(App.class.getResourceAsStream(tableType + "-occupied.png"))));
        } else {
            tableButton.setImage(new Image(Objects.requireNonNull(App.class.getResourceAsStream(tableType + ".png"))));
        }

        Label tableLabel = (Label) root.lookup("#tableLabel" + table.getNumber());

        if (tableLabel != null) {
            tableLabel.setText("Mesa " + table.getNumber() + "\n" + table.getBilling() + " €");
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

            fxmlLoader.setController(new TableController(table, stage));

            stage.setScene(new Scene(fxmlLoader.load(), 1080, 720));

            stage.show();

            stage.setOnCloseRequest(event -> {
                if (table.getProductCount() == 0) {
                    table.setOccupied(false);
                }

                this.updateTableButton(table);
            });
        }
    }
}
