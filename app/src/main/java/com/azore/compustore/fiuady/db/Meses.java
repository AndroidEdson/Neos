package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 19/04/2017.
 */

public final class Meses {
 private  String name;
    private int num;
    private double ganancia;
    private  int thumbanail;

    public Meses(String name, int num, double ganancia, int thumbanail) {
        this.name = name;
        this.num = num;
        this.ganancia = ganancia;
        this.thumbanail = thumbanail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

    public int getThumbanail() {
        return thumbanail;
    }

    public void setThumbanail(int thumbanail) {
        this.thumbanail = thumbanail;
    }
}
