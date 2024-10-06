package com.dirtmuncher.services.impl;

import com.dirtmuncher.Utils;
import com.dirtmuncher.model.RobotActivityState;
import com.dirtmuncher.requests.RobotActivityReqDTO;
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
    public RobotActivityState executePlan(RobotActivityReqDTO actReqDto) {
        int xDim = actReqDto.getRoomSize()[0];
        int yDim = actReqDto.getRoomSize()[1];
        int dirtPatchCount = actReqDto.getPatches().size();
        List<int[]> dirtPatches = actReqDto.getPatches();
        double dirtRatio = (double) dirtPatchCount/(xDim * yDim) ;

        RobotActivityState robotActivityState = new RobotActivityState(actReqDto.getCoords(), 0);

        HashMap<Integer, HashSet<Integer>> dirtCoordsMap;
        boolean[][] grid;
        Runnable cleanIfDirtyRunnable;

        switch (Utils.optimalFootPrintAlgo(dirtRatio)) {
            case SPARSE_GRID_HASHMAP -> {
                dirtCoordsMap = Utils.dirtToSparseGridMap(dirtPatches);
                cleanIfDirtyRunnable = () -> iSimpleAction.cleanIfDirty(dirtCoordsMap, robotActivityState);
            }
            case FULL_GRID_2D_ARRAY -> {
                grid = Utils.dirtToFull2DGrid(actReqDto.getRoomSize(), dirtPatches);
                cleanIfDirtyRunnable = () -> iSimpleAction.cleanIfDirty(grid, robotActivityState);
            }
            default -> {
                throw new UnsupportedOperationException("Not supported lookup algorithm");
            }
        }

        doCommands(actReqDto, robotActivityState, cleanIfDirtyRunnable);
        return robotActivityState;
    }

    private void doCommands(RobotActivityReqDTO actReqDto, RobotActivityState robotActivityState, Runnable cleanIfDirtyRunnable) {
        for (int i = 0; i < actReqDto.getInstructions().length(); i++) {
            char command = actReqDto.getInstructions().charAt(i);
            switch (command) {
                case 'N' -> {
                    iSimpleAction.moveUp(actReqDto.getRoomSize(), robotActivityState);
                }
                case 'S' -> {
                    iSimpleAction.moveDown(actReqDto.getRoomSize(), robotActivityState);
                }
                case 'W' -> {
                    iSimpleAction.moveRight(actReqDto.getRoomSize(), robotActivityState);
                }
                case 'E' -> {
                    iSimpleAction.moveLeft(actReqDto.getRoomSize(), robotActivityState);
                }
            }
            cleanIfDirtyRunnable.run();
        }
    }
}
