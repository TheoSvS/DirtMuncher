package com.dirtmuncher.responses;

import com.dirtmuncher.model.Coords;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The response DTO for the robot's activity
 */
@Data
@NoArgsConstructor
public class RobotActivityRespDTO {
    private int[] coords = new int[2];
    private int patches;
    public RobotActivityRespDTO(int[] coords, int patchesCleaned) {
        this.coords = coords;
        this.patches = patchesCleaned;
    }

    public RobotActivityRespDTO(Coords coordsObj, int patchesCleaned) {
        this.coords[0] = coordsObj.getXAxis();
        this.coords[1] = coordsObj.getYAxis();
        this.patches = patchesCleaned;
    }
}
