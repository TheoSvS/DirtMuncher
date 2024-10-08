package com.dirtmuncher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Stores the robot's current state
 */
@NoArgsConstructor
@Data
public class RobotActivityState {
    private Room room;
    private Coords currPos;
    private int cleanedCounter;
    List<int[]> patches;
    String instructions;

    public RobotActivityState(int[] roomSize, int[] currPos, List<int[]> patches, String instructions, int cleanedCounter) {
        this.room = new Room(roomSize[0],roomSize[1]);
        this.currPos = new Coords(currPos);
        this.cleanedCounter = cleanedCounter;
        this.patches = patches;
        this.instructions = instructions;
    }

    /**
     * Increment the position on the X axis by 1
     */
    public void incrementX() {
        currPos.setXAxis(currPos.getXAxis() + 1);
    }

    /**
     * Decrement the position on the X axis by 1
     */
    public void decrementX() {
        currPos.setXAxis(currPos.getXAxis() - 1);
    }

    /**
     * Increment the position on the Y axis by 1
     */
    public void incrementY() {
        currPos.setYAxis(currPos.getYAxis() + 1);
    }

    /**
     * Decrement the position on the Y axis by 1
     */
    public void decrementY() {
        currPos.setYAxis(currPos.getYAxis() - 1);
    }

}
