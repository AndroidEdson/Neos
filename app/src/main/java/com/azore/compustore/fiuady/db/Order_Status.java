package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 06/04/2017.
 */

public final class Order_Status {


    int id;
    int description;
    int editable;
    String previous;
    String next;


    public Order_Status(int id, int description, int editable, String previous, String next) {


        this.id = id;
        this.description = description;
        this.editable = editable;
        this.previous = previous;
        this.next = next;


    }

    public int getId() {
        return id;
    }


    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getEditable() {
        return editable;
    }

    public void setEditable(int editable) {
        this.editable = editable;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }


}
