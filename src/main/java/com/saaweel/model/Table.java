package com.saaweel.model;

public class Table {
    private final int number;
    private Bill bill;
    private boolean occupied;

    public Table(int number, Bill bill, boolean occupied) {
        this.number = number;
        this.bill = bill;
        this.occupied = occupied;
    }

    public Table(int number) {
        this.number = number;
        this.bill = new Bill();
        this.occupied = false;
    }

    public int getNumber() {
        return number;
    }

    public int getBilling() {
        return bill.getTotal();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getProductCount() {
        return bill.getProductCount();
    }
}
