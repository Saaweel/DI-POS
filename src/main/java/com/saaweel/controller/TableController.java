package com.saaweel.controller;

import com.saaweel.model.Table;

public class TableController {
    private final Table table;

    public TableController(Table table) {
        this.table = table;
    }

    public void initialize() {
        // Temporary list of products
        // Array de productos que contenga el menu (Nombre, Precio e URL de la imagen)
        String[][] products = {
                {"Coca Cola", "2.50", "https://i.imgur.com/UI9K44h.jpeg"},
                {"Papas", "3.50", "https://i.imgur.com/UI9K44h.jpeg"},
                {"Hamburguesa", "5.50", "https://i.imgur.com/UI9K44h.jpeg"},
                {"Pizza", "7.50", "https://i.imgur.com/UI9K44h.jpeg"},
                {"Helado", "4.50", "https://i.imgur.com/UI9K44h.jpeg"},
                {"Cerveza", "3.50", "https://i.imgur.com/UI9K44h.jpeg"},
                {"Vino", "5.50", "https://i.imgur.com/UI9K44h.jpeg"},
                {"Café", "2.50", "https://i.imgur.com/UI9K44h.jpeg"},
                {"Té", "2.50", "https://i.imgur.com/UI9K44h.jpeg"},
                {"Agua", "1.50", "https://i.imgur.com/UI9K44h.jpeg"},
        };



        System.out.printf("Table %d initialized\n", this.table.getNumber());
    }
}
