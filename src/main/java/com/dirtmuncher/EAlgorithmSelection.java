package com.dirtmuncher;

/**
 * Models the selection of the lookup algorithm
 */
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

}
