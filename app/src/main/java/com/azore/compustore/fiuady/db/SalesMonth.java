package com.azore.compustore.fiuady.db;

/**
 * Created by Armín on 19/04/2017.
 */

public final class SalesMonth {

    int count;
    double ganancia;

    public SalesMonth(int count, double ganancia) {
        this.count = count;
        this.ganancia = ganancia;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }
}
