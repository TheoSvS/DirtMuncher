package com.dirtmuncher.services.impl;


import com.dirtmuncher.model.RobotActivityState;
import com.dirtmuncher.services.def.ISimpleAction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

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

    public void cleanIfDirty(HashMap<Integer, HashSet<Integer>> dirtCoordsMap, RobotActivityState robotActivityState) {
        int xAxis = robotActivityState.getCurrPos().getXAxis();
        int yAxis = robotActivityState.getCurrPos().getYAxis();
        if (dirtCoordsMap.get(xAxis) != null && dirtCoordsMap.get(xAxis).contains(yAxis)) {
            robotActivityState.setCleanedCounter(robotActivityState.getCleanedCounter() + 1); //increment cleaned patches
            dirtCoordsMap.get(xAxis).remove(yAxis); //also remove this coordinate from the dirty ones
        }
    }


    public void cleanIfDirty(boolean[][] fullGrid2D, RobotActivityState robotActivityState) {
        int xAxis = robotActivityState.getCurrPos().getXAxis();
        int yAxis = robotActivityState.getCurrPos().getYAxis();
        if (fullGrid2D[xAxis][yAxis]) {
            robotActivityState.setCleanedCounter(robotActivityState.getCleanedCounter() + 1); //increment cleaned patches
            fullGrid2D[xAxis][yAxis] = false; //also remove this coordinate from the dirty ones
        }
    }
}
