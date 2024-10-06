package com.dirtmuncher.services.def;

import com.dirtmuncher.model.RobotActivityState;

import java.util.HashMap;
import java.util.HashSet;

public interface ISimpleAction {
    void moveUp(int[] room, RobotActivityState robotActivityState);

    void moveDown(int[] room, RobotActivityState robotActivityState);

    void moveLeft(int[] room, RobotActivityState robotActivityState);

    void moveRight(int[] room, RobotActivityState robotActivityState);

    void cleanIfDirty(HashMap<Integer, HashSet<Integer>> dirtCoordsMap, RobotActivityState robotActivityState);

    void cleanIfDirty(boolean[][] fullGrid2D, RobotActivityState robotActivityState);

}
