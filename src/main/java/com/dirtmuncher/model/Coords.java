package com.dirtmuncher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Models coordinates
 */
@NoArgsConstructor
@Data
public class Coords {

    private int xAxis;
    private int yAxis;
    private int[] coords;

    public Coords(int[] coords) {
        this.xAxis = coords[0];
        this.yAxis = coords[1];
        this.coords = coords;
    }

    public Coords(int xAxis, int yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.coords = new int[]{xAxis,yAxis};
    }
}
