package com.dirtmuncher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Coords {

    private int xAxis;
    private int yAxis;

    public Coords(int[] coords) {
        this.xAxis = coords[0];
        this.yAxis = coords[1];
    }

    public Coords(int xAxis,int yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
}
