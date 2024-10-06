package com.dirtmuncher;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Slf4j
public class Utils {

    public static EAlgorithmSelection optimalFootPrintAlgo(double dirtyRatio) {

        if (dirtyRatio <= 0.01) {
            return EAlgorithmSelection.SPARSE_GRID_HASHMAP;
        } else {
            return EAlgorithmSelection.FULL_GRID_2D_ARRAY;
        }
    }

    public static HashMap<Integer, HashSet<Integer>> dirtToSparseGridMap(List<int[]> dirtPatch) {
        HashMap<Integer, HashSet<Integer>> dirtMap = new HashMap<>();
        dirtPatch.forEach(pos -> {
            dirtMap.putIfAbsent(pos[0], new HashSet<>());
            dirtMap.get(pos[0]).add(pos[1]);
        });

        return dirtMap;
    }

    public static boolean[][] dirtToFull2DGrid(int[] roomSize, List<int[]> dirtPatch) {
        //2D boolean array stores the whole floor grid and marks dirty patches
        int xDim = roomSize[0];
        int yDim = roomSize[1];
        boolean[][] grid = new boolean[roomSize[0]][roomSize[1]];
        dirtPatch.forEach(coord -> {
            if (xDim > coord[0] && yDim > coord[1]) {
                grid[coord[0]][coord[1]] = true;
            } else {
                //We could have the check during input validation.
                // However if we don't mind just skipping invalid patches, this is more performant (only iterate the dirt patches once here)
                log.info("Received dirt patch out of room bounds. Skipping patch");
            }
        });
        return grid;
    }
}
