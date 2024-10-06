package com.dirtmuncher.services.def;

import com.dirtmuncher.model.RobotActivityState;

/**
 * Interface for robot commands execution
 */
public interface IExecuteCommands {

    public void executePlan(RobotActivityState robotActivityState);
}
