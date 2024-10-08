package com.dirtmuncher.services.impl;


import com.dirtmuncher.model.RobotActivityState;
import com.dirtmuncher.services.def.ISimpleAction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Implementation for the robot's supported actions
 */
@Component
public class SimpleAction implements ISimpleAction {
    public SimpleAction() {
    }


    @Override
    public void moveUp(int[] room, RobotActivityState roboState) {
        if (roboState.getCurrPos().getYAxis() + 1 < room[1]) {
            roboState.incrementY();
        }
    }

    @Override
    public void moveDown(int[] room, RobotActivityState roboState) {
        if (roboState.getCurrPos().getYAxis() - 1 >= 0) {
            roboState.decrementY();
        }
    }

    @Override
    public void moveLeft(int[] room, RobotActivityState roboState) {
        if (roboState.getCurrPos().getXAxis() - 1 >= 0) {
            roboState.decrementX();
        }
    }

    @Override
    public void moveRight(int[] room, RobotActivityState roboState) {
        if (roboState.getCurrPos().getXAxis() + 1 < room[0]) {
            roboState.incrementX();
        }
    }

    /**
     * Receives a HashMap containing X coords as key and Y coords within Hashsets as values, and the current robot activity state.
     * It checks whether the position the robot is currently on has dirt and if it is, it cleans the position by removing the X,Y combination from the HashMap
     * @param dirtCoordsMap the HashMap of dirt patches
     * @param robotActivityState the current robot activity state
     */
    public void cleanIfDirty(HashMap<Integer, HashSet<Integer>> dirtCoordsMap, RobotActivityState robotActivityState) {
        int xAxis = robotActivityState.getCurrPos().getXAxis();
        int yAxis = robotActivityState.getCurrPos().getYAxis();
        if (dirtCoordsMap.get(xAxis) != null && dirtCoordsMap.get(xAxis).contains(yAxis)) {
            robotActivityState.setCleanedCounter(robotActivityState.getCleanedCounter() + 1); //increment cleaned patches
            dirtCoordsMap.get(xAxis).remove(yAxis); //also remove this coordinate from the dirty ones
        }
    }


    /**
     * Receives a 2D boolean array containing X coords and Y coords and the current robot activity state.
     * It checks whether the position the robot is currently on has dirt and if it is, it cleans the position by setting the dirtiness of current position to false
     * @param fullGrid2D the HashMap of dirt patches
     * @param robotActivityState the current robot activity state
     */
    public void cleanIfDirty(boolean[][] fullGrid2D, RobotActivityState robotActivityState) {
        int xAxis = robotActivityState.getCurrPos().getXAxis();
        int yAxis = robotActivityState.getCurrPos().getYAxis();
        if (fullGrid2D[xAxis][yAxis]) {
            robotActivityState.setCleanedCounter(robotActivityState.getCleanedCounter() + 1); //increment cleaned patches
            fullGrid2D[xAxis][yAxis] = false; //also remove this coordinate from the dirty ones
        }
    }
}
