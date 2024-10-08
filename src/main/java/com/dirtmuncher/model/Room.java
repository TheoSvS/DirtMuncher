package com.dirtmuncher.model;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Models the room dimensions
 */
@NoArgsConstructor
@Data
public class Room {
    private int[] dimensions = new int[2];

    public Room(int[] dimensions) {
        this.dimensions = dimensions;
    }

    public Room(int xDim, int yDim) {
        this.dimensions[0] = xDim;
        this.dimensions[1] = yDim;
    }

    public void setXDim(int xDim) {
        this.dimensions[0] = xDim;
    }

    public void setYDim(int yDim) {
        this.dimensions[1] = yDim;
    }

    public int getXDim() {
        return this.dimensions[0];
    }

    public int getYDim() {
        return this.dimensions[1];
    }
}
