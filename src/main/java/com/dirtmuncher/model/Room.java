package com.dirtmuncher.model;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Models the room dimensions
 */
@NoArgsConstructor
@Data
public class Room {
    private int xDim;
    private int yDim;
    private int[] dimensions;

    public Room(int[] dimensions) {
        this.xDim = dimensions[0];
        this.yDim = dimensions[1];
        this.dimensions = dimensions;
    }

    public Room(int xDim, int yDim) {
        this.xDim = xDim;
        this.yDim = yDim;
        this.dimensions = new int[]{xDim,yDim};
    }
}
