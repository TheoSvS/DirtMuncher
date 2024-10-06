package com.dirtmuncher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Room {
    private int xDim;
    private int yDim;

    public Room(int[] dimensions) {
        this.xDim = dimensions[0];
        this.yDim = dimensions[1];
    }

    public Room(int xDim, int yDim) {
        this.xDim = xDim;
        this.yDim = yDim;
    }
}
