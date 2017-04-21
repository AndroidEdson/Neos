package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 20/04/2017.
 */

public final class Ensambles_vendidos_Mes {

    int id;
    String description;
    int qty;
    String date;
    double price_assembly;

    public Ensambles_vendidos_Mes(int id, String description, int qty, String date, double price_assembly) {
        this.id = id;
        this.description = description;
        this.qty = qty;
        this.date = date;
        this.price_assembly = price_assembly;
    }


    public int getId() {
        return id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice_assembly() {
        return price_assembly;
    }

    public void setPrice_assembly(double price_assembly) {
        this.price_assembly = price_assembly;
    }
}
