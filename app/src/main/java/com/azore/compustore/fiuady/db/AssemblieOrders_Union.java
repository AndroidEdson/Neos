package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 16/04/2017.
 */

public final class AssemblieOrders_Union {

    int id;
    int assembly_id;
    String description;
    int qty;
    double price;

    public AssemblieOrders_Union(int id, int assembly_id, String description, int qty, double price) {
        this.id = id;
        this.assembly_id = assembly_id;
        this.description = description;
        this.qty = qty;
        this.price = price;
    }

    public int getId() {
        return id;
    }


    public int getAssembly_id() {
        return assembly_id;
    }

    public void setAssembly_id(int assembly_id) {
        this.assembly_id = assembly_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
