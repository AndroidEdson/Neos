package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 06/04/2017.
 */

public final class Order_Assemblies {

    int id;
    int assembly_id;
    int qty;

    public Order_Assemblies(int id, int assembly_id, int qty) {
        this.id = id;
        this.assembly_id = assembly_id;
        this.qty = qty;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
