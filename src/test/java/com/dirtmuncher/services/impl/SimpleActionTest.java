package com.dirtmuncher.services.impl;

import com.dirtmuncher.model.Coords;
import com.dirtmuncher.model.RobotActivityState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
class SimpleActionTest {

    private SimpleAction simpleAction;
    private RobotActivityState robotActivityState;
    private Coords coords;

    @BeforeEach
    void setUp() {
        simpleAction = new SimpleAction();
        robotActivityState = new RobotActivityState();
        coords = new Coords();
        robotActivityState.setCurrPos(coords);
    }

    @Test
    void moveUp_shouldIncrementYWithinRoom() {
        int[] room = {5, 5};
        coords.setYAxis(3);
        simpleAction.moveUp(room, robotActivityState);
        assertEquals(4,robotActivityState.getCurrPos().getYAxis());
    }

    @Test
    void moveUp_shouldNotIncrementYOutsideRoom() {
        int[] room = {5, 5};
        coords.setYAxis(4);
        simpleAction.moveUp(room, robotActivityState);
        assertEquals(4,robotActivityState.getCurrPos().getYAxis());
    }

    @Test
    void moveDown_shouldDecrementYWithinRoom() {
        int[] room = {5, 5};
        coords.setYAxis(4);
        simpleAction.moveDown(room, robotActivityState);
        assertEquals(3,robotActivityState.getCurrPos().getYAxis());
    }

    @Test
    void moveDown_shouldNotDecrementYOutsideRoom() {
        int[] room = {5, 5};
        coords.setYAxis(0);
        simpleAction.moveDown(room, robotActivityState);
        assertEquals(0,robotActivityState.getCurrPos().getYAxis());
    }

    @Test
    void moveLeft_shouldDecrementXWithinRoom() {
        int[] room = {5, 5};
        coords.setXAxis(2);
        simpleAction.moveLeft(room, robotActivityState);
        assertEquals(1,robotActivityState.getCurrPos().getXAxis());
    }

    @Test
    void moveLeft_shouldNotDecrementXOutsideRoom() {
        int[] room = {5, 5};
        coords.setXAxis(0);
        simpleAction.moveLeft(room, robotActivityState);
        assertEquals(0,robotActivityState.getCurrPos().getXAxis());
    }

    @Test
    void moveRight_shouldIncrementXWithinRoom() {
        int[] room = {5, 5};
        coords.setXAxis(2);
        simpleAction.moveRight(room, robotActivityState);
        assertEquals(3,robotActivityState.getCurrPos().getXAxis());
    }

    @Test
    void moveRight_shouldNotIncrementXOutsideRoom() {
        int[] room = {5, 5};
        coords.setXAxis(4);
        simpleAction.moveRight(room, robotActivityState);
        assertEquals(4,robotActivityState.getCurrPos().getXAxis());
    }

    @Test
    void cleanIfDirty_withHashMap_shouldCleanDirtyPatch() {
        HashMap<Integer, HashSet<Integer>> dirtCoordsMap = new HashMap<>();
        dirtCoordsMap.put(2, new HashSet<>(Set.of(3)));
        coords.setCoords(new int[]{2,3});

        simpleAction.cleanIfDirty(dirtCoordsMap, robotActivityState);

        assertFalse(dirtCoordsMap.get(2).contains(3));
        assertEquals(1, robotActivityState.getCleanedCounter());
    }

    @Test
    void cleanIfDirty_with2DArray_shouldCleanDirtyPatch() {
        boolean[][] fullGrid2D = new boolean[5][5];
        fullGrid2D[2][3] = true;
        coords.setCoords(new int[]{2,3});

        simpleAction.cleanIfDirty(fullGrid2D, robotActivityState);

        assertFalse(fullGrid2D[2][3]);
        assertEquals(1, robotActivityState.getCleanedCounter());
    }
}