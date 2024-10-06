package com.dirtmuncher.services.def;

import com.dirtmuncher.model.RobotActivityState;

public interface IExecuteCommands {

    public void executePlan(RobotActivityState robotActivityState);
}
