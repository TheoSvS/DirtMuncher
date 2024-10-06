package com.dirtmuncher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
@NoArgsConstructor
public class DirtPatchMap {
    private HashMap<Integer, HashSet<Integer>> dirtMap = new HashMap<>();
    private int cleanedPatches = 0;

    public DirtPatchMap(int[][] patchCoordsArray) {
        // key of the HashMap is the x coord and the value is a HashSet containing the y coordinate(s)
        Arrays.stream(patchCoordsArray).forEach(coords -> {
            dirtMap.putIfAbsent(coords[0], new HashSet<>());
            dirtMap.get(coords[0]).add(coords[1]);
        });
    }

    public boolean cleanDirtyPatch(int xCoord, int yCoord) {
        HashSet<Integer> yAxisHashSet = dirtMap.get(xCoord);
        if (yAxisHashSet != null) {  // Check if xAxis coord exists in the dirtMap
            boolean removed = yAxisHashSet.remove(yCoord);
            if (removed) {
                cleanedPatches++;  // Increment cleaned patches
                if (yAxisHashSet.isEmpty()) {
                    dirtMap.remove(xCoord);  // Remove xAxis coord if no more yAxis coordinates exist for it
                }
                return true;
            }
        }
        return false;
    }

}
