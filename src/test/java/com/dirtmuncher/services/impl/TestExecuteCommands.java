package com.dirtmuncher.services.impl;

import com.dirtmuncher.model.Coords;
import com.dirtmuncher.model.RobotActivityState;
import com.dirtmuncher.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestExecuteCommands {

    private ExecuteCommands executeCommands;
    private SimpleAction simpleAction;
    private RobotActivityState robotActivityState;

    @BeforeEach
    void setUp() {
        simpleAction = new SimpleAction();
        executeCommands = new ExecuteCommands(simpleAction);
        robotActivityState = new RobotActivityState();
    }

    @Test
    void executePlan_shouldMoveAndCleanProperly_sparseGrid() {
        List<int[]> dirtPatches = List.of(
                new int[]{3, 3}
        );

        robotActivityState.setRoom(new Room(10,10));
        robotActivityState.setCurrPos(new Coords(1,2));
        robotActivityState.setPatches(dirtPatches);
        robotActivityState.setInstructions("NNESEESWNWW");

        // Execute the commands
        executeCommands.executePlan(robotActivityState);

        // Assert the final state (robot's position and cleaned dirt count)
        Coords finalCoords = robotActivityState.getCurrPos();
        assertEquals(1, finalCoords.getXAxis());
        assertEquals(3, finalCoords.getYAxis());
        assertEquals(1, robotActivityState.getCleanedCounter());
    }

    @Test
    void executePlan_shouldMoveAndCleanProperly_fullGrid() {
        List<int[]> largeDirtPatches = List.of(
                new int[]{3, 4},
                new int[]{5, 6},
                new int[]{7, 8}
        );
        robotActivityState.setRoom(new Room(10, 10));
        robotActivityState.setCurrPos(new Coords(3, 3));
        robotActivityState.setPatches(largeDirtPatches);
        robotActivityState.setInstructions("NNEESW");

        // Execute the commands
        executeCommands.executePlan(robotActivityState);

        // Assert the final state
        Coords finalCoords = robotActivityState.getCurrPos();
        assertEquals(4, finalCoords.getXAxis());
        assertEquals(4, finalCoords.getYAxis());
        assertEquals(1, robotActivityState.getCleanedCounter());
    }

    @Test
    void executePlan_shouldNotMoveOutsideRoom() {
        // Set initial state where robot is at the edge of the room
        robotActivityState.setRoom(new Room(5, 5));
        robotActivityState.setPatches(new ArrayList<>());
        robotActivityState.setCurrPos(new Coords(4, 4)); // Robot at upper-right corner
        robotActivityState.setInstructions("NNEE"); // Trying to move out of bounds

        // Execute the commands
        executeCommands.executePlan(robotActivityState);

        // Assert the final state (robot should not move out of bounds)
        Coords finalCoords = robotActivityState.getCurrPos();
        assertEquals(4, finalCoords.getXAxis()); // Should stay at the edge
        assertEquals(4, finalCoords.getYAxis()); // Should stay at the edge
        assertEquals(0, robotActivityState.getCleanedCounter()); // No dirt cleaned
    }

    @Test
    void executePlan_shouldCleanAllPatches() {
        // Set initial state with multiple dirt patches
        robotActivityState.setRoom(new Room(5, 5));
        robotActivityState.setCurrPos(new Coords(1, 2));
        robotActivityState.setPatches(List.of(
                new int[]{1, 2},
                new int[]{2, 3},
                new int[]{3, 3}
        ));
        robotActivityState.setInstructions("NNESEESWNWW");

        // Execute the commands
        executeCommands.executePlan(robotActivityState);

        // Assert the robot has cleaned all patches
        Coords finalCoords = robotActivityState.getCurrPos();
        assertEquals(1, finalCoords.getXAxis());
        assertEquals(3, finalCoords.getYAxis());
        assertEquals(3, robotActivityState.getCleanedCounter()); // All patches cleaned
    }
}
