package com.saaweel.model;

import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    public static Connection connection;

    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost/di_pos", "root", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getTableByNumber(int number) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM tables WHERE number = ? AND payed = 0");

        statement.setInt(1, number);

        statement.execute();

        ResultSet resultSet = statement.getResultSet();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            return -1;
        }
    }

    public static ArrayList<Product> getProductsByTableId(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE table_id = ?");

        statement.setInt(1, id);

        statement.execute();

        ResultSet resultSet = statement.getResultSet();

        ArrayList<Product> products = new ArrayList<>();

        while (resultSet.next()) {
            products.add(new Product(resultSet.getInt("id"), resultSet.getString("name"), new Image(resultSet.getString("image")), resultSet.getFloat("price"), resultSet.getInt("amount")));
        }

        return products;
    }

    public static int createTable(int number) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO tables (number, payed) VALUES (?, 0)", Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, number);

        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();

        resultSet.next();

        return resultSet.getInt(1);
    }

    public static int createProduct(Product product, int tableId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO products (name, table_id, image, price, amount) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, product.getName());
        statement.setInt(2, tableId);
        statement.setString(3, product.getImage().getUrl());
        statement.setFloat(4, product.getPrice());
        statement.setInt(5, product.getAmount());

        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();

        resultSet.next();

        return resultSet.getInt(1);
    }

    public static void setProductAmount(int id, int amount) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE products SET amount = ? WHERE id = ?");

            statement.setInt(1, amount);
            statement.setInt(2, id);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteProduct(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?");

            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearTable(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE table_id = ?");

            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPayedTable(int id, boolean payed) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE tables SET payed = ? WHERE id = ?");

            statement.setBoolean(1, payed);
            statement.setInt(2, id);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
