package com.dirtmuncher.services.impl;

import com.dirtmuncher.Utils;
import com.dirtmuncher.model.RobotActivityState;
import com.dirtmuncher.services.def.IExecuteCommands;
import com.dirtmuncher.services.def.ISimpleAction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Component
public class ExecuteCommands implements IExecuteCommands {

    private final ISimpleAction iSimpleAction;


    public ExecuteCommands(ISimpleAction iSimpleAction) {
        this.iSimpleAction = iSimpleAction;
    }

    @Override
    public void executePlan(RobotActivityState robotActivity) {
        int xDim = robotActivity.getRoom().getXDim();
        int yDim = robotActivity.getRoom().getYDim();
        int dirtPatchCount = robotActivity.getPatches().size();
        List<int[]> dirtPatches = robotActivity.getPatches();
        double dirtRatio = (double) dirtPatchCount / (xDim * yDim);

        HashMap<Integer, HashSet<Integer>> dirtCoordsMap;
        boolean[][] grid;
        Runnable cleanIfDirtyRunnable;

        switch (Utils.optimalFootPrintAlgo(dirtRatio)) {
            case SPARSE_GRID_HASHMAP -> {
                dirtCoordsMap = Utils.dirtToSparseGridMap(dirtPatches);
                cleanIfDirtyRunnable = () -> iSimpleAction.cleanIfDirty(dirtCoordsMap, robotActivity);
            }
            case FULL_GRID_2D_ARRAY -> {
                grid = Utils.dirtToFull2DGrid(robotActivity.getRoom().getDimensions(), dirtPatches);
                cleanIfDirtyRunnable = () -> iSimpleAction.cleanIfDirty(grid, robotActivity);
            }
            default -> {
                throw new UnsupportedOperationException("Not supported lookup algorithm");
            }
        }

        doCommands(robotActivity, cleanIfDirtyRunnable);
    }

    private void doCommands(RobotActivityState robotActivity, Runnable cleanIfDirtyRunnable) {
        for (int i = 0; i < robotActivity.getInstructions().length(); i++) {
            char command = robotActivity.getInstructions().charAt(i);
            switch (command) {
                case 'N' -> {
                    iSimpleAction.moveUp(robotActivity.getRoom().getDimensions(), robotActivity);
                }
                case 'S' -> {
                    iSimpleAction.moveDown(robotActivity.getRoom().getDimensions(), robotActivity);
                }
                case 'W' -> {
                    iSimpleAction.moveRight(robotActivity.getRoom().getDimensions(), robotActivity);
                }
                case 'E' -> {
                    iSimpleAction.moveLeft(robotActivity.getRoom().getDimensions(), robotActivity);
                }
            }
            cleanIfDirtyRunnable.run();
        }
    }
}
