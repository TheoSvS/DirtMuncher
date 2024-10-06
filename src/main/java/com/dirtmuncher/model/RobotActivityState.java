package com.dirtmuncher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RobotActivityState {
    private Coords currPos;
    private int cleanedCounter;

    public RobotActivityState(int[] currPos, int cleanedCounter) {
        this.currPos = new Coords(currPos);
        this.cleanedCounter = cleanedCounter;
    }

    public void incrementX(){
        currPos.setXAxis(currPos.getXAxis()+1);
    }

    public void decrementX(){
        currPos.setXAxis(currPos.getXAxis()-1);
    }

    public void incrementY(){
        currPos.setYAxis(currPos.getYAxis()+1);
    }

    public void decrementY(){
        currPos.setYAxis(currPos.getYAxis()-1);
    }

}
