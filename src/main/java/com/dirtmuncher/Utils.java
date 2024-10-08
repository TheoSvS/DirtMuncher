package com.dirtmuncher;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Slf4j
public class Utils {

    /**
     * Selects the optimal footprint lookup algorithm based on the ratio of dirty patches to total room size
     * @param dirtyRatio the ratio of dirty patches to total room size
     * @return optimal footprint lookup algorithm
     */
    public static EAlgorithmSelection optimalFootPrintAlgo(double dirtyRatio) {

        if (dirtyRatio <= 0.01) {
            return EAlgorithmSelection.SPARSE_GRID_HASHMAP;
        } else {
            return EAlgorithmSelection.FULL_GRID_2D_ARRAY;
        }
    }

    /**
     * Stores the dirty patches to a sparse grid map
     * @param dirtPatch the dirty patches
     * @return the sparse grid map
     */
    public static HashMap<Integer, HashSet<Integer>> dirtToSparseGridMap(List<int[]> dirtPatch) {
        HashMap<Integer, HashSet<Integer>> dirtMap = new HashMap<>();
        dirtPatch.forEach(pos -> {
            dirtMap.putIfAbsent(pos[0], new HashSet<>());
            dirtMap.get(pos[0]).add(pos[1]);
        });

        return dirtMap;
    }

    /**
     * Stores the full grid 2d array of the room with the dirty patches
     * @param roomSize the room size
     * @param dirtPatch the list of the dirty patches
     * @return the full grid 2d array
     */
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
