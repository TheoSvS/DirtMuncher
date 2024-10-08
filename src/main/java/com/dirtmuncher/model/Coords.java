package com.dirtmuncher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Models coordinates
 */
@NoArgsConstructor
@Data
public class Coords {
    private int[] coords = new int[2];

    public Coords(int[] coords) {
        this.coords = coords;
    }

    public Coords(int xAxis, int yAxis) {
        this.coords[0] = xAxis;
        this.coords[1] = yAxis;
    }

    public void setXAxis(int xAxis) {
        this.coords[0] = xAxis;
    }

    public void setYAxis(int yAxis) {
        this.coords[1] = yAxis;
    }

    public int getXAxis() {
        return this.coords[0];
    }

    public int getYAxis() {
        return this.coords[1];
    }
}
