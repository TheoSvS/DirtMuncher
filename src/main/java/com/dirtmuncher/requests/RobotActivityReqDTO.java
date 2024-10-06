package com.dirtmuncher.requests;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;


/**
 * The request DTO for the robots activity
 */
@Data
@NoArgsConstructor
public class RobotActivityReqDTO {
    @Size(min = 2, max = 2, message = "roomSize must have exactly 2 elements")
    @NotNull(message = "roomSize cannot be null")
    int[] roomSize;

    @Size(min = 2, max = 2, message = "coords must have exactly 2 elements")
    @NotNull(message = "coords cannot be null")
    int[] coords;

    @NotNull(message = "patches cannot be null")

    private List<@Size(min = 2, max = 2, message = "Each patch must have exactly 2 coordinates") int[]> patches;

    @NotBlank(message = "instructions cannot be empty")
    String instructions;

    @AssertTrue(message = "All coordinate values must be non-negative")
    public boolean isRoomSizeValid() {
        for (int value : roomSize) {
            if(value<0){
                return false;
            }
        }
        return true;
    }

    public RobotActivityReqDTO(int[] roomDim, int[] coords, List<int[]> dirtPatches, String instructions) {
        this.roomSize = roomDim;
        this.coords = coords;
        this.patches = dirtPatches;
        this.instructions = instructions;
    }
}
