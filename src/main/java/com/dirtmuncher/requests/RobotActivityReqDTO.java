package com.dirtmuncher.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;


@Data
@NoArgsConstructor
public class RobotActivityReqDTO {
/*    @Valid
    @NotNull
    @Size*/
    int[] roomSize;
    int[] coords;
    List<int[]> patches;
    String instructions;

    public RobotActivityReqDTO(int[] roomDim, int[] coords, List<int[]> dirtPatches, String instructions) {
        this.roomSize = roomDim;
        this.coords = coords;
        this.patches = dirtPatches;
        this.instructions = instructions;
    }
}
