package com.dirtmuncher;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@SpringBootTest
class LookupAlgorithmFootprintStressTest {
    static final int MAX_GRID_AREA = 1000000;
    static int gridXsize;
    static int gridYsize;
    static double dirtyRatio;
    static int totalDirtyPatches;

    @Test
    void evaluateAlgorithmSelectionByFootprint_fixedInput() throws InterruptedException {
        gridXsize = 10;
        gridYsize = 10;
        dirtyRatio = 0.02;
        totalDirtyPatches = (int) ((gridXsize * gridYsize) * dirtyRatio);

        rankBySmallestFootprint();

    }

    @Disabled("Skipping this test during maven build")
    @Test
    void evaluateAlgorithmSelectionByFootprint_rangeInputs() throws InterruptedException {
        gridXsize = 10;
        gridYsize = 10;
        int totalTestCases = 0;
        int optimalAlgoSelection = 0;

        while (gridXsize * gridYsize <= MAX_GRID_AREA) {
            for (dirtyRatio = 0.01; dirtyRatio <= 0.05; dirtyRatio += 0.001) {
                try {
                    totalDirtyPatches = (int) ((gridXsize * gridYsize) * dirtyRatio);

                    rankBySmallestFootprint();
                    totalTestCases++;
                    optimalAlgoSelection++;
                } catch (AssertionError e) {
                    totalTestCases++;
                    System.err.println("Assertion failed: " + e.getMessage());
                } catch (InvalidDirtynessInputException e) {
                    System.err.println(e.getMessage());
                }
            }
            if (gridXsize < gridYsize) {
                gridXsize = gridXsize * 10;
            } else {
                gridYsize = gridYsize * 10;
            }
        }
        System.out.println("\n\nOptimal Algo selection:" + (100d * optimalAlgoSelection) / totalTestCases + "% of cases");
    }

    void rankBySmallestFootprint() throws InterruptedException {
        validateParameters();
        List<int[]> dirtPatches = randomPositions();
        List<Callable<Map.Entry<Long, EAlgorithmSelection>>> calcFootPrintCallables = List.of(
                () -> new AbstractMap.SimpleEntry<>(dirtPatchesFootPrint_DirtyCoords_HashSet(dirtPatches), EAlgorithmSelection.CONCAT_COORDS_HASHSET),
                () -> new AbstractMap.SimpleEntry<>(dirtPatchesFootPrint_SparseGrid_HashMap(dirtPatches), EAlgorithmSelection.SPARSE_GRID_HASHMAP),
                () -> new AbstractMap.SimpleEntry<>(dirtPatchesFootPrint_FullGrid_2DArray(dirtPatches), EAlgorithmSelection.FULL_GRID_2D_ARRAY)
        );

        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
            List<Future<Map.Entry<Long, EAlgorithmSelection>>> futResults = executorService.invokeAll(calcFootPrintCallables);

            TreeMap<Long, EAlgorithmSelection> algoRank = new TreeMap<>();
            futResults.forEach(fut -> {
                try {
                    algoRank.put(fut.get().getKey(), fut.get().getValue());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            System.out.println(System.lineSeparator() + "=======================================");
            System.out.println(gridXsize + "X" + gridYsize + " , dirtRatio:" + String.format("%.6f", dirtyRatio) + " , patches:" + totalDirtyPatches);
            algoRank.entrySet().forEach(e -> System.out.println(e.getValue() + String.format("  %.3f", (e.getKey() / 1024d) / 1024d) + "MB"));

            List<Map.Entry<Long, EAlgorithmSelection>> rankedAlgorithms = new ArrayList<>(algoRank.entrySet());
            //Assert that the optimalFootprint algorithm was selected based on the ranking of the algorithms
            assertSame(Utils.optimalFootPrintAlgo(dirtyRatio), rankedAlgorithms.getFirst().getValue());
        }
    }

    long dirtPatchesFootPrint_SparseGrid_HashMap(List<int[]> dirtPatches) {
        //HashMap solution stores ONLY the DIRTY patches
        //randomly filling dirt patches of totalDirtyPatches number, to have them be sparsely positioned in our grid
        HashMap<Integer, HashSet<Integer>> dirtMap = Utils.dirtToSparseGridMap(dirtPatches);

        //System.out.println(GraphLayout.parseInstance(dirtMap).toFootprint());
        return GraphLayout.parseInstance(dirtMap).totalSize();
    }


    long dirtPatchesFootPrint_DirtyCoords_HashSet(List<int[]> dirtPatches) {
        //Set solution stores ONLY the DIRTY patches
        Set<String> set = dirtPatches.stream().map(coord -> coord[0] + "," + coord[1]).collect(Collectors.toSet());

        //System.out.println(GraphLayout.parseInstance(hashSet).toFootprint());
        return GraphLayout.parseInstance(set).totalSize();
    }


    long dirtPatchesFootPrint_FullGrid_2DArray(List<int[]> dirtPatches) {
        //2D boolean array stores the whole floor grid and marks dirty patches
        boolean[][] grid = Utils.dirtToFull2DGrid(new int[]{gridXsize, gridYsize}, dirtPatches);

        //System.out.println(GraphLayout.parseInstance(grid).toFootprint());
        return GraphLayout.parseInstance(grid).totalSize();
    }

    List<int[]> randomPositions() {
        List<int[]> randomPos = new Random()
                .ints(0, gridXsize * gridYsize)
                .distinct().limit(totalDirtyPatches)
                .mapToObj(num -> new int[]{num / gridYsize, num % gridYsize})
                .toList();

        return randomPos;
    }


    static void validateParameters() {
        if (dirtyRatio > 1) {
            throw new InvalidDirtynessInputException("Ratio of dirty to clean patches cannot be more than 1. Was:" + dirtyRatio);
        }
        totalDirtyPatches = (int) ((gridXsize * gridYsize) * dirtyRatio);
        if (totalDirtyPatches < 1) {
            throw new InvalidDirtynessInputException("Should have at least 1 dirty patch for ranking algorithm footprint. Was:" + totalDirtyPatches);
        }
    }

}
