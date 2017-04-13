package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 12/04/2017.
 */

public final class OrdenesUnion {

    int id;
    int id_status ;
    String status_description;
    int id_customer;
    String first_name;
    String last_name;
    double costo ;
    String date;

    public OrdenesUnion(int id, int id_status, String status_description, int id_customer, String first_name, String last_name, double costo, String date) {
        this.id = id;
        this.id_status = id_status;
        this.status_description = status_description;
        this.id_customer = id_customer;
        this.first_name = first_name;
        this.last_name = last_name;
        this.costo = costo;
        this.date = date;
    }


    public int getId() {
        return id;
    }


    public int getId_status() {
        return id_status;
    }



    public String getStatus_description() {
        return status_description;
    }

    public void setStatus_description(String status_description) {
        this.status_description = status_description;
    }

    public int getId_customer() {
        return id_customer;
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
