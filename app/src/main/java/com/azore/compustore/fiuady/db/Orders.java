package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 06/04/2017.
 */

public final class Orders {

    int id;
    int status_id;
    int costumer_id;
    String date;
    String change_log;

    public Orders(int id, int status_id, int costumer_id, String date, String change_log) {
        this.id = id;
        this.status_id = status_id;
        this.costumer_id = costumer_id;
        this.date = date;
        this.change_log = change_log;
    }

    public int getId() {
        return id;
    }


    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getCostumer_id() {
        return costumer_id;
    }

    public void setCostumer_id(int costumer_id) {
        this.costumer_id = costumer_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChange_log() {
        return change_log;
    }

    public void setChange_log(String change_log) {
        this.change_log = change_log;
    }
}
