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
    private List<int[]> patches;

    @NotNull(message = "instructions cannot be null")
    @NotBlank(message = "instructions cannot be empty")
    String instructions;

    @AssertTrue(message = "dirt patch coordinates cannot contain null and must have exactly 2 non-negative elements")
    public boolean hasValidDirtPatches() {
        return patches.stream()
                .filter(coords -> coords == null || coords.length != 2 || Arrays.stream(coords).anyMatch(coord -> coord < 0))
                .toList()
                .isEmpty();
    }

    @AssertTrue(message = "Room size and robot coords must be non-negative")
    public boolean hasNonNegativeCoordinates() {
        if (roomSize == null || coords == null) {
            return true; //To allow the @NotNull annotations to handle this
        }
        return Arrays.stream(roomSize).noneMatch(coord -> coord < 0) &&
                Arrays.stream(coords).noneMatch(coord -> coord < 0) ;

    }

    public RobotActivityReqDTO(int[] roomDim, int[] coords, List<int[]> dirtPatches, String instructions) {
        this.roomSize = roomDim;
        this.coords = coords;
        this.patches = dirtPatches;
        this.instructions = instructions;
    }
}
