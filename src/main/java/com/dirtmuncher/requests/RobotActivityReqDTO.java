package com.dirtmuncher.requests;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


/**
 * The request DTO for the robots activity
 */
@Data
@NoArgsConstructor
public class RobotActivityReqDTO {
    @Size(min = 2, max = 2, message = "RoomSize must have exactly 2 elements")
    @NotNull(message = "RoomSize cannot be null")
    int[] roomSize;

    @Size(min = 2, max = 2, message = "Coords must have exactly 2 elements")
    @NotNull(message = "Coords cannot be null")
    int[] coords;

    @NotNull(message = "Patches cannot be null")
    private List<int[]> patches;

    @NotNull(message = "Instructions cannot be null")
    @NotBlank(message = "Instructions cannot be empty")
    String instructions;

    //NOTE: Could skip validation of coords of dirt patches here, and skip any invalid ones during processing,
    // so we don't iterate them twice. Could improve performance if we have really large List of dirt patches
    @AssertTrue(message = "Dirt patch coordinates cannot be null, must have exactly 2 non-negative elements within room size")
    public boolean hasValidDirtPatches() {
        if (roomSize == null) {
            return true; //To allow the @NotNull annotations to handle roomSize
        }
        return patches.parallelStream()
                .noneMatch(coords -> coords == null
                        || coords.length != 2
                        || coords[0] > roomSize[0]-1
                        || coords[1] > roomSize[1]-1
                        || coords[0] < 0
                        || coords[1] < 0
                );
    }

    @AssertTrue(message = "Room size and robot coords must be non-negative. Robot coords within room size")
    public boolean hasNonNegativeCoordinates() {
        if (roomSize == null || coords == null) {
            return true; //To allow the @NotNull annotations to handle roomSize and coords
        }
        return coords[0] < roomSize[0]
                && coords[1] < roomSize[1]
                && coords[0] >= 0
                && coords[1] >= 0;

    }

    public RobotActivityReqDTO(int[] roomDim, int[] coords, List<int[]> dirtPatches, String instructions) {
        this.roomSize = roomDim;
        this.coords = coords;
        this.patches = dirtPatches;
        this.instructions = instructions;
    }
}
