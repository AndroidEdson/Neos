package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 03/04/2017.
 */

public final class Assemblies {
    private  int id;
    private  String description;


    public Assemblies(int id, String description) {
        this.id = id;
        this.description = description;
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
}
