package com.dirtmuncher;

public enum EAlgorithmSelection {
    CONCAT_COORDS_HASHSET("Concatenated Coords HashSet"), //incurs overhead of creating each coord String twice, once when adding to the Map and another when creating a String for each position of room to check dirtiness
    SPARSE_GRID_HASHMAP("Sparse grid HashMap"),
    FULL_GRID_2D_ARRAY("FullGrid2DArray");

    private final String name;

    // Constructor to initialize the description
    EAlgorithmSelection(String name) {
        this.name = name;
    }

    // Getter for the description
    public String getName() {
        return name;
    }


    // Static method to select the enum based on dirtRatio
    public static EAlgorithmSelection selectByDirtRatio(double dirtRatio) {
        if (dirtRatio < 0 || dirtRatio > 1) {
            throw new IllegalArgumentException("Dirt ratio must be between 0 and 1");
        }

        if (dirtRatio <= 0.005) {
            return CONCAT_COORDS_HASHSET;
        } else if (dirtRatio <= 0.01) {
            return SPARSE_GRID_HASHMAP;
        } else {
            return FULL_GRID_2D_ARRAY;
        }
    }
}
